package controller;

import org.springframework.web.client.HttpClientErrorException;

import dto.LetDto;
import gui.MainSceneWrapper;
import gui.komponente.ExceptionHandler;
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
		LetDto odabran=letovi.getSelectionModel().getSelectedItem();
		try {
			FlightOperator.getInstance().deleteFlight(odabran.getId());
			msw.setPage(-2);
		} catch (HttpClientErrorException e) {
			ExceptionHandler.prikaziGresku(e);
		}
	}
}
