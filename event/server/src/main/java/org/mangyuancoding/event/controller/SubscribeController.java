package org.mangyuancoding.event.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.dreamlu.mica.core.result.R;
import net.dreamlu.mica.core.utils.$;
import org.mangyuancoding.event.model.SubscribedEvent;
import org.mangyuancoding.event.model.Subscriber;
import org.mangyuancoding.event.store.SubscriberStore;
import org.mangyuancoding.event.support.AmqpChannelConstants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/20
 */
@RestController
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscriberStore subscriberStore;

    @PostMapping(AmqpChannelConstants.SUBSCRIBE_URL)
    public Mono<R<?>> subscribe(@RequestBody RegisterParam param) {

        subscriberStore.store(param.build(), param.buildSubscribedEvent());

        return Mono.just(R.success());
    }


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterParam {

        private String name;

        private String exchange;

        private String routingKey = "#";

        private Set<Event> events;

        public Subscriber build() {
            return Subscriber.builder().exchange(exchange).name(name).routingKey(routingKey).build();
        }

        public List<SubscribedEvent> buildSubscribedEvent() {
            if ($.isEmpty(this.events)) {
                return Collections.emptyList();
            }
            List<SubscribedEvent> subscribedEvents = new ArrayList<>(this.events.size());
            for (Event event : this.events) {
                subscribedEvents.add(SubscribedEvent.builder()
                        .eventStartTime(Instant.ofEpochSecond(event.eventStartTime))
                        .eventType(event.eventType).build());
            }
            return subscribedEvents;
        }

        @Setter
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Event {

            private String eventType;

            private Long eventStartTime;
        }
    }
}
