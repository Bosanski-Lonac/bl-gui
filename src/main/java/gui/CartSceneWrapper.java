package gui;

import java.math.BigDecimal;

import org.springframework.web.client.HttpClientErrorException;

import dto.KorisnikDto;
import dto.LetDto;
import gui.komponente.CreditCardList;
import gui.komponente.ExceptionHandler;
import gui.komponente.LetDisplay;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.TicketOperator;
import model.UserOperator;

public class CartSceneWrapper extends SceneWrapper {
	private LetDto letDto;
	private CreditCardList lista;
	private LetDisplay letPrikaz;
	private Spinner<Integer> kolicina;
	private Label cena;
	private Label popust;
	private Label pravaCena;
	private Button btnRezervisi;
	private Button btnCancel;
	
	public CartSceneWrapper(Scene scena, LetDto letDto) {
		this.letDto = letDto;
		BorderPane pozadina = new BorderPane();
		
		lista = new CreditCardList(this);
		
		letPrikaz = new LetDisplay(letDto);
		kolicina = new Spinner<>(1, letDto.getKapacitet(), 1);
		kolicina.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setCena(newValue);
			}
		});
		cena = new Label();
		popust = new Label();
		pravaCena = new Label();
		setCena(1);
		
		btnRezervisi = new Button("Plati");
		btnRezervisi.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(lista.getSelected() == null) {
					Alert alert = new Alert(AlertType.ERROR, "Niste izabrali karticu.");
					alert.showAndWait();
					return;
				}
				try {
					TicketOperator.getInstance().reserve(letDto.getId(),
							lista.getSelected().getId(), kolicina.getValue());
					TicketOperator.getInstance().displayKarte(0);
					MainView.getInstance().setScene(scena);
				} catch (HttpClientErrorException e) {
					ExceptionHandler.prikaziGresku(e);
				}
			}
		});
		btnCancel = new Button("Poništi");
		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainView.getInstance().setScene(scena);
			}
		});
		
		HBox bottom = new HBox(btnRezervisi, btnCancel);
		bottom.setAlignment(Pos.CENTER);
		BorderPane cart = new BorderPane();
		VBox opcije = new VBox(kolicina, cena, popust, pravaCena, bottom);
		cart.setPadding(new Insets(16, 16, 16, 16));
		cart.setCenter(letPrikaz);
		cart.setBottom(opcije);
		
		pozadina.setRight(cart);
		pozadina.setCenter(lista);
		
		this.scena = new Scene(pozadina);
	}
	
	public void setCena(int kolicina) {
		KorisnikDto korisnikDto = UserOperator.getInstance().getUserInfo(false);
		
		BigDecimal totalCena = letDto.getCena().multiply(new BigDecimal(kolicina));
		BigDecimal customCena = totalCena.multiply(
				new BigDecimal( (100 - korisnikDto.odrediRank().getPopust()) / 100));
		cena.setText("Cena: " + totalCena.toString() + " RSD");
		popust.setText("Vaš popust: " + korisnikDto.odrediRank().getPopust() + "%");
		pravaCena.setText("Vaša cena: " + customCena.toString() + " RSD");
	}
}
