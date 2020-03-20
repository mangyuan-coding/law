package org.mangyuancoding.event.store;

import lombok.RequiredArgsConstructor;
import net.dreamlu.mica.core.utils.$;
import net.dreamlu.mica.core.utils.ClassUtil;
import org.mangyuancoding.constitution.message.IdentifierFactory;
import org.mangyuancoding.event.event.EventMessage;
import org.mangyuancoding.event.event.GenericEventMessage;
import org.mangyuancoding.event.model.MongoEventMessage;
import org.mangyuancoding.event.model.SubscribedEvent;
import org.mangyuancoding.event.model.Subscriber;
import org.mangyuancoding.event.model.repository.MongoEventMessageRepository;
import org.mangyuancoding.event.model.repository.SubscribedEventRepository;
import org.mangyuancoding.event.model.repository.SubscriberRepository;
import org.mangyuancoding.event.support.ChannelSupplier;
import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/20
 */
@Component
@RequiredArgsConstructor
public class SubscriberStore {

    private final ChannelSupplier channelSupplier;
    private final SubscriberRepository subscriberRepository;
    private final SubscribedEventRepository subscribedEventRepository;
    private final MongoEventMessageRepository mongoEventMessageRepository;

    public Iterable<Subscriber> queryByEventType(String eventType) {
        List<SubscribedEvent> subscribedEvents = subscribedEventRepository.findAllByEventType(eventType);

        if ($.isEmpty(subscribedEvents)) {
            return Collections.emptyList();
        }

        Set<String> subscriberIds = subscribedEvents.stream().map(SubscribedEvent::getSubscriberId).collect(Collectors.toSet());
        return subscriberRepository.findAllById(subscriberIds);
    }


    public void store(Subscriber subscriber, List<SubscribedEvent> subscribedEvents) {

        String subscriberId = IdentifierFactory.getInstance().generateIdentifier();

        subscriber.setId(subscriberId);
        subscriberRepository.save(subscriber);

        if ($.isNotEmpty(subscribedEvents)) {
            subscribedEvents.forEach(subscribedEvent -> subscribedEvent.setSubscriberId(subscriberId));
        }

        subscribedEventRepository.saveAll(subscribedEvents);

        ((SubscriberStore) AopContext.currentProxy()).sendEvent2NewSubscriber(subscriberId);
    }

    /**
     * 为新订阅者发送历史消息
     *
     * @param subscriberId 订阅者
     */
    @Async
    public void sendEvent2NewSubscriber(String subscriberId) {
        Optional<Subscriber> subscriber = subscriberRepository.findById(subscriberId);
        if (subscriber.isEmpty()) {
            return;
        }
        List<SubscribedEvent> subscribedEvents = subscribedEventRepository.findAllBySubscriberId(subscriberId);

        if ($.isEmpty(subscribedEvents)) {
            return;
        }

        for (SubscribedEvent subscribedEvent : subscribedEvents) {
            List<EventMessage<?>> eventMessages = query(subscribedEvent.getEventType(), subscribedEvent.getEventStartTime());
            eventMessages.sort(Comparator.comparing(EventMessage::getTimestamp));
            for (EventMessage<?> eventMessage : eventMessages) {
                channelSupplier.send(subscriber.get().getExchange(), subscriber.get().getRoutingKey(), eventMessage);
            }
        }
    }

    private List<EventMessage<?>> query(String eventType, Instant startTimeStamp) {

        List<MongoEventMessage> mongoEventMessages =
                mongoEventMessageRepository.findAllByPayloadTypeEqualsAndInstantAfter(eventType, startTimeStamp);

        if ($.isEmpty(mongoEventMessages)) {
            return Collections.emptyList();
        }

        List<EventMessage<?>> eventMessages = new ArrayList<>(mongoEventMessages.size());
        for (MongoEventMessage mongoEventMessage : mongoEventMessages) {
            try {
                eventMessages.add(new GenericEventMessage<>(
                        $.readJson(mongoEventMessage.getPayloadJson(),
                                ClassUtil.forName(mongoEventMessage.getPayloadType(), ClassLoader.getSystemClassLoader())),
                        mongoEventMessage.getMetaData()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return eventMessages;
    }
}
