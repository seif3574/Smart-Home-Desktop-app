package model.device;

import model.command.Command;
import model.hub.DeviceEvent;
import util.AppConstants;

import java.time.LocalDateTime;

public final class SmartAC extends SmartDevice {
    private boolean on;

    private double currentTemp;
    private double targetTemp;
    private ACMode mode;
    private FanSpeed fanSpeed;

    public SmartAC(String id, String name, String location, String room) {
        super(id, name, location, room, DeviceStatus.ONLINE);
        this.on=false;
        this.currentTemp = AppConstants.DEFAULT_TEMP;
        this.targetTemp = AppConstants.DEFAULT_TEMP;
        this.mode = ACMode.AUTO;
        this.fanSpeed = FanSpeed.MED;
    }

    public boolean isOn() {
        return on;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public double getTargetTemp() {
        return targetTemp;
    }

    public ACMode getMode() {
        return mode;
    }

    public FanSpeed getFanSpeed() {
        return fanSpeed;
    }


    public void applyPowerState(boolean powered) {
        if (this.on == powered) {
            return;
        }
        this.on = powered;
        notifyObservers(new DeviceEvent(getId(), AppConstants.AC_POWER_CHANGED, powered, LocalDateTime.now()));
    }

    public void applyTargetTemperature(double newTargetTemp) {
        double clamped = clampTemp(newTargetTemp);
        if (Double.compare(this.targetTemp, clamped) == 0) { 
            return;
        }
        this.targetTemp = clamped;
        this.currentTemp = clamped;
        notifyObservers(new DeviceEvent(getId(), AppConstants.TEMP_CHANGED, clamped, LocalDateTime.now()));
    }

    public void applyMode(ACMode newMode) {
        if (this.mode == newMode) {
            return;
        }
        this.mode = newMode;
        notifyObservers(new DeviceEvent(getId(), AppConstants.AC_MODE_CHANGED, newMode, LocalDateTime.now()));
    }

    public void applyFanSpeed(FanSpeed newFanSpeed) {
        if (this.fanSpeed == newFanSpeed) {
            return;
        }
        this.fanSpeed = newFanSpeed;
        notifyObservers(new DeviceEvent(getId(), AppConstants.AC_FAN_CHANGED, newFanSpeed, LocalDateTime.now()));
    }

    @Override
    public void executeCommand(Object command) {
        if (command instanceof Command cmd) {
            cmd.execute();
            return;
        }
        throw new IllegalArgumentException("Unsupported command type: " + command);
    }

    @Override
    public String getStatusSummary() {
        return getName()
                + " | "
                + (on ? "ON" : "OFF")
                + " | "
                + String.format("%.0f°C", targetTemp)
                + " | "
                + mode
                + " | "
                + fanSpeed;
    }

    private static double clampTemp(double value) {
        return Math.min(AppConstants.MAX_TEMP, Math.max(AppConstants.MIN_TEMP, value));
    }
}
