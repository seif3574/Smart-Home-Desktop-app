package model.hub;

import java.time.LocalDateTime;

public final class DeviceEvent {
    private final String deviceId;
    private final String eventType;
    private final Object payload;
    private final LocalDateTime timestamp;

    public DeviceEvent(String deviceId, String eventType, Object payload, LocalDateTime timestamp) {
        this.deviceId = deviceId;
        this.eventType = eventType;
        this.payload = payload;
        this.timestamp = timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getEventType() {
        return eventType;
    }

    public Object getPayload() {
        return payload;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
