package gui;

import org.springframework.web.client.RestTemplate;

import javafx.stage.Stage;
import model.FlightOperator;
import model.TicketOperator;
import model.UserOperator;

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
	
	public static MainView getInstance() {
		if(instance==null) {
			instance=new MainView();
		}
		return instance;
	}
}
