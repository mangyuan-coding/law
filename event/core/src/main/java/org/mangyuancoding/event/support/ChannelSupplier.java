package org.mangyuancoding.event.support;

import org.mangyuancoding.constitution.support.Registration;
import org.mangyuancoding.event.event.EventMessage;
import org.springframework.messaging.Message;


/**
 * Description
 * 管道供应商，用来发送数据的，eg：mq，http
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */
public interface ChannelSupplier {
    /**
     * 发送消息
     *
     * @param eventMessage 消息
     */
    void send(EventMessage<?> eventMessage);

    /**
     * 接受到消息
     *
     * @param message 消息
     */
    void receive(Message<byte[]> message);

    /**
     * 注册监听器
     *
     * @param eventListener 事件监听器
     * @return 票根
     */
    Registration register(EventListener eventListener);
}
