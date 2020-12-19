package gui;

import org.controlsfx.control.RangeSlider;

import dto.LetCriteriaDto;
import dto.LetDto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.UserOperator;

public class AdminSceneWrapper extends SceneWrapper {
	private StackPane stack;
	
	private Label lblLetovi;
	private Button btnDodajLet;
	private Button btnObrisiLet;
	
	private Label lblAvioni;
	private Button btnDodajAvion;
	private Button btnObrisiAvion;
	
	private Button btnOdjava;
	
	private HBox hbOptions;
	private VBox vbLetovi;
	private VBox vbAvioni;
	private HBox hbTop;
	
	private Accordion accordion;
	private LetCriteriaDto letCriteriaDto;
	private CheckBox priceFilter;
	private RangeSlider priceRange;
	private TextField departureFilter;
	private TextField arrivalFilter;
	private CheckBox durationFilter;
	private RangeSlider durationRange;
	
	private TableView<LetDto> letovi;
	
	public AdminSceneWrapper() {
		BorderPane pozadina=new BorderPane();
		
		lblLetovi=new Label("Letovi");
		lblLetovi.setTextFill(Color.web("#ffffff"));
		lblLetovi.setFont(new Font("Arial", 20));
		lblLetovi.setAlignment(Pos.CENTER);
		btnDodajLet=new Button("Novi let");
		btnDodajLet.setTextFill(Color.web("#336699"));
		btnDodajLet.setPrefHeight(40);
		btnObrisiLet=new Button("Obriši let");
		btnObrisiLet.setTextFill(Color.web("#336699"));
		btnObrisiLet.setPrefHeight(40);
		
		lblAvioni=new Label("Avioni");
		lblAvioni.setTextFill(Color.web("#ffffff"));
		lblAvioni.setFont(new Font("Arial", 20));
		lblAvioni.setAlignment(Pos.CENTER);
		btnDodajAvion=new Button("Novi avion");
		btnDodajAvion.setTextFill(Color.web("#336699"));
		btnDodajAvion.setPrefHeight(40);
		btnObrisiAvion=new Button("Obrisi avion");
		btnObrisiAvion.setTextFill(Color.web("#336699"));
		btnObrisiAvion.setPrefHeight(40);
		
		btnOdjava=new Button("Odjava");
		btnOdjava.setTextFill(Color.web("#336699"));
		btnOdjava.setFont(new Font("Arial", 20));
		btnOdjava.setAlignment(Pos.BASELINE_RIGHT);
		btnOdjava.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				UserOperator.getInstance().signOut();
				MainView.getInstance().setScene(new LoginSceneWrapper().getScena());
			}
		});
		
		vbLetovi=new VBox(10, lblLetovi, btnDodajLet, btnObrisiLet);
		vbAvioni=new VBox(10, lblAvioni, btnDodajAvion, btnObrisiAvion);
		
		Region filler = new Region();
		filler.setPrefWidth(700);
		
		hbOptions=new HBox(10, vbLetovi, vbAvioni, filler, btnOdjava);
		hbOptions.setAlignment(Pos.CENTER);
		hbOptions.setPadding(new Insets(10, 0, 10, 0));
		
		stack=new StackPane(hbOptions);
		stack.setAlignment(Pos.CENTER_RIGHT);
		StackPane.setMargin(hbOptions, new Insets(0, 10, 0, 0));
		hbTop=new HBox(stack);
		hbTop.setAlignment(Pos.CENTER);
		hbTop.setPrefHeight(100);
		HBox.setHgrow(stack, Priority.ALWAYS);
		hbTop.setStyle("-fx-background-color: #336699;");
		
		pozadina.setTop(hbTop);
		
		createTable();
		
		pozadina.setCenter(letovi);
		
		createAccordion();
		
		pozadina.setLeft(accordion);
		
		this.scena=new Scene(pozadina);
	}
	
	private void createAccordion() {
		departureFilter = new TextField();
		departureFilter.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				letCriteriaDto.setPocetnaDestinacija(newValue);
			}
			
		});
		VBox departureBox = new VBox(departureFilter);
		TitledPane departureTpane = new TitledPane("Pocetna destinacija", departureBox);
		
		arrivalFilter = new TextField();
		arrivalFilter.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				letCriteriaDto.setKrajnjaDestinacija(newValue);
			}
			
		});
		VBox arrivalBox = new VBox(arrivalFilter);
		TitledPane arrivalTpane = new TitledPane("Krajnja destinacija", arrivalBox);
		
		durationFilter = new CheckBox("Koristi filter");
		durationFilter.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					durationRange.setDisable(false);
				} else {
					durationRange.setDisable(true);
					letCriteriaDto.setMinDuzina(null);
					letCriteriaDto.setMaxDuzina(null);
				}
			}
			
		});
		durationRange = new RangeSlider(0, 500, 120, 420);
		durationRange.lowValueChangingProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					letCriteriaDto.setMinDuzina((int)durationRange.getLowValue());
				}
			}
			
		});
		durationRange.highValueChangingProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					letCriteriaDto.setMaxDuzina((int)durationRange.getHighValue());
				}
			}
			
		});
		durationRange.setShowTickLabels(true);
		durationRange.setShowTickMarks(true);
		durationRange.setBlockIncrement(1f);
		durationRange.setMajorTickUnit(10f);
		durationRange.setDisable(true);
		VBox durationBox = new VBox(10, durationFilter, durationRange);
		TitledPane durationTpane = new TitledPane("Duzina", durationBox);
		
		priceFilter = new CheckBox("Koristi filter");
		priceFilter.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					priceRange.setDisable(false);
				} else {
					priceRange.setDisable(true);
					letCriteriaDto.setMinCena(null);
					letCriteriaDto.setMaxCena(null);
				}
			}
			
		});
		priceRange = new RangeSlider(0, 500, 100, 500);
		priceRange.lowValueChangingProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					letCriteriaDto.setMinCena((int)priceRange.getLowValue());
				}
			}
			
		});
		priceRange.highValueChangingProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue) {
					letCriteriaDto.setMaxCena((int)priceRange.getHighValue());
				}
			}
			
		});
		priceRange.setShowTickLabels(true);
		priceRange.setShowTickMarks(true);
		priceRange.setBlockIncrement(1f);
		priceRange.setMajorTickUnit(20f);
		priceRange.setDisable(true);
		VBox priceBox = new VBox(10, priceFilter, priceRange);
		TitledPane cenaTpane = new TitledPane("Cena", priceBox);
		
		accordion = new Accordion(departureTpane, arrivalTpane, durationTpane, cenaTpane);
	}
	
	private void createTable() {
		letovi = new TableView<>();
		
		TableColumn<LetDto, String> pocetnaDestinacija = new TableColumn<>("Pocetna destinacija");
		pocetnaDestinacija.setCellValueFactory(new PropertyValueFactory<LetDto, String>("pocetnaDestinacija"));
		
		TableColumn<LetDto, String> krajnjaDestinacija = new TableColumn<>("Krajnja destinacija");
		krajnjaDestinacija.setCellValueFactory(new PropertyValueFactory<LetDto, String>("krajnjaDestinacija"));
		
		TableColumn<LetDto, Integer> duzina = new TableColumn<>("Duzina");
		duzina.setCellValueFactory(new PropertyValueFactory<LetDto, Integer>("duzina"));
		
		TableColumn<LetDto, Integer> cena = new TableColumn<>("Cena");
		cena.setCellValueFactory(new PropertyValueFactory<LetDto, Integer>("cena"));
		
		TableColumn<LetDto, Integer> kapacitet = new TableColumn<>("Kapacitet");
		kapacitet.setCellValueFactory(new PropertyValueFactory<LetDto, Integer>("kapacitet"));
		
		letovi.getColumns().addAll(pocetnaDestinacija, krajnjaDestinacija, duzina, cena, kapacitet);
	}
}
