package org.mangyuancoding.event;

import net.dreamlu.mica.core.utils.$;
import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.event.channel.ChannelReceiver;
import org.mangyuancoding.event.event.EventMessageMetaDataBuilder;
import org.mangyuancoding.event.event.GenericEventMessage;
import org.mangyuancoding.event.receive.AbstractReceiver;
import org.mangyuancoding.event.receive.EventListener;
import org.springframework.messaging.Message;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
public abstract class AmqpChannelReceiver extends AbstractReceiver implements ChannelReceiver {

    public void receive(Message<byte[]> message) {

        MetaData metaData = EventMessageMetaDataBuilder.build(message.getHeaders());

        GenericEventMessage<byte[]> eventMessage = new GenericEventMessage<>(message.getPayload(), metaData);

        if ($.isNotEmpty(super.eventListeners)) {
            for (EventListener eventListener : super.eventListeners) {
                eventListener.onEvent(eventMessage);
            }
        }
    }

}
