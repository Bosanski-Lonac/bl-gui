package gui;

import org.springframework.web.client.HttpClientErrorException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ExceptionHandler {
	public static void prikaziGresku(HttpClientErrorException e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Communication Error");
		alert.setHeaderText(null);
		alert.setContentText(e.getMessage());
		alert.showAndWait();
	}
}
