package controller;

import java.util.List;

import dto.LetDto;
import gui.MainSceneWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import model.FlightOperator;

public class DeleteFlightAction implements EventHandler<ActionEvent> {
	private MainSceneWrapper msw;
	
	public DeleteFlightAction(MainSceneWrapper msw) {
		this.msw=msw; 
	}

	@Override
	public void handle(ActionEvent event) {
		TableView<LetDto> letovi=msw.getLetovi();
		List<LetDto> odabrani=letovi.getSelectionModel().getSelectedItems();
		for (LetDto letDto : odabrani) {
			FlightOperator.getInstance().deleteFlight(letDto.getId());
		}
	}
}
