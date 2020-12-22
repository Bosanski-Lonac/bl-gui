package controller;

import gui.MainSceneWrapper;
import gui.MainView;
import gui.PlaneSceneWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ShowPlaneFormAction implements EventHandler<ActionEvent> {
	private MainSceneWrapper msw;
	
	public ShowPlaneFormAction(MainSceneWrapper msw){
		this.msw=msw;
	}

	@Override
	public void handle(ActionEvent event) {
		PlaneSceneWrapper psw=new PlaneSceneWrapper(msw);
		MainView.getInstance().setScene(psw.getScena());
	}

}
