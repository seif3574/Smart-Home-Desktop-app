package model.command;

import model.device.ACMode;
import model.device.SmartAC;

public final class ACSetModeCommand implements Command {
    private final SmartAC ac;
    private final ACMode newMode;
    private ACMode prevMode;

    public ACSetModeCommand(SmartAC ac, ACMode newMode) {
        this.ac = ac;
        this.newMode = newMode;
    }

    @Override
    public void execute() {
        prevMode=ac.getMode();
       ac.applyMode(newMode);
    }

    @Override
    public void undo() {
        ac.applyMode(prevMode);
    }
}
