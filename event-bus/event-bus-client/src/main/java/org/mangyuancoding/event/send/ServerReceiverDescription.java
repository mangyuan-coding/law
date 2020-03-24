package org.mangyuancoding.event.send;

import org.mangyuancoding.event.ChannelConstants;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
public class ServerReceiverDescription implements ReceiverDescription {

    @Override
    public String getExchange() {
        return ChannelConstants.SERVER_RECEIVE_EXCHANGE;
    }

    @Override
    public String getQueue() {
        return ChannelConstants.SERVER_RECEIVE_QUEUE;
    }

    @Override
    public String getRoutingKey() {
        return ChannelConstants.SERVER_RECEIVE_ROUTING_KEY;
    }
}
