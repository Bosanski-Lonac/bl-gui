package controller;

import gui.KorisnickiSignupDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.UserOperator;

public class DoKorisnickiSignup implements EventHandler<ActionEvent> {
	private KorisnickiSignupDialog dialog;
	
	public DoKorisnickiSignup(KorisnickiSignupDialog dialog) {
		this.dialog=dialog;
	}

	@Override
	public void handle(ActionEvent event) {
		UserOperator.getInstance().registerUser(dialog.getEmail(), dialog.getPassword(), dialog.getIme(), dialog.getPrezime(), dialog.getBrojPasosa());
	}
}
