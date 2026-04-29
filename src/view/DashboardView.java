package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.device.SmartDevice;

import java.util.List;
import java.util.function.Consumer;

public final class DashboardView {
    private final BorderPane root = new BorderPane();
    private final VBox deviceRows = new VBox(8);
    private final Button addDeviceButton = new Button("Add Device");
    private final Button undoButton = new Button("Undo");
    private final Button redoButton = new Button("Redo");

    public DashboardView() {
        root.setPadding(new Insets(16));

        Label title = new Label("SmartHome Controller");
        title.setStyle("-fx-font-size: 22; -fx-font-weight: bold;");

        HBox toolbar = new HBox(8, addDeviceButton, undoButton, redoButton);
        toolbar.setAlignment(Pos.CENTER_LEFT);
        VBox topBox = new VBox(12, title, toolbar);

        ScrollPane scrollPane = new ScrollPane(deviceRows);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(8, 0, 0, 0));

        root.setTop(topBox);
        root.setCenter(scrollPane);
    }

    public Parent build() {
        return root;
    }

    public void setOnAddDevice(Runnable handler) {
        addDeviceButton.setOnAction(event -> handler.run());
    }

    public void setOnUndo(Runnable handler) {
        undoButton.setOnAction(event -> handler.run());
    }

    public void setOnRedo(Runnable handler) {
        redoButton.setOnAction(event -> handler.run());
    }

    public void renderDevices(List<SmartDevice> devices, Consumer<SmartDevice> onControlClick) {
        deviceRows.getChildren().clear();
        for (SmartDevice device : devices) {
            deviceRows.getChildren().add(createRow(device, onControlClick));
        }
    }

    public void setUndoEnabled(boolean enabled) {
        undoButton.setDisable(!enabled);
    }

    public void setRedoEnabled(boolean enabled) {
        redoButton.setDisable(!enabled);
    }

    private HBox createRow(SmartDevice device, Consumer<SmartDevice> onControlClick) {
        Label nameLabel = new Label(device.getName());
        nameLabel.setMinWidth(220);

        Label statusLabel = new Label(device.getStatusSummary());
        HBox.setHgrow(statusLabel, Priority.ALWAYS);
        statusLabel.setMaxWidth(Double.MAX_VALUE);

        Button controlButton = new Button("Control");
        controlButton.setOnAction(event -> onControlClick.accept(device));

        HBox row = new HBox(12, nameLabel, statusLabel, controlButton);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(8));
        row.setStyle("-fx-background-color: #f2f2f2; -fx-background-radius: 6;");
        return row;
    }
}
