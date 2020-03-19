package org.mangyuancoding.eventsourcing;

import org.mangyuancoding.constitution.message.metadata.MetaData;

/**
 * Description
 * 消息发布器
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */
public interface Publisher {

    default void publish(Object event) {
        publish(event, MetaData.emptyInstance());
    }

    void publish(Object event, MetaData metaData);
}
