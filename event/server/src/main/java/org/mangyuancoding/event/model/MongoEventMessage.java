package org.mangyuancoding.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.event.event.EventMessageMetaDataKeys;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/20
 */
@Setter
@Getter
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class MongoEventMessage {

    /**
     * 消息id
     */
    private String id;
    /**
     * 负载内容
     */
    private byte[] payloadJson;
    /**
     * 原数据json
     */
    private MetaData metaData;
    /**
     * 负载类型
     */
    private String payloadType;
    /**
     * 消息的开始时间
     */
    private Instant instant;

    public MongoEventMessage(byte[] payload, MetaData metaData) {
        this.id = metaData.get(EventMessageMetaDataKeys.MESSAGE_IDENTIFIER).toString();
        this.payloadJson = payload;
        this.payloadType = metaData.get(EventMessageMetaDataKeys.PAYLOAD_TYPE_NAME).toString();
        this.instant = Instant.ofEpochSecond(Long.parseLong(metaData.get(EventMessageMetaDataKeys.TIMESTAMP).toString()));
        this.metaData = metaData;
    }
}
