package service;

import model.device.DeviceStatus;
import model.device.SmartDevice;
import model.hub.DeviceEvent;
import model.hub.DeviceRegistry;
import util.AppConstants;
import util.EventBus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class DeviceService {
    private final DeviceRegistry registry;
    private final EventBus eventBus;

    public DeviceService() {
        this(DeviceRegistry.getInstance(), EventBus.getInstance());
    }

    public DeviceService(DeviceRegistry registry, EventBus eventBus) {
        this.registry = Objects.requireNonNull(registry, "registry must not be null");
        this.eventBus = Objects.requireNonNull(eventBus, "eventBus must not be null");
    }

    public List<SmartDevice> getAllDevices() {
        return registry.getAllDevices();
    }

    public List<SmartDevice> getDevicesByRoom(String roomId) {
        return registry.getAllDevices()
                .stream()
                .filter(device -> roomId.equalsIgnoreCase(device.getRoom()))
                .collect(Collectors.toList());
    }

    public void updateDeviceStatus(String id, DeviceStatus status) {
        SmartDevice device = registry.getDevice(id);
        if (device == null) {
            return;
        }

        device.setStatus(status);
        eventBus.publish(new DeviceEvent(id, AppConstants.DEVICE_STATUS_CHANGED, status, LocalDateTime.now()));
    }
}
