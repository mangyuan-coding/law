package org.mangyuancoding.event.store;

import lombok.RequiredArgsConstructor;
import net.dreamlu.mica.core.utils.$;
import org.mangyuancoding.event.event.EventMessage;
import org.mangyuancoding.event.model.MongoEventMessage;
import org.mangyuancoding.event.model.Subscriber;
import org.mangyuancoding.event.model.repository.MongoEventMessageRepository;
import org.mangyuancoding.event.support.ChannelSupplier;
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

    private final SubscriberStore subscriberStore;
    private final ChannelSupplier channelSupplier;
    private final MongoEventMessageRepository mongoEventMessageRepository;

    @Override
    public void save(EventMessage<?> eventMessage) {
        mongoEventMessageRepository.save(new MongoEventMessage(eventMessage));

        Iterable<Subscriber> subscribers = subscriberStore.queryByEventType(eventMessage.getPayloadType().getSimpleName());
        if ($.isNotEmpty(subscribers)) {
            for (Subscriber subscriber : subscribers) {
                channelSupplier.send(subscriber.getExchange(), subscriber.getRoutingKey(), eventMessage);
            }
        }
    }
}
