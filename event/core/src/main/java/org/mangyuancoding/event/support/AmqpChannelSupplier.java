package org.mangyuancoding.event.support;

import net.dreamlu.mica.core.utils.$;
import org.mangyuancoding.constitution.message.metadata.MessageMetaDataKeys;
import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.constitution.support.Registration;
import org.mangyuancoding.event.event.EventMessage;
import org.mangyuancoding.event.event.EventMetaDataBuilder;
import org.mangyuancoding.event.event.GenericEventMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */
public class AmqpChannelSupplier implements ChannelSupplier {

    private AmqpTemplate amqpTemplate;
    private EventServerProperties eventServerProperties;
    private List<EventListener> eventListeners = new ArrayList<>();

    public AmqpChannelSupplier(AmqpTemplate amqpTemplate, EventServerProperties eventServerProperties) {
        this.amqpTemplate = amqpTemplate;
        this.eventServerProperties = eventServerProperties;
    }

    @Override
    public void send(EventMessage<?> eventMessage) {

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().putAll(eventMessage.getMetaData());

        amqpTemplate.send(eventServerProperties.getExchange(), eventServerProperties.getRoutingKey(),
                new Message(eventMessage.getPayload().toString().getBytes(), messageProperties));
    }

    @Override
    public void send(String exchange, String routingKey, EventMessage<?> eventMessage) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().putAll(eventMessage.getMetaData());

        amqpTemplate.send(exchange, routingKey, new Message(eventMessage.getPayload().toString().getBytes(), messageProperties));
    }

    @Override
    @RabbitListener(queues = EventServerProperties.DEFAULT_QUEUE)
    public void receive(@Payload String body, @Headers Map<String, Object> headers) {

        MetaData metaData = EventMetaDataBuilder.build(headers);

        Object payLoad = $.readJson(body,
                ClassUtils.resolveClassName((String) metaData.get(MessageMetaDataKeys.PAYLOAD_TYPE_NAME),
                        ClassLoader.getSystemClassLoader()));
        if ($.isNotEmpty(eventListeners)) {
            for (EventListener eventListener : eventListeners) {
                eventListener.on(new GenericEventMessage<>(payLoad, metaData));
            }
        }
    }

    @Override
    public Registration register(EventListener eventListener) {
        this.eventListeners.add(eventListener);
        return () -> this.eventListeners.remove(eventListener);
    }

}
