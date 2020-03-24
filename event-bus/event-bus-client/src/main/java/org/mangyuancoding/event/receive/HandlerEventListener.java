package org.mangyuancoding.event.receive;

import org.mangyuancoding.event.event.EventMessage;
import org.mangyuancoding.event.support.Registration;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
public class HandlerEventListener extends AbstractEventListener implements EventListener {

    private List<EventHandler> eventHandlers;

    public Registration register(EventHandler eventHandler) {
        if (this.eventHandlers == null) {
            this.eventHandlers = new ArrayList<>();
        }

        this.eventHandlers.add(eventHandler);

        return () -> this.eventHandlers.remove(eventHandler);
    }

    @Override
    public void onEvent(EventMessage<byte[]> eventMessage) {

        if (this.eventHandlers != null && this.eventHandlers.size() > 0) {
            for (EventHandler eventHandler : this.eventHandlers) {
                if (eventHandler.canHandle(eventMessage.getPayloadType().getName())) {
                    eventHandler.handle(eventMessage);
                }
            }
        }
    }

}
