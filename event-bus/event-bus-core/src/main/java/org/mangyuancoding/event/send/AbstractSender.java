package org.mangyuancoding.event.send;

import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.event.channel.ChannelSupplier;
import org.mangyuancoding.event.event.EventMessage;
import org.mangyuancoding.event.event.EventMessageMetaDataBuilder;
import org.mangyuancoding.event.event.GenericEventMessage;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
public abstract class AbstractSender implements Sender {

    protected ChannelSupplier channelSupplier;

    public AbstractSender() {
    }

    public AbstractSender(ChannelSupplier channelSupplier) {
        this.channelSupplier = channelSupplier;
    }

    @Override
    public void send(Object event, MetaData metaData, ReceiverDescription receiverDescription) {

        EventMessage<Object> eventMessage = GenericEventMessage.asEventMessage(event);

        MetaData defaultMetaData = EventMessageMetaDataBuilder.build(eventMessage);
        if (!metaData.isEmpty()) {
            defaultMetaData.putAll(metaData);
        }

        eventMessage = eventMessage.withMetaData(defaultMetaData);

        channelSupplier.send(eventMessage, receiverDescription);
    }
}
