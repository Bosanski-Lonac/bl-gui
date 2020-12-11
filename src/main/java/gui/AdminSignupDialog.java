package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AdminSignupDialog extends Dialog<Boolean> {
	private Label usernameLbl;
	private TextField usernameTf;
	private Label passwordLbl;
	private TextField passwordTf;
	
	private Button ok;
	private Button cancel;
	
	private HBox buttons;
	
	public AdminSignupDialog() {
		super();
		
		BorderPane pozadina=new BorderPane();
		
		usernameLbl=new Label("Username");
		usernameTf=new TextField();
		passwordLbl=new Label("Password");
		passwordTf=new TextField();
		
		GridPane forma=new GridPane();
		forma.setVgap(8);
		forma.setHgap(8);
		forma.setAlignment(Pos.CENTER);
		
		forma.add(usernameLbl, 0, 0);
		forma.add(usernameTf, 0, 1);
		forma.add(passwordLbl, 0, 2);
		forma.add(passwordTf, 0, 3);
		
		pozadina.setCenter(forma);
		
		ok=new Button("Ok");
		cancel=new Button("Cancel");
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Stage stage=(Stage)cancel.getScene().getWindow();
				stage.close();
			}
		});
		
		buttons=new HBox(ok, cancel);
		buttons.setAlignment(Pos.CENTER);
		
		pozadina.setBottom(buttons);
		
		getDialogPane().setContent(pozadina);
		getDialogPane().setPrefWidth(400);
		getDialogPane().setPrefHeight(600);
		setTitle("Admin sign up");
	}
}
