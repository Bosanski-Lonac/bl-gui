package gui;

import java.math.BigDecimal;

import controller.DodajLetAction;
import dto.AvionDto;
import dto.LetDto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LetDialog extends Dialog<LetDto> {
	private Label lblPocetnaDestinacija;
	private Label lblKrajnjaDestinacija;
	private Label lblDuzina;
	private Label lblCena;
	private Label lblMilje;
	
	private TextField tfPocetnaDestinacija;
	private TextField tfKrajnjaDestinacija;
	private UnsignedIntegerField tfDuzina;
	private TextField tfCena;
	private UnsignedIntegerField tfMilje;
	
	private HBox hbPocetnaDestinacija;
	private HBox hbKrajnjaDestinacija;
	private HBox hbDuzina;
	private HBox hbCena;
	private HBox hbMilje;
	
	private HBox hbDestinacije;
	private HBox hbMetrike;
	
	private VBox vbLet;
	
	public LetDialog(AvionDto avionDto) {
		super();
		
		BorderPane pozadina=new BorderPane();
		
		lblPocetnaDestinacija=new Label("Početna destinacija: ");
		lblKrajnjaDestinacija=new Label("Krajnja destinacija: ");
		lblDuzina=new Label("Dužina leta: ");
		lblCena=new Label("Cena leta: ");
		lblMilje=new Label("Broj milja: ");
		
		tfPocetnaDestinacija=new TextField();
		tfKrajnjaDestinacija=new TextField();
		tfDuzina=new UnsignedIntegerField();
		tfCena=new UnsignedIntegerField();
		tfMilje=new UnsignedIntegerField();
		
		hbPocetnaDestinacija=new HBox(10, lblPocetnaDestinacija, tfPocetnaDestinacija);
		hbPocetnaDestinacija.setAlignment(Pos.CENTER);
		hbKrajnjaDestinacija=new HBox(10, lblKrajnjaDestinacija, tfKrajnjaDestinacija);
		hbKrajnjaDestinacija.setAlignment(Pos.CENTER);
		hbDuzina=new HBox(10, lblDuzina, tfDuzina);
		hbDuzina.setAlignment(Pos.CENTER);
		hbCena=new HBox(10, lblCena, tfCena);
		hbCena.setAlignment(Pos.CENTER);
		hbMilje=new HBox(10, lblMilje, tfMilje);
		hbMilje.setAlignment(Pos.CENTER);
		
		hbDestinacije=new HBox(10, hbPocetnaDestinacija, hbKrajnjaDestinacija);
		hbDestinacije.setAlignment(Pos.CENTER);
		hbMetrike=new HBox(10, hbDuzina, hbMilje);
		hbMetrike.setAlignment(Pos.CENTER);
		
		vbLet=new VBox(10, hbDestinacije, hbMetrike, hbCena);
		vbLet.setAlignment(Pos.CENTER);
		
		FlowPane center=new FlowPane();
		center.getChildren().addAll(vbLet);
		center.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		center.setPadding(new Insets(9, 9, 9, 9));
		center.setAlignment(Pos.CENTER);
		
		pozadina.setCenter(center);
		
		this.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
		((Button)this.getDialogPane().lookupButton(ButtonType.OK)).setOnAction(new DodajLetAction(this, avionDto));
		
		getDialogPane().setContent(pozadina);
		getDialogPane().setPrefWidth(400);
		getDialogPane().setPrefHeight(600);
		setTitle("Dodaj Let");
	}
	
	public String getPocetnaDestinacija() {
		return tfPocetnaDestinacija.getText();
	}
	
	public String getKrajnjaDestinacija() {
		return tfKrajnjaDestinacija.getText();
	}
	
	public Integer getDuzina() {
		return Integer.parseUnsignedInt(tfDuzina.getText());
	}
	
	public BigDecimal getCena() {
		return new BigDecimal(tfCena.getText());
	}
	
	public Integer getMilje() {
		return Integer.parseUnsignedInt(tfMilje.getText());
	}
}
