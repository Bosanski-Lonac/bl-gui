package gui.komponente;

import dto.LetDto;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LetDisplay extends VBox {
	private Label lblPut;
	private Label lblTrajanje;
	private Label lblMilje;
	
	public LetDisplay(LetDto letDto) {
		lblPut = new Label(letDto.getPocetnaDestinacija() + " - " + letDto.getKrajnjaDestinacija());
		lblTrajanje = new Label(letDto.getDuzina() + " minuta");
		lblMilje = new Label(letDto.getMilje() + " kilometara");
		
		this.getChildren().add(lblPut);
		this.getChildren().add(lblTrajanje);
		this.getChildren().add(lblMilje);
		
		this.setAlignment(Pos.CENTER);
	}
}
