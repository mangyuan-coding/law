package org.mangyuancoding.eventsourcing.spring;

import org.mangyuancoding.constitution.message.Message;
import org.mangyuancoding.eventsourcing.ChannelSupplier;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */
public class AmqpChannelSupplier implements ChannelSupplier {



    @Override
    public void send(Message<?> eventMessage) {

    }
}
