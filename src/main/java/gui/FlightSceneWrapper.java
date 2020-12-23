package gui;

import java.math.BigDecimal;

import org.springframework.web.client.HttpClientErrorException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import model.FlightOperator;

public class FlightSceneWrapper extends SceneWrapper {
	private Label lblPocetnaDestinacija;
	private Label lblKrajnjaDestinacija;
	private Label lblDuzina;
	private Label lblCena;
	private Label lblMilje;
	
	private TextField tfPocetnaDestinacija;
	private TextField tfKrajnjaDestinacija;
	private TextField tfDuzina;
	private TextField tfCena;
	private TextField tfMilje;
	
	private HBox hbPocetnaDestinacija;
	private HBox hbKrajnjaDestinacija;
	private HBox hbDuzina;
	private HBox hbCena;
	private HBox hbMilje;
	
	private HBox hbDestinacije;
	private HBox hbMetrike;
	
	private VBox vbLet;
	
	private Button btnDodaj;
	private Button btnCancel;
	
	private HBox hbBottom;
	
	public FlightSceneWrapper(MainSceneWrapper glavniEkran) {
		BorderPane pozadina=new BorderPane();
		
		lblPocetnaDestinacija=new Label("Početna destinacija: ");
		lblKrajnjaDestinacija=new Label("Krajnja destinacija: ");
		lblDuzina=new Label("Dužina leta: ");
		lblCena=new Label("Cena leta: ");
		lblMilje=new Label("Broj milja: ");
		
		tfPocetnaDestinacija=new TextField();
		tfKrajnjaDestinacija=new TextField();
		tfDuzina=new TextField();
		tfCena=new TextField();
		tfMilje=new TextField();
		
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
		
		btnDodaj=new Button("Dodaj");
		btnDodaj.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String pocetnaDestinacija=tfPocetnaDestinacija.getText();
				String krajnjaDestinacija=tfKrajnjaDestinacija.getText();
				Integer duzina=Integer.parseInt(tfDuzina.getText());
				BigDecimal cena=new BigDecimal(tfCena.getText());
				Integer milje=Integer.parseInt(tfMilje.getText());
				FlightOperator.getInstance().addFlight(pocetnaDestinacija, krajnjaDestinacija, duzina, cena, milje);
				try {
					glavniEkran.setLetPageWrapper(FlightOperator.getInstance().getFlights(glavniEkran.getLetCriteriaDto()));
					glavniEkran.setTableLetovi();
				} catch (HttpClientErrorException e) {
					ExceptionHandler.prikaziGresku(e);
				}
				MainView.getInstance().setScene(glavniEkran.getScena());
			}
			
		});
		btnCancel=new Button("Poništi");
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainView.getInstance().setScene(glavniEkran.getScena());
			}
			
		});
		
		hbBottom=new HBox(10, btnDodaj, btnCancel);
		hbBottom.setAlignment(Pos.CENTER);
		
		pozadina.setBottom(hbBottom);
		
		this.scena=new Scene(pozadina);
	}
}
