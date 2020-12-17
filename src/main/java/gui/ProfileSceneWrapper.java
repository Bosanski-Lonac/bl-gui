package gui;

import org.springframework.web.client.HttpClientErrorException;

import dto.KorisnikCUDto;
import dto.KorisnikDto;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.UserOperator;

public class ProfileSceneWrapper extends SceneWrapper {
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
	
	private HBox hbEmail;
	private HBox hbPassword;
	private HBox hbBrojPasosa;
	private HBox hbIme;
	private HBox hbPrezime;
	
	private VBox vbProfil;
	
	private Button cancel;
	private Button save;
	
	private HBox bottom;
	
	public ProfileSceneWrapper(Scene glavniEkran, KorisnikDto korisnikDto) {
		BorderPane pozadina=new BorderPane();
		
		lblEmail=new Label("Unesite email adresu: ");
		lblPassword=new Label("Unesite sifru: ");
		lblBrojPasosa=new Label("Unesite broj pasosa: ");
		lblIme=new Label("Unesite ime: ");
		lblPrezime=new Label("Unesite prezime: ");
		
		tfEmail=new TextField();
		tfEmail.setText(korisnikDto.getUsername());
		pfPassword=new PasswordField();
		tfBrojPasosa=new TextField();
		tfBrojPasosa.setText(korisnikDto.getBrojPasosa());
		tfIme=new TextField();
		tfIme.setText(korisnikDto.getIme());
		tfPrezime=new TextField();
		tfPrezime.setText(korisnikDto.getPrezime());
		
		hbEmail=new HBox(lblEmail, tfEmail);
		hbEmail.setPadding(new Insets(8, 8, 8, 8));
		hbPassword=new HBox(lblPassword, pfPassword);
		hbPassword.setPadding(new Insets(8, 8, 8, 8));
		hbBrojPasosa=new HBox(lblBrojPasosa, tfBrojPasosa);
		hbBrojPasosa.setPadding(new Insets(8, 8, 8, 8));
		hbIme=new HBox(lblIme, tfIme);
		hbIme.setPadding(new Insets(8, 8, 8, 8));
		hbPrezime=new HBox(lblPrezime, tfPrezime);
		hbPrezime.setPadding(new Insets(8, 8, 8, 8));
		
		vbProfil=new VBox(hbEmail, hbPassword, hbBrojPasosa, hbIme, hbPrezime);
		vbProfil.setPadding(new Insets(8, 8, 8, 8));
		vbProfil.setAlignment(Pos.CENTER);
		
		pozadina.setCenter(vbProfil);
		
		cancel = new Button("Poništi");
		cancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainView.getInstance().setScene(glavniEkran);
			}
			
		});
		save = new Button("Sačuvaj");
		save.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Napraviti korisnicki create Dto
				try {
					String email=tfEmail.getText();
					String password=pfPassword.getText();
					String brojPasosa=tfBrojPasosa.getText();
					String ime=tfIme.getText();
					String prezime=tfIme.getText();
					KorisnikCUDto korisnikUpdateDto=new KorisnikCUDto(email, password, brojPasosa, ime, prezime);
					UserOperator.getInstance().updateUserInfo(korisnikUpdateDto);
				} catch (HttpClientErrorException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Communication Error");
					alert.setHeaderText(null);
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
				MainView.getInstance().setScene(glavniEkran);
			}
			
		});
		
		bottom = new HBox(save, cancel);
		bottom.setPadding(new Insets(15, 12, 15, 12));
		bottom.setAlignment(Pos.CENTER);
		
		pozadina.setBottom(bottom);
		this.scena = new Scene(pozadina);
	}
}
