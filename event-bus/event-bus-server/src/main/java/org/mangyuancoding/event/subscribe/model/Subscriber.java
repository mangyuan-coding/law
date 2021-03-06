package org.mangyuancoding.event.subscribe.model;

import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mangyuancoding.event.send.ReceiverDescription;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Description
 * 订阅者
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
public class Subscriber implements ReceiverDescription {

    @Id
    private String id;
    /**
     * 订阅者的名称
     */
    private String name;
    /**
     * 订阅者mq的交换机
     */
    private String exchange;
    /**
     * 路由
     */
    private String routingKey;

    @Override
    public String getQueue() {
        return StringUtil.EMPTY_STRING;
    }
}
