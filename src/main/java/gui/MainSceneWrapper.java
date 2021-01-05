package gui;

import org.controlsfx.control.RangeSlider;
import org.springframework.web.client.HttpClientErrorException;

import controller.DeleteFlightAction;
import dto.KorisnikDto;
import dto.LetCriteriaDto;
import dto.LetDto;
import enums.Role;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.FlightOperator;
import model.UserOperator;
import wrapper.LetPageWrapper;

public class MainSceneWrapper extends SceneWrapper {
	private KorisnikDto korisnikDto;
	private LetCriteriaDto letCriteriaDto;
	private LetPageWrapper letPageWrapper;
	
	private MenuButton userButton;
	
	private MenuItem editPlanes;
	private MenuItem edit;
	private MenuItem signOut;
	
	private StackPane stack;
	
	private TextField departureFilter;
	private TextField arrivalFilter;
	
	private CheckBox durationFilter;
	private RangeSlider durationRange;
	
	private CheckBox priceFilter;
	private RangeSlider priceRange;
	
	private Accordion accordion;
	
	private Pagination pagination;
	private TableView<LetDto> letovi;
	
	private Button btnObrisiLet;
	private Button applyFilter;
	private Button btnReserve;
	
	private HBox top;
	private VBox center;
	private HBox bottom;
	
	public MainSceneWrapper() {
		BorderPane pozadina=new BorderPane();
		
		korisnikDto = UserOperator.getInstance().getUserInfo(false);
		letCriteriaDto = new LetCriteriaDto();
		
		signOut = new MenuItem("Odjavi se");
		signOut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				UserOperator.getInstance().signOut();
				MainView.getInstance().setScene(new LoginSceneWrapper().getScena());
			}
		});
		
		Image image = new Image("ikonice/profile.png");
		ImageView imageView = new ImageView(image);
		
		imageView.setFitHeight(24);
		imageView.setFitWidth(24);
		
		applyFilter = new Button("Primeni filtere");
		applyFilter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setTableLetovi(0);
			}
			
		});
		
		if(korisnikDto.getRole() == Role.ROLE_USER) {
			edit = new MenuItem("Izmeni profil");
			edit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					MainView.getInstance().setScene(new ProfileSceneWrapper(scena).getScena());
				}
			});
			userButton = new MenuButton(korisnikDto.getUsername(), imageView, edit, signOut);
			
			btnReserve = new Button("Rezerviši");
			// Otvori dijalog za selekciju kreditne kartice
			bottom = new HBox(applyFilter, btnReserve);
		} else if(korisnikDto.getRole() == Role.ROLE_ADMIN) {
			editPlanes=new MenuItem("Rukovođstvo");
			editPlanes.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					MainView.getInstance().setScene(new PlaneSceneWrapper(scena).getScena());
				}
			});
			userButton = new MenuButton(korisnikDto.getUsername(), imageView, editPlanes, signOut);
			
			btnObrisiLet=new Button("Obriši let");
			btnObrisiLet.setOnAction(new DeleteFlightAction(this));
			bottom = new HBox(applyFilter, btnObrisiLet);
		}
		
		stack = new StackPane(userButton);
		stack.setAlignment(Pos.CENTER_RIGHT);
		StackPane.setMargin(userButton, new Insets(0, 10, 0, 0));
		
		top = new HBox(stack);
		top.setPadding(new Insets(15, 12, 15, 12));
		top.setSpacing(10);
		top.setStyle("-fx-background-color: #336699;");
		HBox.setHgrow(stack, Priority.ALWAYS);
		
		createTableLetovi();
		pagination = new Pagination();
		pagination.setStyle("-fx-page-information-visible: false;");
		pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> setTableLetovi(newIndex.intValue()));
		
		center = new VBox(pagination, letovi);
		
		createAccordion();
		
		bottom.setPadding(new Insets(15, 12, 15, 12));
		bottom.setAlignment(Pos.CENTER);
		bottom.setSpacing(10);
		
		pozadina.setTop(top);
		pozadina.setCenter(center);
		pozadina.setLeft(accordion);
		pozadina.setBottom(bottom);
		
		setTableLetovi(0);
		
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
	
	private void createTableLetovi() {
		letovi = new TableView<>();
		VBox.setVgrow(letovi, Priority.ALWAYS);
		
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
		
		letovi.getColumns().add(pocetnaDestinacija);
		letovi.getColumns().add(krajnjaDestinacija);
		letovi.getColumns().add(duzina);
		letovi.getColumns().add(cena);
		letovi.getColumns().add(kapacitet);
	}
	
	public void setTableLetovi(int page) {
		letCriteriaDto.setBrojStranice(page);
		try {
			letPageWrapper = FlightOperator.getInstance().getFlights(letCriteriaDto);
		} catch (HttpClientErrorException e) {
			ExceptionHandler.prikaziGresku(e);
			return;
		}
		ObservableList<LetDto> letoviData = FXCollections.observableArrayList(letPageWrapper.getContent());
		letovi.setItems(letoviData);
		pagination.setPageCount(letPageWrapper.getTotalPages());
	}
	
	public TableView<LetDto> getLetovi(){
		return letovi;
	}
}
