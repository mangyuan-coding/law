package org.mangyuancoding.event.event;

import org.mangyuancoding.constitution.message.metadata.MessageMetaDataBuilder;
import org.mangyuancoding.constitution.message.metadata.MetaData;

import java.util.Map;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/20
 */
public class EventMessageMetaDataBuilder extends MessageMetaDataBuilder {

    public static MetaData build(EventMessage<?> message) {
        return MessageMetaDataBuilder.build(message)
                .and(EventMessageMetaDataKeys.TIMESTAMP, message.getTimestamp().getEpochSecond());
    }

    public static MetaData build(Map<String, Object> headers) {

        MetaData metaData = MessageMetaDataBuilder.build(headers);
        if (headers.containsKey(EventMessageMetaDataKeys.TIMESTAMP)) {
            metaData = metaData.and(EventMessageMetaDataKeys.TIMESTAMP, headers.get(EventMessageMetaDataKeys.TIMESTAMP));
        }
        return metaData;
    }
}
