package org.mangyuancoding.event.receive;


import org.mangyuancoding.event.event.EventMessage;
import org.mangyuancoding.event.support.Registration;

/**
 * Description
 * 事件监听器（监听说有消息）
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
public interface EventListener {

    /**
     * 当被注册进receiver后
     *
     * @param registration 票根
     */
    void onRegister(Registration registration);

    /**
     * 事件监听器摧毁方法
     */
    void destroy();

    /**
     * 当收到事件消息后
     *
     * @param eventMessage 事件消息
     */
    void onEvent(EventMessage<byte[]> eventMessage);
}
