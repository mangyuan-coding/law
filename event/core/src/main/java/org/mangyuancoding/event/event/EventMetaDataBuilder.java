package org.mangyuancoding.event.event;

import org.mangyuancoding.constitution.message.Message;
import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.constitution.message.metadata.MetaDataBuilder;

import java.util.Map;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */
public class EventMetaDataBuilder extends MetaDataBuilder {

    public static MetaData build(Message<?> message) {
        return MetaDataBuilder.build(message).and(EventMessageMetaDataKeys.EVENT_NAME, message.getPayloadType().getSimpleName());
    }

    public static MetaData build(Map<String, Object> headers) {

        MetaData metaData = MetaDataBuilder.build(headers);

        if (headers.containsKey(EventMessageMetaDataKeys.EVENT_NAME)) {
            metaData.and(EventMessageMetaDataKeys.EVENT_NAME, headers.get(EventMessageMetaDataKeys.EVENT_NAME));
        }

        return metaData;
    }
}
