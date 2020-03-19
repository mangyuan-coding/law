package org.mangyuancoding.event.support;

import org.mangyuancoding.constitution.support.Registration;
import org.mangyuancoding.event.event.EventMessage;

import java.util.Map;


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
     * @param body    消息体
     * @param headers 消息头
     */
    void receive(String body, Map<String, Object> headers);

    /**
     * 注册监听器
     *
     * @param eventListener 事件监听器
     * @return 票根
     */
    Registration register(EventListener eventListener);
}
