package org.mangyuancoding.event.config;

import org.mangyuancoding.event.DefaultEventListener;
import org.mangyuancoding.event.event.EventMessage;
import org.mangyuancoding.event.store.EventStore;
import org.mangyuancoding.event.store.MongoEventStore;
import org.mangyuancoding.event.support.AmqpChannelSupplier;
import org.mangyuancoding.event.support.ChannelSupplier;
import org.mangyuancoding.event.support.EventServerProperties;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/19
 */
public class EventServerConfig {

    @Resource
    private AmqpTemplate amqpTemplate;
    @Resource
    private MongoTemplate mongoTemplate;

    @Bean
    @Order(1)
    @ConditionalOnBean(MongoTemplate.class)
    public EventStore mongoEventStore() {
        return new MongoEventStore(mongoTemplate);
    }

    @Bean
    @Order(2)
    @ConditionalOnMissingBean(EventStore.class)
    public EventStore emptyEventStore() {
        return new EventStore() {
            @Override
            public <T> void save(EventMessage<T> eventMessage) {
            }
        };
    }

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
