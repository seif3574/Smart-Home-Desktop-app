package model.command;

import model.device.SmartAC;

public final class ACSetTempCommand implements Command {
    private final SmartAC ac;

    private double prevTemp;

    private final double newTemp;

    public ACSetTempCommand(SmartAC ac, double newTemp) {
        this.ac = ac;
        this.newTemp = newTemp;
    }

    @Override
    public void execute() {
        prevTemp=ac.getTargetTemp();
       ac.applyTargetTemperature(newTemp);
    }
    /*Initial state:
targetTemp = 22
Step 1:
prevTemp = ac.getTargetTemp();

👉 prevTemp = 22

Step 2:
ac.applyTargetTemperature(25);

👉 SmartAC updates:

targetTemp = 25
currentTemp = 25
✅ After execute:
targetTemp = 25
prevTemp = 22*/

    @Override
    public void undo() {
        ac.applyTargetTemperature(prevTemp);
    }

}
