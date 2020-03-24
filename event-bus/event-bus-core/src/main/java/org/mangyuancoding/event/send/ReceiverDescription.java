package org.mangyuancoding.event.send;

/**
 * Description
 * 接收者描述
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
public interface ReceiverDescription {

    String getExchange();

    String getQueue();

    String getRoutingKey();
}
