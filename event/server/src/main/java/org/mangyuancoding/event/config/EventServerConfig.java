package org.mangyuancoding.event.config;

import org.mangyuancoding.event.listener.DefaultEventListener;
import org.mangyuancoding.event.store.EventStore;
import org.mangyuancoding.event.support.AmqpChannelConstants;
import org.mangyuancoding.event.support.AmqpChannelSupplier;
import org.mangyuancoding.event.support.ChannelSupplier;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/19
 */
@EnableAsync
@Configuration
public class EventServerConfig {

    @Bean(destroyMethod = "destroy")
    @ConditionalOnBean(EventStore.class)
    public DefaultEventListener eventListener(EventStore eventStore) {
        return new DefaultEventListener(eventStore);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(AmqpChannelConstants.SERVER_RECEIVE_EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(AmqpChannelConstants.SERVER_RECEIVE_QUEUE);
    }

    @Bean
    public Binding bindingExchangeDirect(Queue queue, TopicExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(AmqpChannelConstants.SERVER_RECEIVE_ROUTING_KEY);
    }

    @Bean
    @Autowired
    public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    @Autowired
    @ConditionalOnBean({AmqpTemplate.class, DefaultEventListener.class})
    public ChannelSupplier amqpChannelSupplier(AmqpTemplate amqpTemplate, List<DefaultEventListener> eventListeners) {
        AmqpChannelSupplier amqpChannelSupplier = new AmqpChannelSupplier(amqpTemplate);
        for (DefaultEventListener eventListener : eventListeners) {
            eventListener.collectRegistration(amqpChannelSupplier.register(eventListener));
        }
        return amqpChannelSupplier;
    }
}
