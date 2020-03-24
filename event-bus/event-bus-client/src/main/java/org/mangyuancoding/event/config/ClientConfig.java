package org.mangyuancoding.event.config;

import net.dreamlu.mica.core.utils.$;
import org.mangyuancoding.event.AmqpChannelReceiver;
import org.mangyuancoding.event.AmqpConfig;
import org.mangyuancoding.event.ChannelConstants;
import org.mangyuancoding.event.channel.ChannelReceiver;
import org.mangyuancoding.event.channel.ChannelSupplier;
import org.mangyuancoding.event.receive.EventHandler;
import org.mangyuancoding.event.receive.EventListener;
import org.mangyuancoding.event.receive.HandlerEventListener;
import org.mangyuancoding.event.send.Publisher;
import org.mangyuancoding.event.send.ServerReceiverDescription;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
@Import(AmqpConfig.class)
public class ClientConfig {

    @Resource
    private RestTemplate restTemplate;

    @Bean
    @ConfigurationProperties("event")
    public EventProperties eventProperties() {
        return new EventProperties();
    }

    @Bean
    public Queue clientQueue(EventProperties eventProperties) {
        return new Queue(eventProperties.getClient().getSubscribeName() + ChannelConstants.CLIENT_RECEIVE_QUEUE);
    }

    @Bean
    public TopicExchange clientExchange(EventProperties eventProperties) {
        return new TopicExchange(eventProperties.getClient().getSubscribeName() + ChannelConstants.CLIENT_RECEIVE_EXCHANGE);
    }

    @Bean
    public Binding clientBinding(Queue clientQueue, TopicExchange clientExchange) {
        return BindingBuilder.bind(clientQueue).to(clientExchange).with(ChannelConstants.CLIENT_RECEIVE_ROUTING_KEY);
    }

    @Bean
    public ChannelReceiver channelReceiver() {
        return new AmqpChannelReceiver() {
            @Override
            @RabbitListener(queues = "${event.client.subscribe-name}" + ChannelConstants.CLIENT_RECEIVE_QUEUE)
            public void receive(Message<byte[]> message) {
                super.receive(message);
            }
        };
    }

    @Bean
    public Publisher publisher(ChannelSupplier channelSupplier) {
        return new Publisher(channelSupplier, new ServerReceiverDescription());
    }

    @Bean(destroyMethod = "destroy")
    public EventListener eventListener(ChannelReceiver channelReceiver, ApplicationContext applicationContext) {
        HandlerEventListener eventListener = new HandlerEventListener();
        eventListener.onRegister(channelReceiver.register(eventListener));

        Map<String, EventHandler> eventHandlerMap = applicationContext.getBeansOfType(EventHandler.class);
        if ($.isNotEmpty(eventHandlerMap)) {
            for (EventHandler eventHandler : eventHandlerMap.values()) {
                eventListener.register(eventHandler);
            }
        }

        return eventListener;
    }

    @Bean
    public SubscribeEventRunner subscribeEventRunner(EventProperties eventProperties) {
        return new SubscribeEventRunner(eventProperties, restTemplate);
    }
}
