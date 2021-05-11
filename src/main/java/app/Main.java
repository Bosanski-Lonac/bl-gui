package app;

import gui.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		NotificationManager notificationManager = new NotificationManager(getHostServices());
		MainView mainView = MainView.getInstance();
		mainView.setOnCloseRequest(event -> notificationManager.stop());
	}
}
