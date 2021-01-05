package controller;

import gui.MainSceneWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.TicketOperator;
import model.UserOperator;

public class ReserveAction implements EventHandler<ActionEvent> {
	private MainSceneWrapper msw;
	
	public ReserveAction(MainSceneWrapper msw) {
		this.msw = msw;
	}
	
	@Override
	public void handle(ActionEvent event) {
		TicketOperator.getInstance().reserve(UserOperator.getInstance().getUserInfo(false).getId(),
				msw.getLetovi().getSelectionModel().getSelectedItem().getId(),
				1l);
	}
}
