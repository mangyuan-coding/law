package org.mangyuancoding.event.subscribe.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.dreamlu.mica.core.result.R;
import net.dreamlu.mica.core.utils.$;
import org.mangyuancoding.event.ChannelConstants;
import org.mangyuancoding.event.subscribe.model.SubscribedEvent;
import org.mangyuancoding.event.subscribe.model.Subscriber;
import org.mangyuancoding.event.subscribe.service.SubscriberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    private final SubscriberService subscriberService;

    @PostMapping(ChannelConstants.SUBSCRIBE_URL)
    public Mono<R<?>> subscribe(@RequestBody RegisterParam param) {

        List<String> subscribedEventIds = subscriberService.store(param.build(), param.buildSubscribedEvent());
        subscriberService.sendEvent2NewSubscriber(subscribedEventIds);

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

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Event event = (Event) o;
                return Objects.equals(eventType, event.eventType);
            }

            @Override
            public int hashCode() {
                return Objects.hash(eventType);
            }
        }
    }
}
