package org.mangyuancoding.event.subscribe.model.repository;

import org.mangyuancoding.event.subscribe.model.MongoEventMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/20
 */
public interface MongoEventMessageRepository extends MongoRepository<MongoEventMessage, String> {

    List<MongoEventMessage> findAllByPayloadTypeEqualsAndInstantAfter(String payloadType, Instant instant);
}
