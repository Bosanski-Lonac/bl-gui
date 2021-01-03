package gui;

import org.controlsfx.control.RangeSlider;
import org.springframework.web.client.HttpClientErrorException;

import controller.DeleteFlightAction;
import controller.ShowFlightFormAction;
import controller.ShowPlaneFormAction;
import dto.AvionDto;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.FlightOperator;
import model.UserOperator;
import wrapper.AvionPageWrapper;
import wrapper.LetPageWrapper;

public class MainSceneWrapper extends SceneWrapper {
	private KorisnikDto korisnikDto;
	private LetCriteriaDto letCriteriaDto;
	private LetPageWrapper letPageWrapper;
	private AvionPageWrapper avionPageWrapper;
	
	private Label lblLetovi;
	private Button btnDodajLet;
	private Button btnObrisiLet;
	
	private Label lblAvioni;
	private Button btnDodajAvion;
	private Button btnObrisiAvion;
		
	private HBox hbOptions;
	private VBox vbLetovi;
	private VBox vbAvioni;
	
	private TableView<LetDto> letovi;
	private TableView<AvionDto> avioni;
	private MenuButton userButton;
	
	private MenuItem editFlights;
	private MenuItem editPlanes;
	private MenuItem edit;
	private MenuItem signOut;
	
	private HBox hbAdmin;
	
	private StackPane stack;
	
	private TextField departureFilter;
	private TextField arrivalFilter;
	
	private CheckBox durationFilter;
	private RangeSlider durationRange;
	
	private CheckBox priceFilter;
	private RangeSlider priceRange;
	
	private Accordion accordion;
	
	private Button applyFilter;
	
	private HBox top;
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
		
