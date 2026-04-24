package model.hub;

import model.device.SmartDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class DeviceRegistry {
    private static volatile DeviceRegistry instance;

    private final Map<String, SmartDevice> devices = new ConcurrentHashMap<>();

    private DeviceRegistry() {
    }  //private constructor

    public static DeviceRegistry getInstance() {
        if (instance == null) {
            synchronized (DeviceRegistry.class) {
                if (instance == null) {
                    instance = new DeviceRegistry();
                }
            }
        }
        return instance;
    }  // get instance method

    public void register(SmartDevice device) {
        devices.put(device.getId(), device);
    } //add new device

    public SmartDevice getDevice(String id) {
        return devices.get(id);
    }  //get device by Id

    public List<SmartDevice> getAllDevices() {
        return new ArrayList<>(devices.values());
    } //get all devices

    public SmartDevice removeDevice(String id) {
        return devices.remove(id);
    } // remove device by id
}
