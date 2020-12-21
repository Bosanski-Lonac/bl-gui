package gui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.HttpClientErrorException;

import controller.ShowCCFormAction;
import dto.KorisnikCUDto;
import dto.KorisnikDto;
import dto.KreditnaKarticaDto;
import enums.Rank;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
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
	
	private List<VBox> cards;
	private FlowPane fp;
	private ScrollPane kreditneKartice;
	private VBox kreditneKarticeHolder;
	
	private Accordion accordion;
	
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
		lblIme=new Label("Ime: ");
		lblPrezime=new Label("Prezime: ");
		tfIme=new TextField();
		tfIme.setText(korisnikDto.getIme());
		tfPrezime=new TextField();
		tfPrezime.setText(korisnikDto.getPrezime());
		
		hbFullName=new HBox(10, lblIme, tfIme, lblPrezime, tfPrezime);
		hbUserPass=new HBox(10, lblEmail, tfEmail, lblPassword, pfPassword);
		hbPasos=new HBox(10, lblBrojPasosa, tfBrojPasosa);
		hbPasos.setAlignment(Pos.CENTER);
		
		user=new VBox(10, hbFullName, imageView, lblRank, hbUserPass, hbPasos);
		user.setAlignment(Pos.BASELINE_CENTER);
		user.setPadding(new Insets(32, 32, 32, 32));
		
		FlowPane fpLeft=new FlowPane(user);
		fpLeft.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		fpLeft.setPadding(new Insets(9, 9, 9, 9));
		fpLeft.setBackground(new Background(new BackgroundFill(Color.web("#00bfff"), CornerRadii.EMPTY, Insets.EMPTY)));
		
		left=new VBox(10, fpLeft);
		left.setPadding(new Insets(32, 32, 32, 32));
		
		pozadina.setLeft(left);
		
		
		
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
		
		cards=new ArrayList<>();
		
		listCards(cards);
		
		Image CCimage = new Image("ikonice/creditcard.jpg");
		ImageView CCimageView = new ImageView(CCimage);
		
		CCimageView.setFitHeight(100);
		CCimageView.setFitWidth(200);
		
		btnCreditCard=new Button("", CCimageView);
		btnCreditCard.setOnAction(new ShowCCFormAction(this));
		
		fp=new FlowPane();
		fp.setPrefHeight(300);
		fp.getChildren().add(btnCreditCard);
		fp.getChildren().addAll(cards);
		kreditneKartice=new ScrollPane();
		kreditneKartice.setPrefHeight(500);
		kreditneKartice.setContent(fp);
		
		FlowPane flow=new FlowPane();
		flow.getChildren().add(vbCardExample);
		flow.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		flow.setPadding(new Insets(9, 9, 9, 9));
		vbCardInfo=new VBox(10, kreditneKartice, flow);
		vbCardInfo.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
		
		TitledPane tpKreditne=new TitledPane("Kreditne kartice", vbCardInfo);
		//TitledPane tpAvionske=new TitledPane("Avionske karte", new Button("WOOO"));
		accordion=new Accordion(tpKreditne);
		
		right=new VBox(10, accordion);
		right.setAlignment(Pos.BASELINE_CENTER);
		right.setPadding(new Insets(32, 32, 32, 32));
		
		pozadina.setRight(accordion);
		
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
	
	public void listCards(List<VBox> cards) {
		cards.removeAll(cards);
		KreditnaKarticaPageWrapper kkpw=UserOperator.getInstance().displayCC(0);
		List<KreditnaKarticaDto> kartice=kkpw.getContent();
		for (KreditnaKarticaDto kreditnaKarticaDto : kartice) {
			Label lblCCBrojKartice=new Label();
			lblCCBrojKartice.setText(kreditnaKarticaDto.getKrajKartice());
			Label lblCCImeVlasnika=new Label();
			lblCCImeVlasnika.setText(kreditnaKarticaDto.getImeVlasnika());
			Label lblCCPrezimeVlasnika=new Label();
			lblCCPrezimeVlasnika.setText(kreditnaKarticaDto.getPrezimeVlasnika());
			
			Image closeImage = new Image("ikonice/close.png");
			ImageView closeImageView = new ImageView(closeImage);
			
			closeImageView.setFitHeight(20);
			closeImageView.setFitWidth(20);
			
			Button btnClose=new Button("", closeImageView);
			btnClose.setAlignment(Pos.TOP_RIGHT);
			btnClose.setPrefHeight(20);
			btnClose.setPrefWidth(20);
			btnClose.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					UserOperator.getInstance().deleteCC(kreditnaKarticaDto.getId());
					CCRefresh();
				}
				
			});
			
			HBox hbClose=new HBox(10, btnClose);
			hbClose.setAlignment(Pos.BASELINE_RIGHT);
			
			HBox hbVlasnik=new HBox(10, lblCCImeVlasnika, lblCCPrezimeVlasnika);
			hbVlasnik.setAlignment(Pos.CENTER);
			
			VBox vbCard=new VBox(10, lblCCBrojKartice, hbVlasnik);
			vbCard.setAlignment(Pos.CENTER);
			
			BorderPane bpCard=new BorderPane();
			bpCard.setTop(hbClose);
			bpCard.setCenter(vbCard);
			bpCard.setPadding(new Insets(9, 9, 9, 9));
			bpCard.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			
			VBox kartica=new VBox(10, bpCard);
			kartica.setPadding(new Insets(9, 9, 9, 9));
			kartica.setAlignment(Pos.CENTER);
			kartica.setPrefWidth(300);
			kartica.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
			
			cards.add(kartica);
		}
	}
	
	public void CCRefresh() {
		this.listCards(this.getCards());
		FlowPane fp=new FlowPane();
		this.setFp(fp);
		this.getFp().getChildren().add(this.getBtnCreditCard());
		this.getFp().getChildren().addAll(this.getCards());
		this.getKreditneKartice().setContent(this.getFp());
	}
	
	public List<VBox> getCards(){
		return cards;
	}
	
	public FlowPane getFp() {
		return fp;
	}
	
	public void setFp(FlowPane fp) {
		this.fp=fp;
	}
	
	public ScrollPane getKreditneKartice() {
		return kreditneKartice;
	}
	
	public Button getBtnCreditCard() {
		return btnCreditCard;
	}
}
