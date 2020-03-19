package org.mangyuancoding.constitution.message;

/**
 * Interface describing a serialization mechanism. Implementations can serialize objects of given type {@code T}
 * to an output stream and read the object back in from an input stream.
 */
public interface Serializer {

    /**
     * Serialize the given {@code object} into a Serialized Object containing the given
     * {@code expectedRepresentation}.
     * <p/>
     * Use {@link #canSerializeTo(Class)} to detect whether the {@code expectedRepresentation} is supported by
     * this serializer.
     *
     * @param object                 The object to serialize
     * @param expectedRepresentation The expected data type representing the serialized object
     * @param <T>                    The expected data type representing the serialized object
     * @return the instance representing the serialized object.
     */
    <T> SerializedObject<T> serialize(Object object, Class<T> expectedRepresentation);

    /**
     * Indicates whether this Serializer is capable of serializing to the given {@code expectedRepresentation}.
     * <p/>
     * When {@code true}, this does *not* guarantee that the serialization and (optional) conversion will also
     * succeed when executed. For example, when a serializer produces a {@code byte[]} containing JSON, trying to
     * convert to a Dom4J Document will fail, even though this serializer has a converter to convert
     * {@code byte[]}
     * to Dom4J instances.
     *
     * @param expectedRepresentation The type of data a Serialized Object should contain
     * @param <T>                    The type of data a Serialized Object should contain
     * @return {@code true} if the {@code expectedRepresentation} is supported, otherwise {@code false}.
     */
    <T> boolean canSerializeTo(Class<T> expectedRepresentation);

    /**
     * Deserializes the first object read from the given {@code bytes}. The {@code bytes} are not consumed
     * from the array or modified in any way. The resulting object instance is cast to the expected type.
     *
     * @param serializedObject the instance describing the type of object and the bytes providing the serialized data
     * @param <S>              The data type of the serialized object
     * @param <T>              The expected deserialized type
     * @return the serialized object, cast to the expected type
     *
     * @throws ClassCastException if the first object in the stream is not an instance of &lt;T&gt;.
     */
    <S, T> T deserialize(SerializedObject<S> serializedObject);

    /**
     * Returns the class for the given type identifier. The result of this method must guarantee that the deserialized
     * SerializedObject with the given {@code type} is an instance of the returned Class.
     * <p/>
     * @param type The type identifier of the object
     * @return the Class representing the type of the serialized Object
     */
    Class classForType(SerializedType type);

    /**
     * Returns the type identifier for the given class. This is the type identifier of the Serialized object as
     * returned by {@link #serialize(Object, Class)}.
     *
     * @param type Class representing the type of the serializable Object.
     * @return The type identifier of the object
     */
    SerializedType typeForClass(Class type);

    /**
     * Returns the {@link Converter} used by this Serializer to convert between serialized representations. Generally,
     * this Converter depends on the type of data the serializer serializes to.
     *
     * @return the converter used by this Serializer
     */
    Converter getConverter();
}
