package gui;

import org.springframework.web.client.HttpClientErrorException;

import dto.KorisnikDto;
import dto.KorisnikUpdateDto;
import enums.Rank;
import gui.komponente.CreditCardList;
import gui.komponente.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.UserOperator;

public class ProfileSceneWrapper extends SceneWrapper {
	private KorisnikDto korisnikDto;
	
	private Label lblEmail;
	private TextField tfEmail;
	private Label lblPassword;
	private PasswordField pfPassword;
	private Label lblBrojPasosa;
	private TextField tfBrojPasosa;
	private Label lblIme;
	private TextField tfIme;
	private Label lblPrezime;
	private TextField tfPrezime;
	private Label lblRank;
	
	private HBox hbUserPass;
	private HBox hbFullName;
	private HBox hbPasos;
	private VBox user;
	
	private CreditCardList lista;
	
	private Accordion accordion;
		
	private Button btnCancel;
	private Button btnDodaj;
	
	private HBox bottom;
	
	public ProfileSceneWrapper(Scene glavniEkran) {
		BorderPane pozadina=new BorderPane();
		
		korisnikDto = UserOperator.getInstance().getUserInfo(false);
		
		Image image;
		
		lblRank=new Label(korisnikDto.getRank().toString());
		if(korisnikDto.getRank()==Rank.BRONZA) {
			image = new Image("ikonice/bronze.png");
			lblRank.setTextFill(Color.web("#cd7f32"));
		}else if(korisnikDto.getRank()==Rank.SREBRO) {
			image = new Image("ikonice/silver.png");
			lblRank.setTextFill(Color.web("#c0c0c0"));
		}else {
			image = new Image("ikonice/gold.png");
			lblRank.setTextFill(Color.web("#ffd700"));
		}
		
		ImageView imageView = new ImageView(image);
		
		imageView.setFitHeight(384);
		imageView.setFitWidth(384);
		
		lblEmail=new Label("Email: ");
		lblPassword=new Label("Sifra: ");
		tfEmail=new TextField();
		tfEmail.setText(korisnikDto.getUsername());
		pfPassword=new PasswordField();
		tfBrojPasosa=new TextField();
		lblBrojPasosa=new Label("Broj pasosa: ");
		tfBrojPasosa.setText(korisnikDto.getBrojPasosa());
		lblIme=new Label("Ime: ");
		lblPrezime=new Label("Prezime: ");
		tfIme=new TextField();
		tfIme.setText(korisnikDto.getIme());
		tfPrezime=new TextField();
		tfPrezime.setText(korisnikDto.getPrezime());
		
		hbFullName=new HBox(10, lblIme, tfIme, lblPrezime, tfPrezime);
		hbFullName.setAlignment(Pos.CENTER);
		hbUserPass=new HBox(10, lblEmail, tfEmail, lblPassword, pfPassword);
		hbUserPass.setAlignment(Pos.CENTER);
		hbPasos=new HBox(10, lblBrojPasosa, tfBrojPasosa);
		hbPasos.setAlignment(Pos.CENTER);
		
		user=new VBox(10, hbFullName, imageView, lblRank, hbUserPass, hbPasos);
		user.setAlignment(Pos.BASELINE_CENTER);
		user.setPadding(new Insets(32, 32, 32, 32));
		
		pozadina.setLeft(user);
		
		lista = new CreditCardList(this);
		
		TitledPane tpKreditne=new TitledPane("Kreditne kartice", lista);
		//TitledPane tpAvionske=new TitledPane("Avionske karte", new Button("WOOO"));
		accordion=new Accordion(tpKreditne);

		pozadina.setCenter(accordion);
		
		btnCancel = new Button("Poništi");
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainView.getInstance().setScene(glavniEkran);
			}
			
		});
		btnDodaj = new Button("Sačuvaj");
		btnDodaj.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String email=tfEmail.getText();
				String password=pfPassword.getText();
				if(password.isBlank()) {
					Alert alert = new Alert(AlertType.ERROR, "Niste ukucali šifru.");
					alert.showAndWait();
					return;
				}
				String brojPasosa=tfBrojPasosa.getText();
				String ime=tfIme.getText();
				String prezime=tfPrezime.getText();
				KorisnikUpdateDto korisnikUpdateDto=new KorisnikUpdateDto(email, password, "", ime, prezime, brojPasosa);
				try {
					UserOperator.getInstance().updateUserInfo(korisnikUpdateDto);
				} catch (HttpClientErrorException e) {
					ExceptionHandler.prikaziGresku(e);
				}
				MainView.getInstance().setScene(glavniEkran);
			}
			
		});
		
		bottom = new HBox(btnDodaj, btnCancel);
		bottom.setPadding(new Insets(24, 0, 24, 0));
		bottom.setAlignment(Pos.CENTER);
		
		pozadina.setBottom(bottom);
		this.scena = new Scene(pozadina);
	}
}
