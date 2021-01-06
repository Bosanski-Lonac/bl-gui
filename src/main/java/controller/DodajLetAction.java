package controller;

import java.math.BigDecimal;

import org.springframework.web.client.HttpClientErrorException;

import dto.AvionDto;
import gui.LetDialog;
import gui.komponente.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.FlightOperator;

public class DodajLetAction implements EventHandler<ActionEvent> {
	private LetDialog dialog;
	private AvionDto avionDto;
	
	public DodajLetAction(LetDialog dialog, AvionDto avionDto) {
		this.dialog = dialog;
		this.avionDto = avionDto;
	}
	
	@Override
	public void handle(ActionEvent event) {
		String pocetnaDestinacija = dialog.getPocetnaDestinacija();
		String krajnjaDestinacija = dialog.getKrajnjaDestinacija();
		Integer duzina = dialog.getDuzina();
		BigDecimal cena = dialog.getCena();
		Integer milje = dialog.getMilje();
		try {
			dialog.setResult(FlightOperator.getInstance().
					addLet(pocetnaDestinacija, krajnjaDestinacija, duzina, cena, milje, avionDto));
		} catch (HttpClientErrorException e) {
			ExceptionHandler.prikaziGresku(e);
		}
		dialog.close();
	}
}
