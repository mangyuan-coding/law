package org.mangyuancoding.event;

import org.mangyuancoding.event.channel.ChannelSupplier;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
public class AmqpConfig {

    @Bean
    public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public ChannelSupplier channelSupplier(RabbitTemplate rabbitTemplate) {
        return new AmqpChannelSupplier(rabbitTemplate);
    }
}
