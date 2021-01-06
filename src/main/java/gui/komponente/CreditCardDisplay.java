package gui.komponente;

import java.util.Optional;

import dto.KreditnaKarticaDto;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.UserOperator;

public class CreditCardDisplay extends HBox {
	private RadioButton radioButton;
	private Label lblBrojKartice;
	private Label lblPunoIme;
	private Button btnObrisi;
	
	public CreditCardDisplay(IRefreshable refreshable, KreditnaKarticaDto kreditnaKarticaDto) {
		super(16);
		
		radioButton = new RadioButton();
		radioButton.setUserData(kreditnaKarticaDto);
		lblBrojKartice = new Label(kreditnaKarticaDto.getKrajKartice());
		lblPunoIme = new Label(kreditnaKarticaDto.getImeVlasnika() + " " + kreditnaKarticaDto.getPrezimeVlasnika());
		
		Image image = new Image("ikonice/close.png");
		ImageView imageView = new ImageView(image);
		
		imageView.setFitWidth(16);
		imageView.setFitHeight(16);
		
		btnObrisi = new Button("", imageView);
		btnObrisi.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Da li sigurno želite da obrišete ovu kreditnu karticu?");
				Optional<ButtonType> result = alert.showAndWait();
				if(result.isPresent() && result.get() == ButtonType.OK) {
					UserOperator.getInstance().deleteCC(kreditnaKarticaDto.getId());
					refreshable.setPage(-2);
				}
			}
		});
		
		VBox center = new VBox(lblBrojKartice, lblPunoIme);
		center.setAlignment(Pos.CENTER);
		
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(25, 25, 25, 25));
		this.setStyle("-fx-background-color: white;" +
		"-fx-background-insets: 8, 0, 8, 0;" + 
		"-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 8, 0, 0, 0);");
		
		this.getChildren().add(radioButton);
		this.getChildren().add(center);
		this.getChildren().add(btnObrisi);
	}
	
	public RadioButton getRadioButton() {
		return radioButton;
	}
}
