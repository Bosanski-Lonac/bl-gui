package controller;

import java.util.List;

import dto.AvionDto;
import gui.MainSceneWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import model.FlightOperator;

public class DeletePlaneAction implements EventHandler<ActionEvent> {
	private MainSceneWrapper msw;
	
	public DeletePlaneAction(MainSceneWrapper msw) {
		this.msw=msw;
	}

	@Override
	public void handle(ActionEvent event) {
		TableView<AvionDto> avioni=msw.getAvioni();
		List<AvionDto> odabrani=avioni.getSelectionModel().getSelectedItems();
		for (AvionDto avionDto : odabrani) {
			FlightOperator.getInstance().deletePlane(avionDto.getId());
		}
	}
}
