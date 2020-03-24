package org.mangyuancoding.event.config;

import org.mangyuancoding.event.AmqpChannelReceiver;
import org.mangyuancoding.event.AmqpConfig;
import org.mangyuancoding.event.ChannelConstants;
import org.mangyuancoding.event.channel.ChannelReceiver;
import org.mangyuancoding.event.channel.ChannelSupplier;
import org.mangyuancoding.event.eventstore.EventMessageRepository;
import org.mangyuancoding.event.receive.EventListener;
import org.mangyuancoding.event.send.Sender;
import org.mangyuancoding.event.subscribe.service.SubscriberService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
@Configuration
@Import(AmqpConfig.class)
public class ServerConfig {

    @Bean
    public TopicExchange serverExchange() {
        return new TopicExchange(ChannelConstants.SERVER_RECEIVE_EXCHANGE);
    }

    @Bean
    public Queue serverQueue() {
        return new Queue(ChannelConstants.SERVER_RECEIVE_QUEUE);
    }

    @Bean
    public Binding bindingExchange(Queue serverQueue, TopicExchange serverExchange) {
        return BindingBuilder.bind(serverQueue).to(serverExchange).with(ChannelConstants.SERVER_RECEIVE_ROUTING_KEY);
    }

    @Bean
    public ChannelReceiver channelReceiver() {
        return new AmqpChannelReceiver() {
            @Override
            @RabbitListener(queues = ChannelConstants.SERVER_RECEIVE_QUEUE)
            public void receive(Message<byte[]> message) {
                super.receive(message);
            }
        };
    }

    @Bean
    public Sender sender(ChannelSupplier channelSupplier) {
        return new EventServerSender(channelSupplier);
    }

    @Bean(destroyMethod = "destroy")
    public EventListener eventListener(EventMessageRepository eventMessageRepository,
                                       SubscriberService subscriberService,
                                       Sender sender, ChannelReceiver channelReceiver) {
        EventListener eventListener = new StoreEventListener(eventMessageRepository, subscriberService, sender);
        eventListener.onRegister(channelReceiver.register(eventListener));
        return eventListener;
    }

}
