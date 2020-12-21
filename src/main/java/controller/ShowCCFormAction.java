package controller;

import gui.CreditCardAdditionWrapper;
import gui.MainView;
import gui.ProfileSceneWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ShowCCFormAction implements EventHandler<ActionEvent> {
	private ProfileSceneWrapper psw;

	public ShowCCFormAction(ProfileSceneWrapper psw) {
		this.psw=psw;
	}
	
	@Override
	public void handle(ActionEvent event) {
		CreditCardAdditionWrapper ccaw=new CreditCardAdditionWrapper(psw);
		MainView.getInstance().setScene(ccaw.getScena());
	}

}
