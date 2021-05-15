package app;

import gui.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			ServiceController serviceController = new ServiceController();
			NotificationManager notificationManager = new NotificationManager(getHostServices());
			MainView mainView = MainView.getInstance();
			mainView.setOnCloseRequest(event -> {
				notificationManager.stop();
				serviceController.stop();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
