package gui.komponente;

import org.springframework.web.client.HttpClientErrorException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ExceptionHandler {
	public static void prikaziGresku(HttpClientErrorException e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Communication Error");
		alert.setHeaderText(e.getStatusCode().toString());
		System.out.println(e.getMessage());
		String message;
		int pos = e.getMessage().indexOf("\"message\":");
		if(pos == -1) {
			message = e.getMessage();
		} else {
			pos += 11;
			if(e.getMessage().charAt(pos) == '"') {
				pos = e.getMessage().indexOf("\"path\":");
				pos += 8;
				message = e.getMessage().substring(pos, e.getMessage().indexOf("\"", pos));
			} else {
				message = e.getMessage().substring(pos, e.getMessage().indexOf("\"", pos));
			}
		}
		alert.setContentText(message);
		alert.showAndWait();
	}
}
