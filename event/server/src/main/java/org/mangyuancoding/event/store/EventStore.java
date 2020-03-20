package org.mangyuancoding.event.store;

import org.mangyuancoding.constitution.message.metadata.MetaData;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/18
 */
public interface EventStore {

    /**
     * 保存事件
     *
     * @param payload  负载
     * @param metaData 元数据
     */
    void save(byte[] payload, MetaData metaData);
}
