package gui;

import org.springframework.web.client.RestTemplate;

import javafx.stage.Stage;
import model.UserOperator;

public class MainView extends Stage {
	private static MainView instance=null;
	
	private MainView() {
		UserOperator.getInstance().setRestTemplate(new RestTemplate());
		
		setScene(new LoginSceneWrapper().getScena());
		setWidth(400);
		setHeight(400);
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
