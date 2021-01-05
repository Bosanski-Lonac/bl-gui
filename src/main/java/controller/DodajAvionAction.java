package controller;

import org.springframework.web.client.HttpClientErrorException;

import gui.AvionDialog;
import gui.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.FlightOperator;

public class DodajAvionAction implements EventHandler<ActionEvent> {
	private AvionDialog dialog;
	
	public DodajAvionAction(AvionDialog dialog) {
		this.dialog = dialog;
	}
	
	@Override
	public void handle(ActionEvent event) {
		String naziv = dialog.getNaziv();
		Integer kapacitet = dialog.getKapacitet();
		try {
			dialog.setResult(FlightOperator.getInstance().addPlane(naziv, kapacitet));
		} catch (HttpClientErrorException e) {
			ExceptionHandler.prikaziGresku(e);
		}
		dialog.close();
	}

}
