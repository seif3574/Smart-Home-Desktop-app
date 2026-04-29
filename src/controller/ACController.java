package controller;

import javafx.application.Platform;
import model.command.ACSetModeCommand;
import model.command.ACSetTempCommand;
import model.command.ACToggleCommand;
import model.device.ACMode;
import model.device.FanSpeed;
import model.device.SmartAC;
import service.CommandService;
import util.AppConstants;
import view.ACControlView;
import view.ViewManager;

public final class ACController {
    private final SmartAC ac;
    private final ACControlView view;
    private final CommandService commandService;

    public ACController(SmartAC ac, ACControlView view) {
        this(ac, view, CommandService.getInstance());
    }

    public ACController(SmartAC ac, ACControlView view, CommandService commandService) {
        this.ac = ac;
        this.view = view;
        this.commandService = commandService;
    }

    public void init() { // tartebgom b steps lma el 4a4a tft7
        view.configureTemperatureBounds(AppConstants.MIN_TEMP, AppConstants.MAX_TEMP, ac.getTargetTemp());
        wireActions();
        subscribeToEvents();
        refreshViewFromModel();
    }

    public void refreshViewFromModel() { // bttnada b3d kol 7aga
        view.setTemperatureValue(ac.getTargetTemp());  //bn2ra el degree w n update
        view.render(ac);
        view.setUndoEnabled(commandService.canUndo()); // lw fy 7aga f el undostack hn3ml undo
        view.setRedoEnabled(commandService.canRedo());
    }

    private void wireActions() { //btrbot l azrar
        view.setOnPowerToggle(() -> {
            commandService.execute(new ACToggleCommand(ac)); // 3ml undostack
            refreshViewFromModel();
        });

        view.setOnTemperatureCommit(() -> {   //bn4of el kima dlw
            commandService.execute(new ACSetTempCommand(ac, view.getTemperatureValue()));
            refreshViewFromModel();
        });

        for (ACMode mode : ACMode.values()) { // by4of COOL, HEAT, FAN, AUTO
            view.setOnModeSelected(mode, () -> {
                commandService.execute(new ACSetModeCommand(ac, mode)); //lw 3ml cool hnfzo
                refreshViewFromModel();
            });
        }

        for (FanSpeed fanSpeed : FanSpeed.values()) {
            view.setOnFanSpeedSelected(fanSpeed, this::refreshViewFromModel);
        }

        view.setOnUndo(() -> { //el 7ala el adema
            commandService.undo();
            refreshViewFromModel();
        });

        view.setOnRedo(() -> {
            commandService.redo();
            refreshViewFromModel();
        });

        view.setOnBack(() -> ViewManager.getInstance().showDashboard()); // dashborad mn view manager
    }

    private void subscribeToEvents() {
        ac.addObserver(event -> Platform.runLater(this::refreshViewFromModel));
    }
}