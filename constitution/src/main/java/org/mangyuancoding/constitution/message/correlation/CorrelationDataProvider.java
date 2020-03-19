package org.mangyuancoding.constitution.message.correlation;

import org.mangyuancoding.constitution.message.Message;

import java.util.Map;

/**
 * Object defining the data from a Message that should be attached as correlation data to messages generated as
 * result of the processing of that message.
 */
@FunctionalInterface
public interface CorrelationDataProvider {

    /**
     * Provides a map with the entries to attach as correlation data to generated messages while processing given
     * {@code message}.
     * <p/>
     * This method should not return {@code null}.
     *
     * @param message The message to define correlation data for
     * @return the data to attach as correlation data to generated messages
     */
    Map<String, ?> correlationDataFor(Message<?> message);
}
