package org.mangyuancoding.event.publish;

import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.constitution.message.metadata.MessageMetaDataBuilder;
import org.mangyuancoding.event.event.EventMessageMetaDataBuilder;
import org.mangyuancoding.event.support.ChannelSupplier;
import org.mangyuancoding.event.event.EventMessage;
import org.mangyuancoding.event.event.GenericEventMessage;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */
public abstract class AbstractPublisher implements Publisher {

    private ChannelSupplier channelSupplier;

    public AbstractPublisher(ChannelSupplier channelSupplier) {
        this.channelSupplier = channelSupplier;
    }

    @Override
    public void publish(Object event, MetaData metaData) {

        EventMessage<Object> eventMessage = GenericEventMessage.asEventMessage(event);
        MetaData defaultMetaData = EventMessageMetaDataBuilder.build(eventMessage);
        if (!metaData.isEmpty()) {
            defaultMetaData.putAll(metaData);
        }

        channelSupplier.send(eventMessage.withMetaData(defaultMetaData));
    }

    public static Publisher build(ChannelSupplier supplier) {
        return new DefaultPublisher(supplier);
    }

    private static class DefaultPublisher extends AbstractPublisher {
        public DefaultPublisher(ChannelSupplier channelSupplier) {
            super(channelSupplier);
        }
    }
}
