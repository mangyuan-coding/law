package org.mangyuancoding.event.store;

import lombok.RequiredArgsConstructor;
import net.dreamlu.mica.core.utils.$;
import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.event.model.MongoEventMessage;
import org.mangyuancoding.event.model.Subscriber;
import org.mangyuancoding.event.model.repository.MongoEventMessageRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/19
 */
@Component
@RequiredArgsConstructor
public class MongoEventStore implements EventStore {

    private final AmqpTemplate amqpTemplate;
    private final SubscriberStore subscriberStore;
    private final MongoEventMessageRepository mongoEventMessageRepository;

    @Override
    public void save(byte[] payload, MetaData metaData) {
        MongoEventMessage mongoEventMessage = new MongoEventMessage(payload, metaData);
        mongoEventMessageRepository.save(mongoEventMessage);

        Iterable<Subscriber> subscribers = subscriberStore.queryByEventType(mongoEventMessage.getPayloadType());
        if ($.isNotEmpty(subscribers)) {
            for (Subscriber subscriber : subscribers) {
                send(subscriber.getExchange(), subscriber.getRoutingKey(), mongoEventMessage);
            }
        }
    }

    private void send(String exchange, String routingKey, MongoEventMessage eventMessage) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().putAll(eventMessage.getMetaData());

        amqpTemplate.send(exchange, routingKey, new Message(eventMessage.getPayloadJson().getBytes(), messageProperties));
    }
}
