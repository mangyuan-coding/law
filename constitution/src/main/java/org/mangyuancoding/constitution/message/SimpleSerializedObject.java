package org.mangyuancoding.constitution.message;

import org.springframework.util.Assert;

import java.util.Objects;

import static java.lang.String.format;

/**
 * SerializedObject implementation that takes all properties as constructor parameters.
 *
 * @param <T> The data type representing the serialized object
 */
public class SimpleSerializedObject<T> implements SerializedObject<T> {

    private final T data;
    private final SerializedType type;
    private final Class<T> dataType;

    /**
     * Initializes a SimpleSerializedObject using given {@code data} and {@code serializedType}.
     *
     * @param data           The data of the serialized object
     * @param dataType       The type of data
     * @param serializedType The type description of the serialized object
     */
    public SimpleSerializedObject(T data, Class<T> dataType, SerializedType serializedType) {
        Assert.notNull(data, () -> "Data for a serialized object cannot be null");
        Assert.notNull(serializedType, () -> "The type identifier of the serialized object");
        this.data = data;
        this.dataType = dataType;
        this.type = serializedType;
    }

    /**
     * Initializes a SimpleSerializedObject using given {@code data} and a serialized type identified by given
     * {@code type} and {@code revision}.
     *
     * @param data     The data of the serialized object
     * @param dataType The type of data
     * @param type     The type identifying the serialized object
     * @param revision The revision of the serialized object
     */
    public SimpleSerializedObject(T data, Class<T> dataType, String type, String revision) {
        this(data, dataType, new SimpleSerializedType(type, revision));
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public Class<T> getContentType() {
        return dataType;
    }

    @Override
    public SerializedType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleSerializedObject<?> that = (SimpleSerializedObject<?>) o;
        return Objects.equals(data, that.data) && Objects.equals(type, that.type) &&
                Objects.equals(dataType, that.dataType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, type, dataType);
    }

    @Override
    public String toString() {
        return format("SimpleSerializedObject [%s]", type);
    }
}
