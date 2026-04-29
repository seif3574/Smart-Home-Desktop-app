package view;

import model.device.ACMode;
import model.device.FanSpeed;
import model.device.SmartAC;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.EnumMap;
import java.util.Map;

public final class ACControlView {
    private final BorderPane root = new BorderPane();


    private final Label deviceNameLabel = new Label();
    private final Label statusSummaryLabel = new Label();
    private final Label targetValueLabel = new Label();


    private final Slider temperatureSlider = new Slider();
    private final ToggleGroup modeGroup = new ToggleGroup();
    private final Map<ACMode, ToggleButton> modeButtons = new EnumMap<>(ACMode.class);
    private final ToggleGroup fanSpeedGroup = new ToggleGroup();
    private final Map<FanSpeed, ToggleButton> fanSpeedButtons = new EnumMap<>(FanSpeed.class);


    private final Button powerToggleButton = new Button("Toggle Power");
    private final Button undoButton = new Button("Undo");
    private final Button redoButton = new Button("Redo");
    private final Button backButton = new Button("Back");

    public ACControlView() { // msafa mn kol n7ya
        root.setPadding(new Insets(16));

        //  Header Section
        deviceNameLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        statusSummaryLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #555555;");
        VBox topBox = new VBox(8, deviceNameLabel, statusSummaryLabel);  // vertical
        topBox.setPadding(new Insets(0, 0, 16, 0));

        //  Control Section (Mode, Speed, Temperature)
        VBox controlContainer = new VBox(16);
        controlContainer.getChildren().addAll(
                createSection("Operation Mode", createModeSelector()),
                createSection("Fan Speed", createFanSpeedSelector()),
                createSection("Target Temperature", createTemperatureSliderBox())
        );

        //  Toolbar Section
        HBox toolbar = new HBox(8, powerToggleButton, undoButton, redoButton, backButton); // trteb el buttons
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setPadding(new Insets(20, 0, 0, 0));

        root.setTop(topBox);
        root.setCenter(controlContainer);
        root.setBottom(toolbar);
    }

    public Parent build() {
        return root;
    }

    //  Rendering

    public void render(SmartAC ac) {
        deviceNameLabel.setText(ac.getName() + " - " + ac.getRoom());  //update name

        String powerStatus = ac.isOn() ? "ON" : "OFF";
        statusSummaryLabel.setText(String.format("Status: %s | Current: %.1f°C", powerStatus, ac.getCurrentTemp()));

        targetValueLabel.setText(String.format("%.0f°C", ac.getTargetTemp()));  // update text of temp
        temperatureSlider.setValue(ac.getTargetTemp()); //update slider to the target

        modeGroup.selectToggle(modeButtons.get(ac.getMode()));
        fanSpeedGroup.selectToggle(fanSpeedButtons.get(ac.getFanSpeed()));
    }

    //  Helper UI Builders

    private VBox createSection(String title, Parent content) {
        Label label = new Label(title);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");
        return new VBox(6, label, content);
    }

    private HBox createModeSelector() {
        HBox box = new HBox(8);
        for (ACMode mode : ACMode.values()) {
            ToggleButton btn = new ToggleButton(mode.name());
            btn.setToggleGroup(modeGroup);
            btn.setMinWidth(80);
            modeButtons.put(mode, btn);
            box.getChildren().add(btn);
        }
        return box;
    }

    private HBox createFanSpeedSelector() {
        HBox box = new HBox(8);
        for (FanSpeed speed : FanSpeed.values()) {
            ToggleButton btn = new ToggleButton(speed.name());
            btn.setToggleGroup(fanSpeedGroup);
            btn.setMinWidth(80);
            fanSpeedButtons.put(speed, btn);
            box.getChildren().add(btn);
        }
        return box;
    }

    private VBox createTemperatureSliderBox() {
        temperatureSlider.setShowTickLabels(true);
        temperatureSlider.setShowTickMarks(true);
        temperatureSlider.setSnapToTicks(true);
        temperatureSlider.setMajorTickUnit(1.0);
        temperatureSlider.setBlockIncrement(1.0);

        targetValueLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #2a9df4;");
        VBox box = new VBox(4, temperatureSlider, targetValueLabel);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    // --- Event Handlers (Matching DashboardView Style) ---

    public void setOnPowerToggle(Runnable handler) {
        powerToggleButton.setOnAction(e -> handler.run());
    }

    public void setOnTemperatureCommit(Runnable handler) {
        temperatureSlider.setOnMouseReleased(e -> handler.run());
    }

    public void setOnModeSelected(ACMode mode, Runnable handler) {
        modeButtons.get(mode).setOnAction(e -> handler.run());
    }

    public void setOnFanSpeedSelected(FanSpeed speed, Runnable handler) {
        fanSpeedButtons.get(speed).setOnAction(e -> handler.run());
    }

    public void setOnUndo(Runnable handler) { undoButton.setOnAction(e -> handler.run()); }
    public void setOnRedo(Runnable handler) { redoButton.setOnAction(e -> handler.run()); }
    public void setOnBack(Runnable handler) { backButton.setOnAction(e -> handler.run()); }

    public void setUndoEnabled(boolean enabled) { undoButton.setDisable(!enabled); }
    public void setRedoEnabled(boolean enabled) { redoButton.setDisable(!enabled); }

    public void configureTemperatureBounds(double min, double max) {
        temperatureSlider.setMin(min);
        temperatureSlider.setMax(max);
    }

    public double getTemperatureValue() {
        return temperatureSlider.getValue();
    }
}