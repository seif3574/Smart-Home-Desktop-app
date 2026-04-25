package model.command;

import model.device.SmartAC;

public final class ACToggleCommand implements Command {
    private final SmartAC ac;
    private boolean prevState;

    public ACToggleCommand(SmartAC ac) {
        this.ac = ac;
    }

    @Override
    public void execute() { //change ac if on to off , if off to on and save old state for undo
        prevState = ac.isOn();
        boolean nextState = !prevState;
        ac.applyPowerState(nextState);
    }

    @Override
    public void undo() {
        ac.applyPowerState(prevState);
    }


}
