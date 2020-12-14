package controller;

import gui.KorisnickiSignupDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SignupAction implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		KorisnickiSignupDialog dialog=new KorisnickiSignupDialog();
		dialog.showAndWait();
	}

}
