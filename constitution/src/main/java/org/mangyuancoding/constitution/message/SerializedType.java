package org.mangyuancoding.constitution.message;

/**
 * Describes the type of a serialized object. This information is used to decide how to deserialize an object.
 */
public interface SerializedType {

    /**
     * Returns the type that represents an empty message, of undefined type. The type of such message is "empty" and
     * always has a {@code null} revision.
     *
     * @return the type representing an empty message
     */
    static SerializedType emptyType() {
        return SimpleSerializedType.emptyType();
    }

    /**
     * Check whether the {@code serializedType} equals {@link SerializedType#emptyType#getName()} and returns a
     * corresponding {@code true} or {@code false} whether this is the case or not. The given {@code serializedType}
     * should not be {@code null} as otherwise a {@link NullPointerException} will be thrown.
     *
     * @param serializedType the type to check whether it equals {@link SerializedType#emptyType()}
     * @return {@code true} if the {@code serializedType} does equals the {@link SerializedType#emptyType()#getName()}
     * and {@code false} if it does
     *
     * @throws NullPointerException if the given {@link SerializedType} is {@code null}
     */
    static boolean isEmptyType(SerializedType serializedType) {
        return emptyType().getName().equals(serializedType.getName());
    }

    /**
     * Returns the name of the serialized type. This may be the class name of the serialized object, or an alias for
     * that name.
     *
     * @return the name of the serialized type
     */
    String getName();

    /**
     * Returns the revision identifier of the serialized object. This revision identifier is used by upcasters to
     * decide how to transform serialized objects during deserialization.
     *
     * @return the revision identifier of the serialized object
     */
    String getRevision();
}
