package org.mangyuancoding.constitution.message.correlation;

import org.mangyuancoding.constitution.message.Message;
import org.mangyuancoding.constitution.message.metadata.MetaData;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * CorrelationDataProvider implementation defines correlation headers by the header names. The headers from messages
 * with these keys are returned as correlation data.
 */
public class SimpleCorrelationDataProvider implements CorrelationDataProvider {

    private final String[] headerNames;

    /**
     * Initializes the CorrelationDataProvider to return the meta data of messages with given {@code metaDataKeys}
     * as correlation data.
     *
     * @param metaDataKeys The keys of the meta data entries from messages to return as correlation data
     */
    public SimpleCorrelationDataProvider(String... metaDataKeys) {
        this.headerNames = Arrays.copyOf(metaDataKeys, metaDataKeys.length);
    }

    @Override
    public Map<String, ?> correlationDataFor(Message<?> message) {
        if (headerNames.length == 0) {
            return Collections.emptyMap();
        }
        Map<String, Object> data = new HashMap<>();
        final MetaData metaData = message.getMetaData();
        for (String headerName : headerNames) {
            if (metaData.containsKey(headerName)) {
                data.put(headerName, metaData.get(headerName));
            }
        }
        return data;
    }
}
