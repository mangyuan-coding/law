package org.mangyuancoding.event.config;

import org.mangyuancoding.event.DefaultEventListener;
import org.mangyuancoding.event.store.EventStore;
import org.mangyuancoding.event.support.AmqpChannelSupplier;
import org.mangyuancoding.event.support.ChannelSupplier;
import org.mangyuancoding.event.support.EventServerProperties;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/19
 */
@Configuration
public class EventServerConfig {

    @Resource
    private AmqpTemplate amqpTemplate;

    @Bean(destroyMethod = "destroy")
    @ConditionalOnBean(EventStore.class)
    public DefaultEventListener eventListener(EventStore eventStore) {
        return new DefaultEventListener(eventStore);
    }

    @Bean
    @ConditionalOnBean({AmqpTemplate.class, EventServerProperties.class, DefaultEventListener.class})
    public ChannelSupplier amqpChannelSupplier(EventServerProperties eventServerProperties, List<DefaultEventListener> eventListeners) {
        AmqpChannelSupplier amqpChannelSupplier = new AmqpChannelSupplier(amqpTemplate, eventServerProperties);
        for (DefaultEventListener eventListener : eventListeners) {
            eventListener.collectRegistration(amqpChannelSupplier.register(eventListener));
        }
        return amqpChannelSupplier;
    }

    @Bean
    @ConfigurationProperties(prefix = "event-server")
    public EventServerProperties eventServerProperties() {
        return new EventServerProperties();
    }
}
