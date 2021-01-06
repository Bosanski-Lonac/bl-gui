package gui;

import org.springframework.web.client.HttpClientErrorException;

import controller.DeletePlaneAction;
import controller.ShowFlightFormAction;
import controller.ShowPlaneFormAction;
import dto.AvionDto;
import dto.KorisnikDto;
import gui.komponente.ExceptionHandler;
import gui.komponente.IRefreshable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import wrapper.AvionPageWrapper;

public class PlaneSceneWrapper extends SceneWrapper implements IRefreshable {
	private KorisnikDto korisnikDto;
	private AvionPageWrapper avionPageWrapper;
	
	private MenuButton userButton;
	
	private MenuItem defaultView;
	private MenuItem signOut;
	
	private StackPane stack;
	
	private Pagination pagination;
	private TableView<AvionDto> avioni;
	
	private Button btnDodajLet;
	private Button btnDodajAvion;
	private Button btnObrisiAvion;
	
	private HBox top;
	private VBox center;
	private HBox bottom;
	
	public PlaneSceneWrapper(Scene scena) {
		BorderPane pozadina=new BorderPane();
		
		korisnikDto = UserOperator.getInstance().getUserInfo(false);
		
		defaultView = new MenuItem("Glavna");
		defaultView.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainView.getInstance().setScene(scena);
			}
			
		});
		
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
		
		userButton = new MenuButton(korisnikDto.getUsername(), imageView, defaultView, signOut);
		
		stack = new StackPane(userButton);
		stack.setAlignment(Pos.CENTER_RIGHT);
		StackPane.setMargin(userButton, new Insets(0, 10, 0, 0));
		
		top = new HBox(stack);
		top.setPadding(new Insets(15, 12, 15, 12));
		top.setSpacing(10);
		top.setStyle("-fx-background-color: #336699;");
		HBox.setHgrow(stack, Priority.ALWAYS);
		
		createTableAvioni();
		pagination = new Pagination();
		pagination.setStyle("-fx-page-information-visible: false;");
		pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> setPage(newIndex.intValue()));
		
		center = new VBox(pagination, avioni);
		
		btnDodajLet = new Button("Novi let za avion");
		btnDodajLet.setOnAction(new ShowFlightFormAction(this));
		btnDodajAvion = new Button("Dodaj avion");
		btnDodajAvion.setOnAction(new ShowPlaneFormAction(this));
		btnObrisiAvion = new Button("Obriši avion");
		btnObrisiAvion.setOnAction(new DeletePlaneAction(this));
		
		bottom = new HBox(btnDodajLet, btnDodajAvion, btnObrisiAvion);
		bottom.setPadding(new Insets(15, 12, 15, 12));
		bottom.setAlignment(Pos.CENTER);
		bottom.setSpacing(10);
		
		pozadina.setTop(top);
		pozadina.setCenter(center);
		pozadina.setBottom(bottom);
		
		setPage(0);
		
		this.scena=new Scene(pozadina);
	}
	
	private void createTableAvioni() {
		avioni=new TableView<>();
		VBox.setVgrow(avioni, Priority.ALWAYS);
		
		TableColumn<AvionDto, String> naziv=new TableColumn<>("Naziv");
		naziv.setCellValueFactory(new PropertyValueFactory<AvionDto, String>("naziv"));
		
		TableColumn<AvionDto, Integer> kapacitet=new TableColumn<>("Kapacitet Putnika");
		kapacitet.setCellValueFactory(new PropertyValueFactory<AvionDto, Integer>("kapacitetPutnika"));
		
		avioni.getColumns().add(naziv);
		avioni.getColumns().add(kapacitet);
	}
	
	public void setPage(int page) {
		if(avioni.getItems().size() < 2 && page == -2 && pagination.getCurrentPageIndex() > 1) {
			page = pagination.getCurrentPageIndex() - 1;
		} else if(page < 0) {
			page = pagination.getCurrentPageIndex();
		}
		try {
			avionPageWrapper = FlightOperator.getInstance().getPlanes(page);
		} catch (HttpClientErrorException e) {
			ExceptionHandler.prikaziGresku(e);
			return;
		}
		ObservableList<AvionDto> avioniData=FXCollections.observableArrayList(avionPageWrapper.getContent());
		avioni.setItems(avioniData);
		pagination.setPageCount(avionPageWrapper.getTotalPages());
	}
	
	public TableView<AvionDto> getAvioni() {
		return avioni;
	}
}
