package org.mangyuancoding.constitution.message;

/**
 * Interface describing a mechanism that can convert data from one to another type.
 */
public interface Converter {

    /**
     * Indicates whether this converter is capable of converting the given {@code sourceType} to the {@code targetType}.
     *
     * @param sourceType The type of data to convert from
     * @param targetType The type of data to convert to
     * @return {@code true} if conversion is possible, {@code false} otherwise
     */
    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    /**
     * Converts the given object into another.
     *
     * @param original   the value to convert
     * @param targetType The type of data to convert to
     * @param <T>        the target data type
     * @return the converted value
     */
    default <T> T convert(Object original, Class<T> targetType) {
        return convert(original, original.getClass(), targetType);
    }

    /**
     * Converts the given object into another using the source type to find the conversion path.
     *
     * @param original   the value to convert
     * @param sourceType the type of data to convert
     * @param targetType The type of data to convert to
     * @param <T>        the target data type
     * @return the converted value
     */
    <T> T convert(Object original, Class<?> sourceType, Class<T> targetType);

    /**
     * Converts the data format of the given {@code original} IntermediateRepresentation to the target data type.
     *
     * @param original   The source to convert
     * @param targetType The type of data to convert to
     * @param <T>        the target data type
     * @return the converted representation
     */
    @SuppressWarnings("unchecked")
    default <T> SerializedObject<T> convert(SerializedObject<?> original, Class<T> targetType) {
        if (original.getContentType().equals(targetType)) {
            return (SerializedObject<T>) original;
        }
        return new SimpleSerializedObject<>(convert(original.getData(), original.getContentType(), targetType),
                targetType, original.getType());
    }

}
