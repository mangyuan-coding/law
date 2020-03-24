package org.mangyuancoding.event.eventstore;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/20
 */
public interface EventMessageRepository extends MongoRepository<EventMessagePO, String> {

    List<EventMessagePO> findAllByPayloadTypeEqualsAndInstantAfter(String payloadType, Instant instant);
}
