package gui;

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
	private VBox vbCenter;
	
	private Button btnDodaj;
	private Button btnCancel;
	
	private HBox bottom;
	
	public CreditCardAdditionWrapper(ProfileSceneWrapper userProfile) {
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
		
		FlowPane center=new FlowPane();
		center.getChildren().addAll(vbCardInfo);
		center.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		center.setPadding(new Insets(9, 9, 9, 9));
		center.setAlignment(Pos.CENTER);
		
		vbCenter=new VBox(10, center);
		vbCenter.setAlignment(Pos.CENTER);
		
		pozadina.setCenter(vbCenter);
		
		btnDodaj=new Button("Dodaj");
		btnDodaj.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Long brojKartice=Long.parseLong(tfBrojKartice.getText());
				String imeVlasnika=tfImeVlasnika.getText();
				String prezimeVlasnika=tfPrezimeVlasnika.getText();
				Integer sigurnosniBroj=Integer.parseInt(tfSigurnosniBroj.getText());
				UserOperator.getInstance().addCC(brojKartice, imeVlasnika, prezimeVlasnika, sigurnosniBroj);
				userProfile.CCRefresh();
				MainView.getInstance().setScene(userProfile.getScena());
			}
			
		});
		
		btnCancel=new Button("Poništi");
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainView.getInstance().setScene(userProfile.getScena());
			}
			
		});
		
		bottom=new HBox(10, btnDodaj, btnCancel);
		bottom.setAlignment(Pos.CENTER);
		bottom.setPadding(new Insets(24, 24, 24, 24));
		
		pozadina.setBottom(bottom);
		
		this.scena=new Scene(pozadina);
	}
}
