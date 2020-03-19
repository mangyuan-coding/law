package org.mangyuancoding.eventsourcing;

import org.mangyuancoding.constitution.message.Message;

/**
 * Description
 * 管道供应商，用来发送数据的，eg：mq，http
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */
public interface ChannelSupplier {

    void send(Message<?> eventMessage);
}
