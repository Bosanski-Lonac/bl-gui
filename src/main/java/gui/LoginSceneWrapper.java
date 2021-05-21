package gui;

import app.App;
import controller.LoginAction;
import controller.SignupAction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class LoginSceneWrapper extends SceneWrapper {
	private Button signupBtn;
	private Button loginBtn;
	private CheckBox cbAdmin;
	
	private Label usernameLbl;
	private TextField usernameTf;
	private Label passwordLbl;
	private PasswordField passwordTf;

	private Hyperlink hlUputstvo;
	
	public LoginSceneWrapper() {
		BorderPane pozadina=new BorderPane();

		int velicinaFonta = 14;

		hlUputstvo = new Hyperlink("Link ka uputstvu");
		hlUputstvo.setStyle("-fx-font-size: " + velicinaFonta  + ";");
		hlUputstvo.setOnAction(event -> {
			App.getInstance().getHostServices().showDocument("https://docs.google.com/document/d/1KW8VYR94WCSJW3alTcrtArk8G3CH43EQKvTyO7lC1fs/edit?usp=sharing");
			hlUputstvo.setVisited(false);
		});
		
		loginBtn = new Button("Prijavite se");
		loginBtn.setOnAction(new LoginAction(this));
		loginBtn.setStyle("-fx-font-size: " + velicinaFonta + ";");
		loginBtn.setPadding(new Insets(5, 16, 5, 16));

		signupBtn=new Button("Registrujte se");
		signupBtn.setOnAction(new SignupAction());
		signupBtn.setStyle("-fx-font-size: " + velicinaFonta + ";");
		cbAdmin=new CheckBox("Admin");
		
		cbAdmin.selectedProperty().addListener((ov, old_val, new_val) -> {
			if(new_val) {
				usernameLbl.setText("Username");
			}else {
				usernameLbl.setText("Email");
			}
		 });
		cbAdmin.setStyle("-fx-font-size: " + velicinaFonta + ";");
		
		usernameLbl=new Label("Email");
		usernameLbl.setStyle("-fx-font-size: " + velicinaFonta + ";");
		usernameTf=new TextField();
		usernameTf.setStyle("-fx-font-size: " + velicinaFonta + ";");
		passwordLbl=new Label("Password");
		passwordLbl.setStyle("-fx-font-size: " + velicinaFonta + ";");
		passwordTf=new PasswordField();
		passwordTf.setStyle("-fx-font-size: " + velicinaFonta + ";");

		VBox vboxUsername = new VBox(2, usernameLbl, usernameTf);
		VBox vBoxPassword = new VBox(2, passwordLbl, passwordTf);
		
		GridPane forma=new GridPane();
		forma.setVgap(16);
		forma.setAlignment(Pos.CENTER);
		
		forma.add(vboxUsername, 0, 0);
		forma.add(vBoxPassword, 0, 1);
		forma.add(cbAdmin, 0, 2);
		forma.add(loginBtn, 0, 3);
		forma.add(hlUputstvo, 0, 4);

		signupBtn.setPadding(new Insets(5, 30, 5, 30));
		forma.add(signupBtn, 0, 9);
		
		pozadina.setCenter(forma);
		
		this.scena=new Scene(pozadina);

	}
	
	public Boolean getCbAdmin() {
		return cbAdmin.isSelected();
	}
	
	public String getUsernameField() {
		return usernameTf.getText();
	}
	
	public String getPasswordField() {
		return passwordTf.getText();
	}
}
