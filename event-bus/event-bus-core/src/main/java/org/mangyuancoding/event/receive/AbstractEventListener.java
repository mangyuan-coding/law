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
public abstract class AbstractEventListener implements EventListener {

    private List<Registration> registrations;

    public void destroy() {
        if (registrations != null && registrations.size() > 0) {
            for (Registration registration : this.registrations) {
                registration.close();
            }
        }
    }

    @Override
    public void onRegister(Registration registration) {
        if (this.registrations == null) {
            this.registrations = new ArrayList<>();
        }
        this.registrations.add(registration);
    }
}
