package model.device;

import model.hub.DeviceEvent;
import observer.DeviceObserver;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class SmartDevice {
    private final String id;
    private final String name;
    private final String location;
    private String room;
    private DeviceStatus status;
    private final List<DeviceObserver> observers = new CopyOnWriteArrayList<>();

    protected SmartDevice(String id, String name, String location, String room, DeviceStatus status) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.room = room;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public void addObserver(DeviceObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(DeviceObserver observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(DeviceEvent event) {
        for (DeviceObserver observer : observers) {
            observer.onDeviceUpdate(event);
        }
    }

    public abstract void executeCommand(Object command);

    public abstract String getStatusSummary();
}
