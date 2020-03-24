package org.mangyuancoding.event.channel;

import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.event.event.EventMessage;
import org.mangyuancoding.event.send.ReceiverDescription;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
public interface ChannelSupplier {

    void send(EventMessage<Object> eventMessage, ReceiverDescription receiverDescription);

    void send(byte[] payload, MetaData metaData, ReceiverDescription receiverDescription);
}
