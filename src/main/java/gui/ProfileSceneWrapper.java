package gui;

import org.springframework.web.client.HttpClientErrorException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.UserOperator;

public class ProfileSceneWrapper extends SceneWrapper {
	private Scene glavniEkran;
	
	private Button cancel;
	private Button save;
	
	private HBox bottom;
	
	public ProfileSceneWrapper(Scene glavniEkran) {
		this.glavniEkran = glavniEkran;
		BorderPane pozadina=new BorderPane();
		
		cancel = new Button("Poništi");
		cancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainView.getInstance().setScene(glavniEkran);
			}
			
		});
		save = new Button("Sačuvaj");
		save.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Napraviti korisnicki create Dto
				try {
					UserOperator.getInstance().updateUserInfo(null);
				} catch (HttpClientErrorException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Communication Error");
					alert.setHeaderText(null);
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
				MainView.getInstance().setScene(glavniEkran);
			}
			
		});
		
		bottom = new HBox(save, cancel);
		
		pozadina.setBottom(bottom);
		this.scena = new Scene(pozadina);
	}
}
