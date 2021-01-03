package gui;

import org.springframework.web.client.HttpClientErrorException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.FlightOperator;

public class PlaneSceneWrapper extends SceneWrapper {
	private Label lblNaziv;
	private Label lblKapacitet;
	
	private TextField tfNaziv;
	private TextField tfKapacitet;
	
	private HBox hbNaziv;
	private HBox hbKapacitet;
	
	private HBox hbAvion;
	
	private Button btnDodaj;
	private Button btnCancel;
	
	private HBox hbBottom;
	
	public PlaneSceneWrapper(MainSceneWrapper glavniEkran) {
		BorderPane pozadina=new BorderPane();
		
		lblNaziv=new Label("Naziv aviona: ");
		lblKapacitet=new Label("Kapacitet aviona: ");
		
		tfNaziv=new TextField();
		tfKapacitet=new TextField();
		
		hbNaziv=new HBox(10, lblNaziv, tfNaziv);
		hbNaziv.setAlignment(Pos.CENTER);
		hbKapacitet=new HBox(10, lblKapacitet, tfKapacitet);
		hbKapacitet.setAlignment(Pos.CENTER);
		
		hbAvion=new HBox(10, hbNaziv, hbKapacitet);
		hbAvion.setAlignment(Pos.CENTER);
		
		pozadina.setCenter(hbAvion);
		
		btnDodaj=new Button("Dodaj");
		btnDodaj.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String naziv=tfNaziv.getText();
				Integer kapacitet=Integer.parseInt(tfKapacitet.getText());
				FlightOperator.getInstance().addPlane(naziv, kapacitet);
				try {
					//glavniEkran.setAvionPageWrapper(FlightOperator.getInstance().getPlanes(glavniEkran.getAvionCriteriaDto()));
					glavniEkran.setTableAvioni();
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
