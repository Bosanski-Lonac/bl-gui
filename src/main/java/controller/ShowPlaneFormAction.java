package controller;

import java.util.Optional;

import dto.AvionDto;
import gui.AvionDialog;
import gui.komponente.IRefreshable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ShowPlaneFormAction implements EventHandler<ActionEvent> {
	private IRefreshable refreshable;
	
	public ShowPlaneFormAction(IRefreshable refreshable) {
		this.refreshable=refreshable;
	}
	
	@Override
	public void handle(ActionEvent event) {
		AvionDialog dialog = new AvionDialog();
		Optional<AvionDto> result = dialog.showAndWait();
		if(result.isPresent()) {
			refreshable.setPage(-1);
		}
	}

}
