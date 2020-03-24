package org.mangyuancoding.event.receive;


import org.mangyuancoding.event.event.EventMessage;

/**
 * Description
 * 事件处理器，处理指定事件
 * <p>
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/23
 */
public interface EventHandler {

    void handle(EventMessage<?> eventMessage);

    boolean canHandle(String messageType);
}
