package org.mangyuancoding.eventsourcing.event;

import org.mangyuancoding.constitution.message.Message;
import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.constitution.message.metadata.MetaDataBuilder;

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
}
