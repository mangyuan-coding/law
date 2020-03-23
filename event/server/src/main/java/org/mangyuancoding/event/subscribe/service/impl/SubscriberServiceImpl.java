package org.mangyuancoding.event.subscribe.service.impl;

import lombok.RequiredArgsConstructor;
import net.dreamlu.mica.core.utils.$;
import org.mangyuancoding.constitution.message.IdentifierFactory;
import org.mangyuancoding.event.subscribe.model.MongoEventMessage;
import org.mangyuancoding.event.subscribe.model.SubscribedEvent;
import org.mangyuancoding.event.subscribe.model.Subscriber;
import org.mangyuancoding.event.subscribe.model.repository.MongoEventMessageRepository;
import org.mangyuancoding.event.subscribe.model.repository.SubscribedEventRepository;
import org.mangyuancoding.event.subscribe.model.repository.SubscriberRepository;
import org.mangyuancoding.event.subscribe.service.SubscriberService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/20
 */
@Service
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final AmqpTemplate amqpTemplate;
    private final SubscriberRepository subscriberRepository;
    private final SubscribedEventRepository subscribedEventRepository;
    private final MongoEventMessageRepository mongoEventMessageRepository;

    @Override
    public Iterable<Subscriber> queryByEventType(String eventType) {
        List<SubscribedEvent> subscribedEvents = subscribedEventRepository.findAllByEventType(eventType);

        if ($.isEmpty(subscribedEvents)) {
            return Collections.emptyList();
        }

        Set<String> subscriberIds = subscribedEvents.stream().map(SubscribedEvent::getSubscriberId).collect(Collectors.toSet());
        return subscriberRepository.findAllById(subscriberIds);
    }

    @Override
    public List<String> store(Subscriber subscriber, List<SubscribedEvent> subscribedEvents) {

        Subscriber existingSubscriber = subscriberRepository.findByName(subscriber.getName());

        List<String> sendEventIds = new ArrayList<>();
        String subscriberId;
        if (existingSubscriber != null) {
            subscriberId = existingSubscriber.getId();

            List<SubscribedEvent> existingSubscribedEvents = subscribedEventRepository.findAllBySubscriberId(subscriberId);

            if ($.isNotEmpty(existingSubscribedEvents)) {
                Set<String> existingType = existingSubscribedEvents.stream().map(SubscribedEvent::getEventType).collect(Collectors.toSet());
                subscribedEvents.removeIf(e -> existingType.contains(e.getEventType()));
                if ($.isNotEmpty(subscribedEvents) && subscribedEvents.size() > 0) {
                    subscribedEvents.forEach(subscribedEvent -> {
                        subscribedEvent.setSubscriberId(subscriberId);
                        subscribedEvent.setId(IdentifierFactory.getInstance().generateIdentifier());
                        sendEventIds.add(subscribedEvent.getId());
                    });
                    subscribedEventRepository.saveAll(subscribedEvents);
                }
            } else {
                subscribedEvents.forEach(subscribedEvent -> {
                    subscribedEvent.setSubscriberId(subscriberId);
                    subscribedEvent.setId(IdentifierFactory.getInstance().generateIdentifier());
                    sendEventIds.add(subscribedEvent.getId());
                });
                subscribedEventRepository.saveAll(subscribedEvents);
            }
        } else {
            subscriberId = IdentifierFactory.getInstance().generateIdentifier();
            subscriber.setId(subscriberId);
            subscriberRepository.save(subscriber);

            if ($.isNotEmpty(subscribedEvents)) {
                subscribedEvents.forEach(subscribedEvent -> {
                    subscribedEvent.setSubscriberId(subscriberId);
                    subscribedEvent.setId(IdentifierFactory.getInstance().generateIdentifier());
                    sendEventIds.add(subscribedEvent.getId());
                });
                subscribedEventRepository.saveAll(subscribedEvents);
            }
        }

        return sendEventIds;
    }

    @Async
    public void sendEvent2NewSubscriber(List<String> subscribedEventIds) {

        Iterable<SubscribedEvent> subscribedEvents = subscribedEventRepository.findAllById(subscribedEventIds);

        if ($.isEmpty(subscribedEvents)) {
            return;
        }

        Set<String> subscriberIds = new HashSet<>();
        for (SubscribedEvent subscribedEvent : subscribedEvents) {
            subscriberIds.add(subscribedEvent.getSubscriberId());
        }
        Iterable<Subscriber> subscribers = subscriberRepository.findAllById(subscriberIds);
        if ($.isEmpty(subscribers)) {
            return;
        }
        Map<String, Subscriber> subscriberMap = new HashMap<>();
        for (Subscriber subscriber : subscribers) {
            subscriberMap.put(subscriber.getId(), subscriber);
        }

        for (SubscribedEvent subscribedEvent : subscribedEvents) {
            Subscriber subscriber = subscriberMap.get(subscribedEvent.getSubscriberId());
            if (subscriber == null) {
                continue;
            }
            List<MongoEventMessage> eventMessages = query(subscribedEvent.getEventType(), subscribedEvent.getEventStartTime());
            if ($.isEmpty(eventMessages)) {
                continue;
            }
            eventMessages.sort(Comparator.comparing(MongoEventMessage::getInstant));
            for (MongoEventMessage mongoEventMessage : eventMessages) {
                send(subscriber.getExchange(), subscriber.getRoutingKey(), mongoEventMessage);
            }
        }
    }

    private void send(String exchange, String routingKey, MongoEventMessage mongoEventMessage) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().putAll(mongoEventMessage.getMetaData());

        amqpTemplate.send(exchange, routingKey, new Message(mongoEventMessage.getPayloadJson(), messageProperties));
    }

    private List<MongoEventMessage> query(String eventType, Instant startTimeStamp) {

        List<MongoEventMessage> mongoEventMessages =
                mongoEventMessageRepository.findAllByPayloadTypeEqualsAndInstantAfter(eventType, startTimeStamp);

        if ($.isEmpty(mongoEventMessages)) {
            return Collections.emptyList();
        }
        return mongoEventMessages;
    }
}
