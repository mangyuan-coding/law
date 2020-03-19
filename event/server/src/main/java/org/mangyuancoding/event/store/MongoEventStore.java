package org.mangyuancoding.event.store;

import org.mangyuancoding.event.event.EventMessage;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Description
 * Author niumangyuan
 * Email niumangyuan@vcredit.com
 * Date 2020/3/19
 */
public class MongoEventStore implements EventStore {

    private static final String EVENT_COLLECTION = "event-store";

    private MongoTemplate mongoTemplate;

    public MongoEventStore(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public <T> void save(EventMessage<T> eventMessage) {
        mongoTemplate.save(eventMessage, EVENT_COLLECTION);
    }
}
