package controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import model.device.SmartAC;
import model.device.SmartDevice;
import observer.DeviceEventListener;
import service.CommandService;
import service.DeviceService;
import util.AppConstants;
import util.EventBus;
import view.DashboardView;
import view.ViewManager;

public final class DashboardController {
    private final DashboardView view;
    private final DeviceService deviceService;
    private final CommandService commandService;
    private final EventBus eventBus;

    public DashboardController(DashboardView view) {
        this(view, new DeviceService(), CommandService.getInstance(), EventBus.getInstance());
    }

    public DashboardController(
            DashboardView view,
            DeviceService deviceService,
            CommandService commandService,
            EventBus eventBus
    ) {
        this.view = view;
        this.deviceService = deviceService;
        this.commandService = commandService;
        this.eventBus = eventBus;
    }

    public void init() {
        wireActions();
        subscribeToEvents();
        refreshDashboard();
    }

    public void refreshDashboard() {
        view.renderDevices(deviceService.getAllDevices(), this::onControlClick);
        view.setUndoEnabled(commandService.canUndo());
        view.setRedoEnabled(commandService.canRedo());
    }

    private void wireActions() {
        view.setOnAddDevice(() -> ViewManager.getInstance().showAddDevice());
        view.setOnUndo(() -> {
            commandService.undo();
            refreshDashboard();
        });
        view.setOnRedo(() -> {
            commandService.redo();
            refreshDashboard();
        });
    }

    private void subscribeToEvents() {
        eventBus.subscribe(AppConstants.DEVICE_ADDED, event -> Platform.runLater(this::refreshDashboard));
        eventBus.subscribe(AppConstants.DEVICE_STATUS_CHANGED, event -> Platform.runLater(this::refreshDashboard));

        DeviceEventListener listener = new DeviceEventListener(event -> Platform.runLater(this::refreshDashboard));
        for (SmartDevice device : deviceService.getAllDevices()) {
            device.addObserver(listener);
        }
    }

    private void onControlClick(SmartDevice device) {
        if (device instanceof SmartAC smartAC) {
            ViewManager.getInstance().showACControl(smartAC);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Control screen is available for AC devices only.");
        alert.setHeaderText("Unsupported device");
        alert.showAndWait();
    }
}
