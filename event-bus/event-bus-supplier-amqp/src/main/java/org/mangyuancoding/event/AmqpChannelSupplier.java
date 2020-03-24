package org.mangyuancoding.event;

import net.dreamlu.mica.core.utils.$;
import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.event.channel.ChannelSupplier;
import org.mangyuancoding.event.event.EventMessage;
import org.mangyuancoding.event.send.ReceiverDescription;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.util.Objects;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */
public class AmqpChannelSupplier implements ChannelSupplier {

    private AmqpTemplate amqpTemplate;

    public AmqpChannelSupplier(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void send(EventMessage<Object> eventMessage, ReceiverDescription receiverDescription) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().putAll(eventMessage.getMetaData());

        amqpTemplate.send(receiverDescription.getExchange(), receiverDescription.getRoutingKey(),
                new Message(Objects.requireNonNull($.toJson(eventMessage.getPayload())).getBytes(),
                        messageProperties));
    }

    @Override
    public void send(byte[] payload, MetaData metaData, ReceiverDescription receiverDescription) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().putAll(metaData);

        amqpTemplate.send(receiverDescription.getExchange(), receiverDescription.getRoutingKey(),
                new Message(payload, messageProperties));
    }
}
