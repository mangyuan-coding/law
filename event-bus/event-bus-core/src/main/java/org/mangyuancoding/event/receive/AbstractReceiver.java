package org.mangyuancoding.event.receive;

import org.mangyuancoding.event.support.Registration;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
public abstract class AbstractReceiver implements Receiver {

    protected List<EventListener> eventListeners;

    @Override
    public Registration register(EventListener eventListener) {

        if (this.eventListeners == null) {
            this.eventListeners = new ArrayList<>();
        }

        this.eventListeners.add(eventListener);

        return () -> this.eventListeners.remove(eventListener);
    }
}
