package org.mangyuancoding.event.subscribe.model.repository;

import org.mangyuancoding.event.subscribe.model.Subscriber;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/20
 */
public interface SubscriberRepository extends MongoRepository<Subscriber, String> {

    Subscriber findByName(String name);
}
