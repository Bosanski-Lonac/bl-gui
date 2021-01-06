package controller;

import java.util.Optional;

import dto.AvionDto;
import dto.LetDto;
import gui.LetDialog;
import gui.PlaneSceneWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ShowFlightFormAction implements EventHandler<ActionEvent> {
	private PlaneSceneWrapper psw;
	
	public ShowFlightFormAction(PlaneSceneWrapper psw) {
		this.psw=psw;
	}

	@Override
	public void handle(ActionEvent event) {
		AvionDto odabran=psw.getAvioni().getSelectionModel().getSelectedItem();
		if(odabran == null) {
			Alert alert = new Alert(AlertType.INFORMATION, "Niste izabrali avion.");
			alert.showAndWait();
			return;
		}
		LetDialog dialog=new LetDialog(odabran);
		Optional<LetDto> result = dialog.showAndWait();
		if(result.isPresent()) {
			psw.setPage(-1);
		}
	}

}
