package controller;

import gui.FlightSceneWrapper;
import gui.MainSceneWrapper;
import gui.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ShowFlightFormAction implements EventHandler<ActionEvent> {
	private MainSceneWrapper msw;
	
	public ShowFlightFormAction(MainSceneWrapper msw) {
		this.msw=msw;
	}

	@Override
	public void handle(ActionEvent event) {
		FlightSceneWrapper fsw=new FlightSceneWrapper(msw);
		MainView.getInstance().setScene(fsw.getScena());
	}

}
