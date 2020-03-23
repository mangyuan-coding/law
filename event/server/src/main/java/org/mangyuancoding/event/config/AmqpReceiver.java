package org.mangyuancoding.event.config;

import lombok.RequiredArgsConstructor;
import org.mangyuancoding.event.support.AmqpChannelConstants;
import org.mangyuancoding.event.support.ChannelSupplier;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/23
 */
@Component
@RequiredArgsConstructor
public class AmqpReceiver {

    private final ChannelSupplier channelSupplier;

    @RabbitListener(queues = AmqpChannelConstants.SERVER_RECEIVE_QUEUE)
    public void receive(Message<byte[]> message) {
        channelSupplier.receive(message);
    }
}
