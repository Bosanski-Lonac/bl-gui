package gui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class GUIDekorater {
	private static GUIDekorater instance=null;
	
	private GUIDekorater() {};
	
	public void decorate(Object component) {
		if(component instanceof Button) {
			Button button=(Button)component;
			button.setStyle("-fx-foreground-color: #3CB371;\n"+"-fx-background-color: #FFFFFF;\n"+"-fx-border-color: #3CB371;\n"+"-fx-border-width: 2;");
			button.addEventHandler(MouseEvent.MOUSE_ENTERED,
			        new EventHandler<MouseEvent>() {
			          @Override
			          public void handle(MouseEvent e) {
			            button.setStyle("-fx-foreground-color: #000000;\n"+"-fx-background-color: #3CB371;\n"+"-fx-border-color: #FFFFFF;\n"+"-fx-border-width: 2;");
			          }
			        });
			button.addEventHandler(MouseEvent.MOUSE_EXITED,
			        new EventHandler<MouseEvent>() {
			          @Override
			          public void handle(MouseEvent e) {
			            button.setStyle("-fx-foreground-color: #3CB371;\n"+"-fx-background-color: #FFFFFF;\n"+"-fx-border-color: #3CB371;\n"+"-fx-border-width: 2;");
			          }
			        });
		}
	}
	
	public static GUIDekorater getInstance() {
		if(instance==null) {
			instance=new GUIDekorater();
		}
		return instance;
	}
}
