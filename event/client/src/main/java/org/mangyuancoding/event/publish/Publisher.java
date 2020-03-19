package org.mangyuancoding.event.publish;

import org.mangyuancoding.constitution.message.metadata.MetaData;

/**
 * Description
 * 消息发布器
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */
public interface Publisher {

    /**
     * 直接发送事件到事件服务器
     *
     * @param event 事件
     */
    default void publish(Object event) {
        publish(event, MetaData.emptyInstance());
    }

    /**
     * 发送消息，已经一些元数据到事件服务器
     *
     * @param event    事件
     * @param metaData 元数据
     */
    void publish(Object event, MetaData metaData);
}
