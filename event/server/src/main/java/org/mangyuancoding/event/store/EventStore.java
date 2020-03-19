package org.mangyuancoding.event.store;

import org.mangyuancoding.event.event.EventMessage;

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
     * @param eventMessage 事件
     * @param <T>          事件负载类型
     */
    <T> void save(EventMessage<T> eventMessage);
}
