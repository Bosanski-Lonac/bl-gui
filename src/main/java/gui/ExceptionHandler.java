package gui;

import org.springframework.web.client.HttpClientErrorException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ExceptionHandler {
	public static void prikaziGresku(HttpClientErrorException e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Communication Error");
		alert.setHeaderText(e.getStatusCode().toString());
		int pos = e.getMessage().indexOf("\"message\":");
		pos += 11;
		String message;
		if(e.getMessage().charAt(pos) == '"') {
			pos = e.getMessage().indexOf("\"path\":");
			pos += 8;
			message = e.getMessage().substring(pos, e.getMessage().indexOf("\"", pos));
		} else {
			message = e.getMessage().substring(pos, e.getMessage().indexOf("\"", pos) - 1);
		}
		alert.setContentText(message);
		alert.showAndWait();
	}
}
