package gui;

import org.springframework.web.client.HttpClientErrorException;

import gui.komponente.ExceptionHandler;
import gui.komponente.IRefreshable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.UserOperator;

public class CreditCardAdditionWrapper extends SceneWrapper {
	private Label lblBrojKartice;
	private Label lblImeVlasnika;
	private Label lblPrezimeVlasnika;
	private Label lblSigurnosniBroj;
	
	private TextField tfImeVlasnika;
	private TextField tfPrezimeVlasnika;
	private TextField tfBrojKartice;
	private TextField tfSigurnosniBroj;
	
	private HBox hbBrojKartice;
	private HBox hbFullName;
	private HBox hbSigurnosniBroj;
	
	private VBox vbCardInfo;
	
	private Button btnDodaj;
	private Button btnCancel;
	
	private HBox bottom;
	
	public CreditCardAdditionWrapper(SceneWrapper scena, IRefreshable refreshable) {
		BorderPane pozadina=new BorderPane();
		
		lblBrojKartice=new Label("Broj kreditne kartice: ");
		lblImeVlasnika=new Label("Ime vlasnika: ");
		lblPrezimeVlasnika=new Label("Prezime vlasnika: ");
		lblSigurnosniBroj=new Label("Sigurnosni broj: ");
		
		tfBrojKartice=new TextField();
		tfImeVlasnika=new TextField();
		tfPrezimeVlasnika=new TextField();
		tfSigurnosniBroj=new TextField();
		
		hbBrojKartice=new HBox(10, lblBrojKartice, tfBrojKartice);
		hbBrojKartice.setAlignment(Pos.CENTER);
		hbFullName=new HBox(10, lblImeVlasnika, tfImeVlasnika, lblPrezimeVlasnika, tfPrezimeVlasnika);
		hbFullName.setAlignment(Pos.CENTER);
		hbSigurnosniBroj=new HBox(10, lblSigurnosniBroj, tfSigurnosniBroj);
		hbSigurnosniBroj.setAlignment(Pos.CENTER);
		
		vbCardInfo=new VBox(10, hbBrojKartice, hbFullName, hbSigurnosniBroj);
		vbCardInfo.setAlignment(Pos.CENTER);
		
		pozadina.setCenter(vbCardInfo);
		
		btnDodaj=new Button("Dodaj");
		btnDodaj.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					Long brojKartice=Long.parseLong(tfBrojKartice.getText());
					String imeVlasnika=tfImeVlasnika.getText();
					String prezimeVlasnika=tfPrezimeVlasnika.getText();
					Integer sigurnosniBroj=Integer.parseInt(tfSigurnosniBroj.getText());
					UserOperator.getInstance().addCC(brojKartice, imeVlasnika, prezimeVlasnika, sigurnosniBroj);
					refreshable.setPage(-1);
					MainView.getInstance().setScene(scena.getScena());
				} catch (HttpClientErrorException e) {
					ExceptionHandler.prikaziGresku(e);
				}
			}
			
		});
		
		btnCancel=new Button("Poništi");
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainView.getInstance().setScene(scena.getScena());
			}
			
		});
		
		bottom=new HBox(10, btnDodaj, btnCancel);
		bottom.setAlignment(Pos.CENTER);
		bottom.setPadding(new Insets(24, 0, 24, 0));
		
		pozadina.setBottom(bottom);
		
		this.scena=new Scene(pozadina);
	}
}
