package controller;

import java.util.Optional;

import dto.AvionDto;
import gui.AvionDialog;
import gui.PlaneSceneWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ShowPlaneFormAction implements EventHandler<ActionEvent> {
	
private PlaneSceneWrapper psw;
	
	public ShowPlaneFormAction(PlaneSceneWrapper psw) {
		this.psw=psw;
	}
	
	@Override
	public void handle(ActionEvent event) {
		AvionDialog dialog = new AvionDialog();
		Optional<AvionDto> result = dialog.showAndWait();
		if(result.isPresent()) {
			psw.setTableAvioni(-1);
		}
	}

}
