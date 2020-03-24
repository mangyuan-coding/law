package org.mangyuancoding.event.subscribe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
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
public class SubscribedEvent {

    @Id
    private String id;
    /**
     * 订阅者id
     */
    private String subscriberId;
    /**
     * 消息类型
     */
    private String eventType;

    /**
     * 订阅的事件的开始时间
     */
    private Instant eventStartTime;
    /**
     * 上次发送的事件的事件
     */
    private Instant lastSendEventTime;
}
