package controller;

import gui.LoginSceneWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.UserOperator;

public class LoginAction implements EventHandler<ActionEvent> {
	
	private LoginSceneWrapper scena;
	
	public LoginAction(LoginSceneWrapper scena) {
		this.scena = scena;
	}
	
	@Override
	public void handle(ActionEvent event) {
		boolean admin = scena.getCbAdmin();
		String username = scena.getUsernameField();
		String password = scena.getPasswordField();
		if(admin) {
			UserOperator.getInstance().signInAdmin(username, password);
		} else {
			UserOperator.getInstance().signInUser(username, password);
		}
	}
}
