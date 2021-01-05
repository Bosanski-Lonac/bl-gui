package controller;

import gui.AvionDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ShowPlaneFormAction implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		AvionDialog dialog = new AvionDialog();
		dialog.showAndWait();
	}

}
