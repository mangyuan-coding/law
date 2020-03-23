package org.mangyuancoding.event.subscribe.service;

import org.mangyuancoding.event.subscribe.model.SubscribedEvent;
import org.mangyuancoding.event.subscribe.model.Subscriber;

import java.util.List;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/23
 */
public interface SubscriberService {
    /**
     * 通过订阅的事件类型查询订阅者
     *
     * @param eventType 事件类型
     * @return 监听者
     */
    Iterable<Subscriber> queryByEventType(String eventType);

    /**
     * 保存订阅者信息
     *
     * @param subscriber       订阅者
     * @param subscribedEvents 订阅的事件
     * @return subscriberId
     */
    List<String> store(Subscriber subscriber, List<SubscribedEvent> subscribedEvents);

    /**
     * 为新订阅者发送历史消息
     *
     * @param subscribedEventIds 订阅者订阅的消息
     */
    void sendEvent2NewSubscriber(List<String> subscribedEventIds);
}
