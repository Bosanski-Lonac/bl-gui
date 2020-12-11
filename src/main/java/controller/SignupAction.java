package controller;

import gui.AdminSignupDialog;
import gui.KorisnickiSignupDialog;
import gui.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SignupAction implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		if(MainView.getInstance().getCbAdmin().isSelected()) {
			AdminSignupDialog dialog=new AdminSignupDialog();
			dialog.showAndWait();
		}else {
			KorisnickiSignupDialog dialog=new KorisnickiSignupDialog();
			dialog.showAndWait();
		}
	}

}
