package com.payu.paga.type;

/**
 * Created by akesh.patil on 27-03-2017.
 */
public enum EventType {
    INSERT,
    MODIFY;

    public static boolean isInsertEvent(String eventName) {
        return INSERT.toString().equalsIgnoreCase(eventName);
    }

    public static boolean isUpdateEvent(String eventName) {
        return MODIFY.toString().equalsIgnoreCase(eventName);
    }
}
