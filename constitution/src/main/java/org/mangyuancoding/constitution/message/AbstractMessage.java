package org.mangyuancoding.constitution.message;

import org.mangyuancoding.constitution.message.metadata.MetaData;

import java.util.Map;

/**
 * Abstract base class for Messages.
 */
public abstract class AbstractMessage<T> implements Message<T> {

    private static final long serialVersionUID = -5847906865361406657L;
    private final String identifier;

    /**
     * Initializes a new message with given identifier.
     *
     * @param identifier the message identifier
     */
    public AbstractMessage(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public Message<T> withMetaData(Map<String, ?> metaData) {
        if (getMetaData().equals(metaData)) {
            return this;
        }
        return withMetaData(MetaData.from(metaData));
    }

    @Override
    public Message<T> andMetaData(Map<String, ?> metaData) {
        if (metaData.isEmpty()) {
            return this;
        }
        return withMetaData(getMetaData().mergedWith(metaData));
    }

    /**
     * Returns a new message instance with the same payload and properties as this message but given {@code metaData}.
     *
     * @param metaData The metadata in the new message
     * @return a copy of this instance with given metadata
     */
    protected abstract Message<T> withMetaData(MetaData metaData);
}
