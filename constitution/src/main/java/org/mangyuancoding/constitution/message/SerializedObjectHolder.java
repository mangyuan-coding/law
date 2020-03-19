package org.mangyuancoding.constitution.message;

import java.util.HashMap;
import java.util.Map;

/**
 * Holder that keeps references to serialized representations of a payload and meta data of a specific message.
 * Typically, this object should not live longer than the message object is is attached to.
 */
public class SerializedObjectHolder {

    private final Message message;
    private final Object payloadGuard = new Object();
    // guarded by "payloadGuard"
    private final Map<Serializer, SerializedObject> serializedPayload = new HashMap<>();

    private final Object metaDataGuard = new Object();
    // guarded by "metaDataGuard"
    private final Map<Serializer, SerializedObject> serializedMetaData = new HashMap<>();

    /**
     * Initialize the holder for the serialized representations of the payload and meta data of given
     * {@code message}
     *
     * @param message The message to initialize the holder for
     */
    public SerializedObjectHolder(Message message) {
        this.message = message;
    }

    @SuppressWarnings("unchecked")
    public <T> SerializedObject<T> serializePayload(Serializer serializer, Class<T> expectedRepresentation) {
        synchronized (payloadGuard) {
            SerializedObject existingForm = serializedPayload.get(serializer);
            if (existingForm == null) {
                SerializedObject<T> serialized = serializer.serialize(message.getPayload(), expectedRepresentation);
                if (message.getPayload() == null) {
                    // make sure the payload type is maintained
                    serialized = new SimpleSerializedObject<>(serialized.getData(),
                                                              serialized.getContentType(),
                                                              serializer.typeForClass(message.getPayloadType()));
                }
                serializedPayload.put(serializer, serialized);
                return serialized;
            } else {
                return serializer.getConverter().convert(existingForm, expectedRepresentation);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> SerializedObject<T> serializeMetaData(Serializer serializer, Class<T> expectedRepresentation) {
        synchronized (metaDataGuard) {
            SerializedObject existingForm = serializedMetaData.get(serializer);
            if (existingForm == null) {
                SerializedObject<T> serialized = serializer.serialize(message.getMetaData(), expectedRepresentation);
                serializedMetaData.put(serializer, serialized);
                return serialized;
            } else {
                return serializer.getConverter().convert(existingForm, expectedRepresentation);
            }
        }
    }
}
