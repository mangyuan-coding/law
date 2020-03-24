package org.mangyuancoding.event.config;


import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.event.channel.ChannelSupplier;
import org.mangyuancoding.event.send.AbstractSender;
import org.mangyuancoding.event.send.ReceiverDescription;
import org.mangyuancoding.event.send.Sender;

/**
 * Description
 * 事件中心的发布器
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
public class EventServerSender extends AbstractSender implements Sender {

    public EventServerSender(ChannelSupplier channelSupplier) {
        super(channelSupplier);
    }

    @Override
    public void publish(Object event, MetaData metaData) {
        throw new IllegalArgumentException("不支持广播模式");
    }

    @Override
    public void send(byte[] payload, MetaData metaData, ReceiverDescription receiverDescription) {
        super.channelSupplier.send(payload, metaData, receiverDescription);
    }
}
