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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.UserOperator;
import wrapper.KreditnaKarticaPageWrapper;

public class ProfileSceneWrapper extends SceneWrapper {
	private Label lblEmail;
	private TextField tfEmail;
	private Label lblPassword;
	private PasswordField pfPassword;
	private TextField tfBrojPasosa;
	private Label lblIme;
	private TextField tfIme;
	private Label lblPrezime;
	private TextField tfPrezime;
	private Label lblRank;
	
	private HBox hbUserPass;
	private VBox left;
	private HBox hbFullName;
	
	private HBox hbCards;
	
	private Button visaBtn;
	private Button mcBtn;
		
	private Button cancel;
	private Button save;
	
	private HBox bottom;
	private VBox vbCardInfo;
	private VBox right;
	
	@SuppressWarnings("unused")
	public ProfileSceneWrapper(Scene glavniEkran, KorisnikDto korisnikDto) {
		BorderPane pozadina=new BorderPane();
		
		Image image = new Image("ikonice/profile.png");
		ImageView imageView = new ImageView(image);
		
		imageView.setFitHeight(100);
		imageView.setFitWidth(100);
		
		/*lblRank=new Label(korisnikDto.getRank().toString());
		if(korisnikDto.getRank()==Rank.BRONZA) {
			lblRank.setTextFill(Color.web("#cd7f32"));
		}else if(korisnikDto.getRank()==Rank.SREBRO) {
			lblRank.setTextFill(Color.web("#c0c0c0"));
		}else {
			lblRank.setTextFill(Color.web("#ffd700"));
		}*/
		
		lblRank=new Label("Bronza");
		lblRank.setTextFill(Color.web("#cd7f32"));
		
		lblEmail=new Label("Email: ");
		lblPassword=new Label("Sifra: ");
		tfEmail=new TextField();
		tfEmail.setText(korisnikDto.getUsername());
		pfPassword=new PasswordField();
		
		hbUserPass=new HBox(10, lblEmail, tfEmail, lblPassword, pfPassword);
		
		left=new VBox(10, imageView, lblRank, hbUserPass);
		
		pozadina.setLeft(left);
		
		lblIme=new Label("Ime: ");
		lblPrezime=new Label("Prezime: ");
		tfIme=new TextField();
		tfIme.setText(korisnikDto.getIme());
		tfPrezime=new TextField();
		tfPrezime.setText(korisnikDto.getPrezime());
		
		hbFullName=new HBox(10, lblIme, tfIme, lblPrezime, tfPrezime);
		
		KreditnaKarticaPageWrapper kkpw=UserOperator.getInstance().displayCC();
		List<KreditnaKarticaDto> kartice=kkpw.getContent();
		
		Image visaKarticaImg = new Image("ikonice/visa.png");
		ImageView visaKarticaImgView = new ImageView(visaKarticaImg);
		
		visaKarticaImgView.setFitHeight(50);
		visaKarticaImgView.setFitWidth(50);
		
		visaBtn=new Button("Visa", visaKarticaImgView);
		visaBtn.setDisable(true);
		
		Image mcKarticaImg = new Image("ikonice/mastercard.png");
		ImageView mcKarticaImgView = new ImageView(mcKarticaImg);
		
		mcKarticaImgView.setFitHeight(50);
		mcKarticaImgView.setFitWidth(50);
		
		mcBtn=new Button("MasterCard", mcKarticaImgView);
		mcBtn.setDisable(true);
		
		hbCards=new HBox(10, visaBtn, mcBtn);
		
		List<VBox> cards=new ArrayList<>();
		
		for (KreditnaKarticaDto kreditnaKarticaDto : kartice) {
			Label lblBrojKartice=new Label("Broj: ");
			Label lblImeVlasnika=new Label("Ime: ");
			Label lblPrezimeVlasnika=new Label("Prezime: ");
			Label lblSigurnosniBroj=new Label("Sigurnosni broj: ");
			
			TextField tfImeVlasnika=new TextField();
			TextField tfPrezimeVlasnika=new TextField();
			TextField tfBrojKartice=new TextField();
			TextField tfSigurnosniBroj=new TextField();
			
			HBox hbBrojKartice=new HBox(10, lblBrojKartice, tfBrojKartice);
			HBox hbVlasnik=new HBox(10, lblImeVlasnika, tfImeVlasnika, lblPrezimeVlasnika, tfPrezimeVlasnika);
			HBox hbSigurnosniBroj=new HBox(10, lblSigurnosniBroj, tfSigurnosniBroj);
			VBox vbCardInfo=new VBox(10, hbBrojKartice, hbVlasnik, hbSigurnosniBroj);
			
			cards.add(vbCardInfo);
		}
		FlowPane fp=new FlowPane();
		fp.getChildren().addAll(cards);
		ScrollPane kreditneKartice=new ScrollPane();
		kreditneKartice.setContent(fp);
		
		vbCardInfo=new VBox(10, kreditneKartice, hbCards);
		
		right=new VBox(10, hbFullName, vbCardInfo);
		
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
