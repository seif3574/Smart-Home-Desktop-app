package observer;

public interface DeviceObserver {
    void onDeviceUpdate(model.hub.DeviceEvent event);
}
