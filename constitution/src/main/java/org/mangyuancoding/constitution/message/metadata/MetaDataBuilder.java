package org.mangyuancoding.constitution.message.metadata;

import org.mangyuancoding.constitution.message.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */
public abstract class MetaDataBuilder {

    public static MetaData build(Message<?> message) {
        Map<String, Object> metaData = new HashMap<>();

        metaData.put(MessageMetaDataKeys.MESSAGE_IDENTIFIER, message.getIdentifier());
        metaData.put(MessageMetaDataKeys.PAYLOAD_TYPE_NAME, message.getPayloadType().getName());

        return MetaData.from(metaData);
    }

    public static MetaData build(Map<String, Object> headers) {

        Map<String, Object> metaData = new HashMap<>();

        if (headers.containsKey(MessageMetaDataKeys.MESSAGE_IDENTIFIER)) {
            metaData.put(MessageMetaDataKeys.MESSAGE_IDENTIFIER, headers.get(MessageMetaDataKeys.MESSAGE_IDENTIFIER));
        }

        if (headers.containsKey(MessageMetaDataKeys.PAYLOAD_TYPE_NAME)) {
            metaData.put(MessageMetaDataKeys.PAYLOAD_TYPE_NAME, headers.get(MessageMetaDataKeys.PAYLOAD_TYPE_NAME));
        }

        return MetaData.from(metaData);
    }
}
