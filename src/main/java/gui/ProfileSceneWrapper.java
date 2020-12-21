package gui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.HttpClientErrorException;

import dto.KorisnikCUDto;
import dto.KorisnikDto;
import dto.KreditnaKarticaDto;
import enums.Rank;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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
import security.EmailSender;
import wrapper.KreditnaKarticaPageWrapper;

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
	private HBox hbPasos;
	private VBox user;
	private VBox left;
	
	private HBox hbFullName;
	
	private VBox right;
	
	private Label lblBrojKartice;
	private Label lblImeVlasnika;
	private Label lblPrezimeVlasnika;
	private Label lblSigurnosniBroj;
	
	private TextField tfImeVlasnika;
	private TextField tfPrezimeVlasnika;
	private TextField tfBrojKartice;
	private TextField tfSigurnosniBroj;
	
	private HBox hbBrojKartice;
	private HBox hbVlasnik;
	private HBox hbSigurnosniBroj;
	private VBox vbCardInfo;
	private VBox vbCardExample;
	
	private Button btnCreditCard;
		
	private Button cancel;
	private Button save;
	
	private HBox bottom;
	
	public ProfileSceneWrapper(Scene glavniEkran) {
		BorderPane pozadina=new BorderPane();
		
		korisnikDto = UserOperator.getInstance().getUserInfo(false);
		
		Image image = new Image("ikonice/profile.png");
		ImageView imageView = new ImageView(image);
		
		imageView.setFitHeight(72);
		imageView.setFitWidth(72);
		
		lblRank=new Label(korisnikDto.getRank().toString());
		if(korisnikDto.getRank()==Rank.BRONZA) {
			lblRank.setTextFill(Color.web("#cd7f32"));
		}else if(korisnikDto.getRank()==Rank.SREBRO) {
			lblRank.setTextFill(Color.web("#c0c0c0"));
		}else {
			lblRank.setTextFill(Color.web("#ffd700"));
		}
		
		lblEmail=new Label("Email: ");
		lblPassword=new Label("Sifra: ");
		tfEmail=new TextField();
		tfEmail.setText(korisnikDto.getUsername());
		pfPassword=new PasswordField();
		tfBrojPasosa=new TextField();
		lblBrojPasosa=new Label("Broj pasosa: ");
		tfBrojPasosa.setText(korisnikDto.getBrojPasosa());
		
		hbUserPass=new HBox(10, lblEmail, tfEmail, lblPassword, pfPassword);
		hbPasos=new HBox(10, lblBrojPasosa, tfBrojPasosa);
		hbPasos.setAlignment(Pos.CENTER);
		
		user=new VBox(10, imageView, lblRank, hbUserPass, hbPasos);
		user.setAlignment(Pos.BASELINE_CENTER);
		user.setPadding(new Insets(32, 32, 32, 32));
		
		FlowPane fpLeft=new FlowPane(user);
		fpLeft.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		fpLeft.setPadding(new Insets(9, 9, 9, 9));
		
		left=new VBox(10, fpLeft);
		left.setPadding(new Insets(32, 32, 32, 32));
		
		pozadina.setLeft(left);
		
		lblIme=new Label("Ime: ");
		lblPrezime=new Label("Prezime: ");
		tfIme=new TextField();
		tfIme.setText(korisnikDto.getIme());
		tfPrezime=new TextField();
		tfPrezime.setText(korisnikDto.getPrezime());
		
		hbFullName=new HBox(10, lblIme, tfIme, lblPrezime, tfPrezime);
		
		lblBrojKartice=new Label("Broj: ");
		lblImeVlasnika=new Label("Ime: ");
		lblPrezimeVlasnika=new Label("Prezime: ");
		lblSigurnosniBroj=new Label("Sigurnosni broj: ");
		
		tfImeVlasnika=new TextField();
		tfImeVlasnika.setText("Marko");
		tfImeVlasnika.setEditable(false);
		tfPrezimeVlasnika=new TextField();
		tfPrezimeVlasnika.setText("Milosevic");
		tfPrezimeVlasnika.setEditable(false);
		tfBrojKartice=new TextField();
		tfBrojKartice.setText("**** **** **** 3946");
		tfBrojKartice.setEditable(false);
		tfSigurnosniBroj=new TextField();
		tfSigurnosniBroj.setText("123");
		tfSigurnosniBroj.setEditable(false);
		
		hbBrojKartice=new HBox(10, lblBrojKartice, tfBrojKartice);
		hbVlasnik=new HBox(10, lblImeVlasnika, tfImeVlasnika, lblPrezimeVlasnika, tfPrezimeVlasnika);
		hbSigurnosniBroj=new HBox(10, lblSigurnosniBroj, tfSigurnosniBroj);
		
		vbCardExample=new VBox(10, hbBrojKartice, hbVlasnik, hbSigurnosniBroj);
		
		KreditnaKarticaPageWrapper kkpw=UserOperator.getInstance().displayCC(0);
		List<KreditnaKarticaDto> kartice=kkpw.getContent();
		List<VBox> cards=new ArrayList<>();
		
		for (KreditnaKarticaDto kreditnaKarticaDto : kartice) {
			TextField tfCCBrojKartice=new TextField();
			tfCCBrojKartice.setText(kreditnaKarticaDto.getKrajKartice());
			TextField tfCCImeVlasnika=new TextField();
			tfCCImeVlasnika.setText(kreditnaKarticaDto.getImeVlasnika());
			TextField tfCCPrezimeVlasnika=new TextField();
			tfCCPrezimeVlasnika.setText(kreditnaKarticaDto.getPrezimeVlasnika());
			TextField tfCCSigurnosniBroj=new TextField();
			tfCCSigurnosniBroj.setText("***");
			
			HBox hbVlasnik=new HBox(10, tfCCImeVlasnika, tfCCPrezimeVlasnika);
			hbVlasnik.setAlignment(Pos.CENTER);
			
			VBox vbCard=new VBox(10, tfCCBrojKartice, hbVlasnik, tfCCSigurnosniBroj);
			vbCard.setAlignment(Pos.CENTER);
			
			FlowPane fpCard=new FlowPane();
			fpCard.getChildren().add(vbCard);
			fpCard.setPadding(new Insets(9, 9, 9, 9));
			fpCard.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			fpCard.setAlignment(Pos.CENTER);
			fpCard.setAlignment(Pos.CENTER);
			
			VBox kartica=new VBox(10, fpCard);
			kartica.setPadding(new Insets(9, 9, 9, 9));
			kartica.setAlignment(Pos.CENTER);
			kartica.setPrefWidth(300);
			kartica.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
			
			cards.add(kartica);
		}
		Image CCimage = new Image("ikonice/creditcard.jpg");
		ImageView CCimageView = new ImageView(CCimage);
		
		CCimageView.setFitHeight(100);
		CCimageView.setFitWidth(200);
		
		btnCreditCard=new Button("", CCimageView);
		btnCreditCard.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainView.getInstance().setScene(new CreditCardAdditionWrapper(scena).getScena());
			}
			
		});
		
		FlowPane fp=new FlowPane();
		fp.setPrefHeight(300);
		fp.getChildren().add(btnCreditCard);
		fp.getChildren().addAll(cards);
		ScrollPane kreditneKartice=new ScrollPane();
		kreditneKartice.setContent(fp);
		
		FlowPane flow=new FlowPane();
		flow.getChildren().add(vbCardExample);
		flow.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		flow.setPadding(new Insets(9, 9, 9, 9));
		vbCardInfo=new VBox(10, kreditneKartice, flow);
		vbCardInfo.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
		
		right=new VBox(10, hbFullName, vbCardInfo);
		right.setAlignment(Pos.BASELINE_CENTER);
		right.setPadding(new Insets(32, 32, 32, 32));
		
		pozadina.setRight(right);
		
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
					if(!(korisnikDto.getUsername().equals(korisnikUpdateDto.getEmail()))) {
						EmailSender.getInstance().sendEmail(korisnikUpdateDto.getEmail(), "Potvrda o promeni email adrese", "Uspešno ste promenili svoju email adresu.");
					}
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
