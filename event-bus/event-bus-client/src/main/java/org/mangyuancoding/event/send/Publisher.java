package org.mangyuancoding.event.send;

import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.event.channel.ChannelSupplier;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
public class Publisher extends AbstractSender {

    private ServerReceiverDescription receiverDescription;

    public Publisher(ChannelSupplier channelSupplier, ServerReceiverDescription receiverDescription) {
        super(channelSupplier);
        this.receiverDescription = receiverDescription;
    }

    @Override
    public void publish(Object event, MetaData metaData) {
        super.send(event, metaData, this.receiverDescription);
    }

    @Override
    public void send(byte[] payload, MetaData metaData, ReceiverDescription receiverDescription) {
        throw new IllegalArgumentException("不支持该发送方式");
    }
}
