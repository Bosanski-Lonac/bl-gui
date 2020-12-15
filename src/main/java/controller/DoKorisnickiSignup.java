package controller;

import org.springframework.web.client.HttpClientErrorException;

import dto.KorisnikDto;
import gui.KorisnickiSignupDialog;
import gui.MainSceneWrapper;
import gui.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
			KorisnikDto korisnikDto = UserOperator.getInstance().registerUser(email,
					password, ime, prezime, brojPasosa);
			MainView.getInstance().setScene(new MainSceneWrapper(korisnikDto).getScena());
		} catch (HttpClientErrorException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Communication Error");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		dialog.close();
	}
}
