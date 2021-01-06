package controller;

import gui.CreditCardAdditionWrapper;
import gui.MainView;
import gui.SceneWrapper;
import gui.komponente.IRefreshable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ShowCCFormAction implements EventHandler<ActionEvent> {
	private SceneWrapper scena;
	private IRefreshable refreshable;

	public ShowCCFormAction(SceneWrapper scena, IRefreshable refreshable) {
		this.scena=scena;
		this.refreshable=refreshable;
	}
	
	@Override
	public void handle(ActionEvent event) {
		CreditCardAdditionWrapper ccaw=new CreditCardAdditionWrapper(scena, refreshable);
		MainView.getInstance().setScene(ccaw.getScena());
	}

}
