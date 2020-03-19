package org.mangyuancoding.event;

import org.mangyuancoding.constitution.support.Registration;
import org.mangyuancoding.event.event.EventMessage;
import org.mangyuancoding.event.store.EventStore;
import org.mangyuancoding.event.support.EventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */
public class DefaultEventListener implements EventListener {

    private EventStore eventStore;
    private List<Registration> registrations = new ArrayList<>();

    public DefaultEventListener(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void destroy() {
        if (registrations != null && registrations.size() > 0) {
            for (Registration registration : this.registrations) {
                registration.close();
            }
        }
    }

    @Override
    public void collectRegistration(Registration registration) {
        registrations.add(registration);
    }

    @Override
    public <T> void on(EventMessage<T> eventMessage) {
        eventStore.save(eventMessage);
    }
}
