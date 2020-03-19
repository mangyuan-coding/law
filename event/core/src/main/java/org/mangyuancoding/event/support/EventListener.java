package org.mangyuancoding.event.support;

import org.mangyuancoding.constitution.support.Registration;
import org.mangyuancoding.event.event.EventMessage;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/19
 */
public interface EventListener {

    void collectRegistration(Registration registration);

    <T> void on(EventMessage<T> eventMessage);
}
