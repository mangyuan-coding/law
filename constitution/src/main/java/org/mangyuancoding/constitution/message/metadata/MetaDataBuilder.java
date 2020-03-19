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
        Map<String, String> metaData = new HashMap<>();

        metaData.put(MessageMetaDataKeys.MESSAGE_IDENTIFIER, message.getIdentifier());
        metaData.put(MessageMetaDataKeys.MESSAGE_NAME, message.getPayloadType().getSimpleName());

        return MetaData.from(metaData);
    }
}
