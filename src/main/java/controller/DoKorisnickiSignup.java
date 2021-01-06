package controller;

import org.springframework.web.client.HttpClientErrorException;

import gui.KorisnickiSignupDialog;
import gui.MainSceneWrapper;
import gui.MainView;
import gui.komponente.ExceptionHandler;
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
		String email = dialog.getEmail();
		String password = dialog.getPassword();
		String ime = dialog.getIme();
		String prezime = dialog.getPrezime();
		String brojPasosa = dialog.getBrojPasosa();
		try {
			UserOperator.getInstance().registerUser(email,
					password, ime, prezime, brojPasosa);
			MainView.getInstance().setScene(new MainSceneWrapper().getScena());
		} catch (HttpClientErrorException e) {
			ExceptionHandler.prikaziGresku(e);
		}
		dialog.close();
	}
}
