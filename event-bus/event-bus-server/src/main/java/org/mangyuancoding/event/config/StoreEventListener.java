package org.mangyuancoding.event.config;

import net.dreamlu.mica.core.utils.$;
import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.event.event.EventMessage;
import org.mangyuancoding.event.eventstore.EventMessagePO;
import org.mangyuancoding.event.eventstore.EventMessageRepository;
import org.mangyuancoding.event.receive.AbstractEventListener;
import org.mangyuancoding.event.receive.EventListener;
import org.mangyuancoding.event.send.Sender;
import org.mangyuancoding.event.subscribe.model.Subscriber;
import org.mangyuancoding.event.subscribe.service.SubscriberService;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
public class StoreEventListener extends AbstractEventListener implements EventListener {

    private EventMessageRepository eventMessageRepository;
    private SubscriberService subscriberService;
    private Sender sender;

    public StoreEventListener(EventMessageRepository eventMessageRepository, SubscriberService subscriberService, Sender sender) {
        this.eventMessageRepository = eventMessageRepository;
        this.subscriberService = subscriberService;
        this.sender = sender;
    }

    @Override
    public void onEvent(EventMessage<byte[]> eventMessage) {

        EventMessagePO store = new EventMessagePO(eventMessage.getPayload(), eventMessage.getMetaData());
        eventMessageRepository.save(store);

        Iterable<Subscriber> subscribers = subscriberService.queryByEventType(store.getPayloadType());
        if ($.isNotEmpty(subscribers)) {
            for (Subscriber subscriber : subscribers) {
                sender.send(store.getPayloadJson(), MetaData.from(store.getMetaData()), subscriber);
            }
        }
    }
}
