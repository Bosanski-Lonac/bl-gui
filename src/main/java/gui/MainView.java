package gui;

import app.NotificationManager;
import app.ServiceController;
import javafx.stage.WindowEvent;
import org.springframework.web.client.RestTemplate;

import javafx.stage.Stage;
import model.FlightOperator;
import model.TicketOperator;
import model.UserOperator;

import java.io.IOException;

public class MainView extends Stage {
	private static MainView instance=null;
	
	private MainView() {
		UserOperator.getInstance().setRestTemplate(new RestTemplate());
		FlightOperator.getInstance().setRestTemplate(UserOperator.getInstance().getRestTemplate());
		TicketOperator.getInstance().setRestTemplate(UserOperator.getInstance().getRestTemplate());
		
		setScene(new LoginSceneWrapper().getScena());
		setWidth(1024);
		setHeight(768);
		setTitle("Bosanski Lonac");
		show();
	}

	public void configureInstance(NotificationManager notificationManager, ServiceController serviceController) throws IOException {
		setOnCloseRequest(event -> {
			notificationManager.stop();
			serviceController.stop();
		});
		LaunchDialog dialog = new LaunchDialog();
		serviceController.start(dialog);
		dialog.showAndWait();
		if (!dialog.getResult()) {
			this.fireEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSE_REQUEST));
		}
	}
	
	public static MainView getInstance() {
		if(instance==null) {
			instance=new MainView();
		}
		return instance;
	}
}
