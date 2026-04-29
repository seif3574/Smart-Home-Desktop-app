package observer;

import model.hub.DeviceEvent;

import java.util.Objects;
import java.util.function.Consumer;

public class DeviceEventListener implements DeviceObserver {
    private final Consumer<DeviceEvent> callback;

    public DeviceEventListener(Consumer<DeviceEvent> callback) {
        this.callback = Objects.requireNonNull(callback, "callback must not be null");
    }

    @Override
    public void onDeviceUpdate(DeviceEvent event) {
        callback.accept(event);
    }
}
