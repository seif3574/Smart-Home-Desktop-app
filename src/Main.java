import javafx.application.Application;
import javafx.stage.Stage;
import model.device.SmartAC;
import model.hub.DeviceRegistry;
import view.ViewManager;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        bootstrapDevices();
        ViewManager.getInstance().init(primaryStage);
        ViewManager.getInstance().showDashboard();
    }

    private static void bootstrapDevices() {
        DeviceRegistry registry = DeviceRegistry.getInstance();
        if (!registry.getAllDevices().isEmpty()) {
            return;
        }

        registry.register(new SmartAC("ac1", "Living Room AC", "Living Room", "Living Room"));
        registry.register(new SmartAC("ac2", "Bedroom AC", "Bedroom", "Bedroom"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}


//hello