package org.mangyuancoding.event.config;

import org.mangyuancoding.event.publish.AbstractPublisher;
import org.mangyuancoding.event.publish.Publisher;
import org.mangyuancoding.event.support.AmqpChannelSupplier;
import org.mangyuancoding.event.support.ChannelSupplier;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

/**
 * Description
 * 自动配置
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/19
 */
public class EventClientConfig {

    @Bean
    public ChannelSupplier channelSupplier(AmqpTemplate amqpTemplate) {
        return new AmqpChannelSupplier(amqpTemplate);
    }

    @Bean
    @Order(1)
    @ConditionalOnBean(ChannelSupplier.class)
    public Publisher publisher(ChannelSupplier channelSupplier) {
        return AbstractPublisher.build(channelSupplier);
    }

    @Bean
    @Order(2)
    @ConditionalOnMissingBean(Publisher.class)
    public Publisher publisher() {
        return (event, metaData) -> {
        };
    }
}
