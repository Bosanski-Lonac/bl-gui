package gui.komponente;

import java.util.Optional;

import dto.KartaDto;
import dto.LetDto;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.TicketOperator;

public class KartaDisplay extends GridPane {
	private LetDisplay letPrikaz;
	private Label lblDatum;
	private Label lblCena;
	private Button btnObrisi;
	
	public KartaDisplay(IRefreshable refreshable, KartaDto kartaDto, LetDto letDto) {
		super();
		
		this.setVgap(8);
		this.setHgap(8);
		this.setAlignment(Pos.CENTER);
		
		letPrikaz = new LetDisplay(letDto);
		lblDatum = new Label(kartaDto.getDatumKupovine().toString());
		lblCena = new Label(kartaDto.getCena() + " RSD");
		
		Image image = new Image("ikonice/close.png");
		ImageView imageView = new ImageView(image);
		
		imageView.setFitWidth(16);
		imageView.setFitHeight(16);
		
		btnObrisi = new Button("", imageView);
		btnObrisi.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Da li sigurno želite da obrišete ovu rezervaciju?");
				Optional<ButtonType> result = alert.showAndWait();
				if(result.isPresent() && result.get() == ButtonType.OK) {
					TicketOperator.getInstance().deleteKarta(kartaDto.getId());
					refreshable.setPage(-2);
				}
			}
		});
		
		this.add(letPrikaz, 0, 0);
		GridPane.setColumnSpan(letPrikaz, 2);
		this.add(btnObrisi, 2, 0);
		this.add(lblDatum, 0, 1);
		this.add(lblCena, 1, 1);
	}
}
