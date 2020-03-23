package org.mangyuancoding.event.receive;

import org.mangyuancoding.event.event.GenericEventMessage;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/23
 */
public interface EventHandler {

    void on(GenericEventMessage<?> eventMessage);

    String handleEventType();
}
