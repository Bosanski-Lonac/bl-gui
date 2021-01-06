package gui;

import controller.DodajAvionAction;
import dto.AvionDto;
import gui.komponente.UnsignedIntegerField;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class AvionDialog extends Dialog<AvionDto> {
	private Label lblNaziv;
	private Label lblKapacitet;
	
	private TextField tfNaziv;
	private UnsignedIntegerField tfKapacitet;
	
	private HBox hbNaziv;
	private HBox hbKapacitet;
	
	private HBox hbAvion;
	
	public AvionDialog() {
		super();
		
		BorderPane pozadina=new BorderPane();
		
		lblNaziv=new Label("Naziv aviona: ");
		lblKapacitet=new Label("Kapacitet aviona: ");
		
		tfNaziv=new TextField();
		tfKapacitet=new UnsignedIntegerField();
		
		hbNaziv=new HBox(10, lblNaziv, tfNaziv);
		hbNaziv.setAlignment(Pos.CENTER);
		hbKapacitet=new HBox(10, lblKapacitet, tfKapacitet);
		hbKapacitet.setAlignment(Pos.CENTER);
		
		hbAvion=new HBox(10, hbNaziv, hbKapacitet);
		hbAvion.setAlignment(Pos.CENTER);
		
		pozadina.setCenter(hbAvion);
		
		this.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
		((Button)this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(new DodajAvionAction(this));
		
		getDialogPane().setContent(pozadina);
		getDialogPane().setPrefWidth(400);
		getDialogPane().setPrefHeight(600);
		setTitle("Dodaj Avion");
	}
	
	public String getNaziv() {
		return tfNaziv.getText();
	}
	
	public Integer getKapacitet() {
		return Integer.parseUnsignedInt(tfKapacitet.getText());
	}
}
