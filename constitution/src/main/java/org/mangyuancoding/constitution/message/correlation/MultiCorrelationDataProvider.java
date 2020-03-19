package org.mangyuancoding.constitution.message.correlation;

import org.mangyuancoding.constitution.message.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CorrelationDataProvider that combines the data of multiple other correlation providers. When multiple instance
 * provide the same keys, a delegate will override the entries provided by previously resolved delegates.
 *
 * @author Allard Buijze
 * @since 2.3
 */
public class MultiCorrelationDataProvider<T extends Message<?>> implements CorrelationDataProvider {

    private final List<? extends CorrelationDataProvider> delegates;

    /**
     * Initialize the correlation data provider, delegating to given {@code correlationDataProviders}.
     *
     * @param correlationDataProviders the providers to delegate to.
     */
    public MultiCorrelationDataProvider(List<? extends CorrelationDataProvider> correlationDataProviders) {
        delegates = new ArrayList<>(correlationDataProviders);
    }

    @Override
    public Map<String, ?> correlationDataFor(Message<?> message) {
        Map<String, Object> correlationData = new HashMap<>();
        for (CorrelationDataProvider delegate : delegates) {
            final Map<String, ?> extraData = delegate.correlationDataFor(message);
            if (extraData != null) {
                correlationData.putAll(extraData);
            }
        }
        return correlationData;
    }
}