		if(korisnikDto.getRole() == Role.ROLE_USER) {
			edit = new MenuItem("Izmeni profil");
			edit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					MainView.getInstance().setScene(new ProfileSceneWrapper(scena).getScena());
				}
			});
			userButton = new MenuButton(korisnikDto.getUsername(), imageView, edit, signOut);
		} else if(korisnikDto.getRole() == Role.ROLE_ADMIN) {
			editFlights=new MenuItem("Rukovodi letovima");
			editFlights.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						letPageWrapper = FlightOperator.getInstance().getFlights(letCriteriaDto);
						setTableLetovi();
						hbAdmin=setFlightOptions();
					} catch (HttpClientErrorException e) {
						ExceptionHandler.prikaziGresku(e);
					}
				}
			});
			editPlanes=new MenuItem("Rukovodi avionima");
			/*editPlanes.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						avionPageWrapper = FlightOperator.getInstance().getPlanes(avionCriteriaDto);
						setTableAvioni();
						hbAdmin=setPlaneOptions();
					} catch (HttpClientErrorException e) {
						ExceptionHandler.prikaziGresku(e);
					}
				}
			});*/
			userButton = new MenuButton(korisnikDto.getUsername(), imageView, signOut, editFlights, editPlanes);
			hbAdmin=setFlightOptions();
		}
		
		if(hbAdmin!=null) {
			stack = new StackPane(hbAdmin, userButton);
		}else {
			stack = new StackPane(userButton);
		}
		stack.setAlignment(Pos.CENTER_RIGHT);
		StackPane.setMargin(userButton, new Insets(0, 10, 0, 0));
		
		top = new HBox(stack);
		top.setPadding(new Insets(15, 12, 15, 12));
		top.setSpacing(10);
		top.setStyle("-fx-background-color: #336699;");
		HBox.setHgrow(stack, Priority.ALWAYS);
		
		createTableLetovi();
		createTableAvioni();
		
		createAccordion();
		
		applyFilter = new Button("Primeni filtere");
		applyFilter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				letCriteriaDto.setBrojStranice(0);
				try {
					letPageWrapper = FlightOperator.getInstance().getFlights(letCriteriaDto);
					setTableLetovi();
				} catch (HttpClientErrorException e) {
					ExceptionHandler.prikaziGresku(e);
				}
			}
			
		});
		bottom = new HBox(applyFilter);
		bottom.setPadding(new Insets(15, 12, 15, 12));
		bottom.setAlignment(Pos.CENTER);
		bottom.setSpacing(10);
		
		pozadina.setTop(top);
		pozadina.setCenter(letovi);
		pozadina.setLeft(accordion);
		pozadina.setBottom(bottom);
		
		letCriteriaDto.setBrojStranice(0);
		
		try {
			letPageWrapper = FlightOperator.getInstance().getFlights(letCriteriaDto);
			setTableLetovi();
		} catch (HttpClientErrorException e) {
			ExceptionHandler.prikaziGresku(e);
		}
		
		this.scena=new Scene(pozadina);
	}
	
	private HBox setFlightOptions() {
		lblLetovi=new Label("Letovi");
		lblLetovi.setTextFill(Color.web("#ffffff"));
		lblLetovi.setFont(new Font("Arial", 20));
		lblLetovi.setAlignment(Pos.CENTER);
		btnDodajLet=new Button("Novi let");
		btnDodajLet.setTextFill(Color.web("#336699"));
		btnDodajLet.setPrefHeight(40);
		btnDodajLet.setOnAction(new ShowFlightFormAction(this));
		btnObrisiLet=new Button("Obriši let");
		btnObrisiLet.setTextFill(Color.web("#336699"));
		btnObrisiLet.setPrefHeight(40);
		btnObrisiLet.setOnAction(new DeleteFlightAction(this));
		
		vbLetovi=new VBox(10, lblLetovi, btnDodajLet, btnObrisiLet);
		
		Region filler = new Region();
		filler.setPrefWidth(700);
		
		hbOptions=new HBox(10, vbLetovi, filler);
		hbOptions.setAlignment(Pos.CENTER);
		hbOptions.setPadding(new Insets(10, 0, 10, 0));
		
		return hbOptions;
	}
	
	private HBox setPlaneOptions() {
		lblAvioni=new Label("Avioni");
		lblAvioni.setTextFill(Color.web("#ffffff"));
		lblAvioni.setFont(new Font("Arial", 20));
		lblAvioni.setAlignment(Pos.CENTER);
		btnDodajAvion=new Button("Novi avion");
		btnDodajAvion.setTextFill(Color.web("#336699"));
		btnDodajAvion.setPrefHeight(40);
		btnDodajAvion.setOnAction(new ShowPlaneFormAction(this));
		btnObrisiAvion=new Button("Obrisi avion");
		btnObrisiAvion.setTextFill(Color.web("#336699"));
		btnObrisiAvion.setPrefHeight(40);
		
		vbAvioni=new VBox(10, lblAvioni, btnDodajAvion, btnObrisiAvion);
		
		Region filler = new Region();
		filler.setPrefWidth(700);
		
		hbOptions=new HBox(10, vbAvioni, filler);
		hbOptions.setAlignment(Pos.CENTER);
		hbOptions.setPadding(new Insets(10, 0, 10, 0));
		
		return hbOptions;
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
	
	private void createTableAvioni() {
		avioni=new TableView<>();
		
		TableColumn<AvionDto, String> naziv=new TableColumn<>("Naziv");
		naziv.setCellValueFactory(new PropertyValueFactory<AvionDto, String>("naziv"));
		
		TableColumn<AvionDto, Integer> kapacitet=new TableColumn<>("Kapacitet");
		kapacitet.setCellValueFactory(new PropertyValueFactory<AvionDto, Integer>("kapacitet"));
		
		avioni.getColumns().addAll(naziv, kapacitet);
	}
	
	public void setTableLetovi() {
		ObservableList<LetDto> letoviData = FXCollections.observableArrayList(letPageWrapper.getContent());

		letovi.setItems(letoviData);
	}
	
	public void setTableAvioni() {
		ObservableList<AvionDto> avioniData=FXCollections.observableArrayList(avionPageWrapper.getContent());
		
		avioni.setItems(avioniData);
	}
	
	public TableView<LetDto> getLetovi(){
		return letovi;
	}
	
	public TableView<AvionDto> getAvioni(){
		return avioni;
	}
	
	public LetCriteriaDto getLetCriteriaDto() {
		return letCriteriaDto;
	}
	
	public void setLetPageWrapper(LetPageWrapper letPageWrapper) {
		this.letPageWrapper=letPageWrapper;
	}
	
	public void setAvionPageWrapper(AvionPageWrapper avionPageWrapper) {
		this.avionPageWrapper=avionPageWrapper;
	}
}
