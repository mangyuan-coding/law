package org.mangyuancoding.event.send;


import org.mangyuancoding.constitution.message.metadata.MetaData;

/**
 * Description
 * 发布器
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/24
 */
public interface Sender {

    /**
     * 直接发送事件到事件服务器
     *
     * @param event 事件
     */
    default void publish(Object event) {
        publish(event, MetaData.emptyInstance());
    }

    /**
     * 发送消息，以及一些元数据到事件服务器
     *
     * @param event    事件
     * @param metaData 元数据
     */
    void publish(Object event, MetaData metaData);

    /**
     * 发送给指定接收者
     *
     * @param event               事件
     * @param metaData            元数据
     * @param receiverDescription 接收者描述
     */
    void send(Object event, MetaData metaData, ReceiverDescription receiverDescription);

    /**
     * 发送流式数据
     *
     * @param payload             json字节流
     * @param metaData            元数据
     * @param receiverDescription 接收者描述
     */
    void send(byte[] payload, MetaData metaData, ReceiverDescription receiverDescription);
}
