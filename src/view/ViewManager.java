package view;

import controller.DashboardController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.device.SmartAC;
import util.AppConstants;

public final class ViewManager {
    private static volatile ViewManager instance;
    private Stage primaryStage;

    private ViewManager() {
    }

    public static ViewManager getInstance() {
        if (instance == null) {
            synchronized (ViewManager.class) {
                if (instance == null) {
                    instance = new ViewManager();
                }
            }
        }
        return instance;
    }

    public void init(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle(AppConstants.APP_NAME);
    }

    public void showDashboard() {
        DashboardView dashboardView = new DashboardView();
        DashboardController dashboardController = new DashboardController(dashboardView);
        dashboardController.init();

        primaryStage.setScene(new Scene(dashboardView.build(), 980, 640));
        primaryStage.show();
    }

    public void showACControl(SmartAC ac) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "AC Control (Member 4 scope) is not implemented in Member 3 tasks.\nSelected: " + ac.getName());
        alert.setHeaderText("Navigation ready");
        alert.showAndWait();
    }

    public void showAddDevice() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Add Device flow (Member 5 scope) is not implemented in Member 3 tasks.");
        alert.setHeaderText("Navigation ready");
        alert.showAndWait();
    }
}
