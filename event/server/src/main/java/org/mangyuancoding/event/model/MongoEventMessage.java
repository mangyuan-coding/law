package org.mangyuancoding.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.dreamlu.mica.core.utils.$;
import org.mangyuancoding.constitution.message.metadata.MetaData;
import org.mangyuancoding.event.event.EventMessage;
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
     * 负载内容json化
     */
    private String payloadJson;
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

    public MongoEventMessage(EventMessage<?> eventMessage) {
        this.id = eventMessage.getIdentifier();
        this.payloadJson = $.toJson(eventMessage.getPayload());
        this.payloadType = eventMessage.getPayloadType().getName();
        this.instant = eventMessage.getTimestamp();
        this.metaData = eventMessage.getMetaData();
    }
}
