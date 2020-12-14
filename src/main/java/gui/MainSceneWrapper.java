package gui;

import dto.LetDto;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class MainSceneWrapper extends SceneWrapper {
	
	private TableView<LetDto> flights;
	
	public MainSceneWrapper(String username) {
		BorderPane pozadina=new BorderPane();
		
		flights = new TableView<>();
		
		pozadina.setCenter(flights);
		
		this.scena=new Scene(pozadina);
	}
}
