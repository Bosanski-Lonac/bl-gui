package gui;

import controller.LoginAction;
import controller.SignupAction;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginSceneWrapper extends SceneWrapper {
	private Button signupBtn;
	private Button loginBtn;
	private CheckBox cbAdmin;
	
	private Label usernameLbl;
	private TextField usernameTf;
	private Label passwordLbl;
	private PasswordField passwordTf;
	
	private VBox login;
	private HBox register;
	
	public LoginSceneWrapper() {
		BorderPane pozadina=new BorderPane();
		
		loginBtn = new Button("Prijavite se");
		loginBtn.setOnAction(new LoginAction(this));
		
		signupBtn=new Button("Registrujte se");
		signupBtn.setOnAction(new SignupAction());
		cbAdmin=new CheckBox("Admin");
		
		register = new HBox(signupBtn);
		register.setAlignment(Pos.CENTER);
		register.setPadding(new Insets(15, 0, 15, 0));
		pozadina.setTop(register);
		
		cbAdmin.selectedProperty().addListener(new ChangeListener<Boolean>() {
	           public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
	               if(new_val) {
	            	   usernameLbl.setText("Username");
	               }else {
	            	   usernameLbl.setText("Email");
	               }
	            }
	        });
		
		usernameLbl=new Label("Email");
		usernameTf=new TextField();
		passwordLbl=new Label("Password");
		passwordTf=new PasswordField();
		
		GridPane forma=new GridPane();
		forma.setVgap(8);
		forma.setAlignment(Pos.CENTER);
		
		forma.add(usernameLbl, 0, 1);
		forma.add(usernameTf, 0, 2);
		forma.add(passwordLbl, 0, 3);
		forma.add(passwordTf, 0, 4);
		
		pozadina.setCenter(forma);
		
		login = new VBox(cbAdmin, loginBtn);
		login.setAlignment(Pos.CENTER);
		login.setPadding(new Insets(15, 0, 15, 0));
		login.setSpacing(10);
		
		pozadina.setBottom(login);
		
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
