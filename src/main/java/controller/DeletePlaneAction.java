package controller;

import org.springframework.web.client.HttpClientErrorException;

import dto.AvionDto;
import gui.PlaneSceneWrapper;
import gui.komponente.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import model.FlightOperator;

public class DeletePlaneAction implements EventHandler<ActionEvent> {
	private PlaneSceneWrapper psw;
	
	public DeletePlaneAction(PlaneSceneWrapper psw) {
		this.psw=psw;
	}

	@Override
	public void handle(ActionEvent event) {
		TableView<AvionDto> avioni = psw.getAvioni();
		AvionDto odabran=avioni.getSelectionModel().getSelectedItem();
		if(odabran == null) {
			return;
		}
		try {
			FlightOperator.getInstance().deletePlane(odabran.getId());
			psw.setPage(-2);
		} catch (HttpClientErrorException e) {
			ExceptionHandler.prikaziGresku(e);
		}
	}
}
