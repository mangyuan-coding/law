package org.mangyuancoding.event.receive;

import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.core.utils.$;
import net.dreamlu.mica.core.utils.ClassUtil;
import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.event.event.EventMessageMetaDataBuilder;
import org.mangyuancoding.event.event.EventMessageMetaDataKeys;
import org.mangyuancoding.event.event.GenericEventMessage;
import org.mangyuancoding.event.support.AmqpChannelConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/23
 */
@Slf4j
public class Receiver {

    private ApplicationContext applicationContext;
    private Map<String, EventHandler> eventHandlerMap = new HashMap<>();

    public Receiver(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        var contextEventHandlers = applicationContext.getBeansOfType(EventHandler.class);
        for (var eventHandler : contextEventHandlers.values()) {
            eventHandlerMap.put(eventHandler.handleEventType(), eventHandler);
        }
    }

    @RabbitListener(queues = "${event.client.subscribe-name}" + AmqpChannelConstants.CLIENT_RECEIVE_QUEUE)
    public void receive(Message<byte[]> message) {
        MetaData metaData = EventMessageMetaDataBuilder.build(message.getHeaders());
        Object payload = null;
        try {
            payload = $.readJson(message.getPayload(),
                    ClassUtil.forName((String) metaData.get(EventMessageMetaDataKeys.PAYLOAD_TYPE_NAME),
                            ClassLoader.getSystemClassLoader()));
        } catch (ClassNotFoundException e) {
            log.error("项目中未找到该事件的负载类:" + metaData.get(EventMessageMetaDataKeys.PAYLOAD_TYPE_NAME));
        }

        GenericEventMessage<?> eventMessage = new GenericEventMessage<>(payload, metaData);
        EventHandler eventHandler = eventHandlerMap.get(eventMessage.getPayloadType().getName());
        if (eventHandler != null) {
            eventHandler.on(eventMessage);
        }
    }
}
