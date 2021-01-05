package controller;

import dto.AvionDto;
import gui.LetDialog;
import gui.PlaneSceneWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ShowFlightFormAction implements EventHandler<ActionEvent> {
	private PlaneSceneWrapper psw;
	
	public ShowFlightFormAction(PlaneSceneWrapper psw) {
		this.psw=psw;
	}

	@Override
	public void handle(ActionEvent event) {
		AvionDto odabran=psw.getAvioni().getSelectionModel().getSelectedItem();
		LetDialog dialog=new LetDialog(odabran);
		dialog.showAndWait();
	}

}
