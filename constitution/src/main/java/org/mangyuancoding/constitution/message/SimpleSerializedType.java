package org.mangyuancoding.constitution.message;

import org.mangyuancoding.constitution.support.Assert;

import java.util.Objects;

import static java.lang.String.format;

/**
 * SerializedType implementation that takes its properties as constructor parameters.
 */
public class SimpleSerializedType implements SerializedType {

    private static final SerializedType EMPTY_TYPE = new SimpleSerializedType("empty", null);
    private final String type;
    private final String revisionId;

    /**
     * Returns the type that represents an empty message, of undefined type. The type of such message is "empty" and
     * always has a {@code null} revision.
     *
     * @return the type representing an empty message
     */
    public static SerializedType emptyType() {
        return EMPTY_TYPE;
    }

    /**
     * Initialize with given {@code objectType} and {@code revisionNumber}
     *
     * @param objectType     The description of the serialized object's type
     * @param revisionNumber The revision of the serialized object's type
     */
    public SimpleSerializedType(String objectType, String revisionNumber) {
        Assert.notNull(objectType, () -> "objectType cannot be null");
        this.type = objectType;
        this.revisionId = revisionNumber;
    }

    @Override
    public String getName() {
        return type;
    }

    @Override
    public String getRevision() {
        return revisionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleSerializedType that = (SimpleSerializedType) o;
        return Objects.equals(type, that.type) && Objects.equals(revisionId, that.revisionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, revisionId);
    }

    @Override
    public String toString() {
        return format("SimpleSerializedType[%s] (revision %s)", type, revisionId);
    }
}
