package app;

import gui.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

	public static App instance = null;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		instance = this;
		ServiceController serviceController = new ServiceController();
		NotificationManager notificationManager = new NotificationManager(getHostServices());
		MainView mainView = MainView.getInstance();
		try {
			mainView.configureInstance(notificationManager, serviceController);
		} catch (IOException e) {
			notificationManager.stop();
			serviceController.stop();
		}
	}

	public static App getInstance(){
		if(instance == null) {
			instance = new App();
		}
		return instance;
	}
}
