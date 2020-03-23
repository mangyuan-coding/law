package org.mangyuancoding.event.support;

import net.dreamlu.mica.core.utils.$;
import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.constitution.support.Registration;
import org.mangyuancoding.event.event.EventMessage;
import org.mangyuancoding.event.event.EventMessageMetaDataBuilder;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */
public class AmqpChannelSupplier implements ChannelSupplier {

    private AmqpTemplate amqpTemplate;
    private List<EventListener> eventListeners = new ArrayList<>();

    public AmqpChannelSupplier(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void send(EventMessage<?> eventMessage) {

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().putAll(eventMessage.getMetaData());

        amqpTemplate.send(AmqpChannelConstants.SERVER_RECEIVE_EXCHANGE, AmqpChannelConstants.SERVER_RECEIVE_ROUTING_KEY,
                new Message(Objects.requireNonNull($.toJson(eventMessage.getPayload())).getBytes(),
                        messageProperties));
    }

    @Override
    public void receive(org.springframework.messaging.Message<byte[]> message) {

        MetaData metaData = EventMessageMetaDataBuilder.build(message.getHeaders());

        if ($.isNotEmpty(eventListeners)) {
            for (EventListener eventListener : eventListeners) {
                eventListener.on(message.getPayload(), metaData);
            }
        }
    }

    @Override
    public Registration register(EventListener eventListener) {
        this.eventListeners.add(eventListener);
        return () -> this.eventListeners.remove(eventListener);
    }

}
