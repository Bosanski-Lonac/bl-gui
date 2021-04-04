package gui.komponente;

import gui.komponente.exceptions.MissingInputException;
import org.springframework.web.client.HttpClientErrorException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ExceptionHandler {
	public static void prikaziGresku(HttpClientErrorException e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Communication Error");
		alert.setHeaderText(e.getStatusCode().toString());
		String message;
		int pos = e.getMessage().indexOf("\"message\":");
		if(pos == -1) {
			message = e.getMessage();
		} else {
			pos += 11;
			if(e.getMessage().charAt(pos) == '"') {
				pos = e.getMessage().indexOf("\"path\":");
				pos += 8;
				int end = e.getMessage().indexOf("\"", pos);
				if(end == -1) {
					end = e.getMessage().length() - 1;
				}
				message = e.getMessage().substring(pos, end);
			} else {
				int end = e.getMessage().indexOf("\"", pos);
				if(end == -1) {
					end = e.getMessage().length() - 1;
				}
				message = e.getMessage().substring(pos, end);
			}
		}
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void prikaziGresku(MissingInputException e){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Invalid input");
		alert.setHeaderText("Nedosaju parametri");
		alert.setContentText(e.getMessage());
		alert.showAndWait();
	}
}
