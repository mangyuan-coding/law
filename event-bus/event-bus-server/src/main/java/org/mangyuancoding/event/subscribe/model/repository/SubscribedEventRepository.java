package org.mangyuancoding.event.subscribe.model.repository;

import org.mangyuancoding.event.subscribe.model.SubscribedEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/20
 */
public interface SubscribedEventRepository extends MongoRepository<SubscribedEvent, String> {

    List<SubscribedEvent> findAllByEventType(String eventType);

    List<SubscribedEvent> findAllBySubscriberId(String subscriberId);
}
