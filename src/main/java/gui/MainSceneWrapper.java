package gui;

import dto.KorisnikDto;
import dto.LetDto;
import enums.Role;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import model.UserOperator;

public class MainSceneWrapper extends SceneWrapper {
	
	private TableView<LetDto> flights;
	private MenuButton userButton;
	
	private MenuItem edit;
	private MenuItem signOut;
	
	private StackPane stack;
	
	private Button search;
	
	private HBox top;
	private HBox bottom;
	
	public MainSceneWrapper(KorisnikDto korisnikDto) {
		BorderPane pozadina=new BorderPane();
		
		top = new HBox();
		bottom = new HBox();
		
		stack = new StackPane();
		
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
			userButton = new MenuButton(korisnikDto.getUsername(), imageView, signOut);
		}
		
		
		stack.getChildren().addAll(userButton);
		stack.setAlignment(Pos.CENTER_RIGHT);
		StackPane.setMargin(userButton, new Insets(0, 10, 0, 0));
		
		top.setPadding(new Insets(15, 12, 15, 12));
		top.setSpacing(10);
		top.setStyle("-fx-background-color: #336699;");
		
		top.getChildren().add(stack);
		HBox.setHgrow(stack, Priority.ALWAYS);
		
		flights = new TableView<>();
		
		search = new Button("Pretraga");
		bottom.setPadding(new Insets(15, 12, 15, 12));
		bottom.getChildren().add(search);
		bottom.setAlignment(Pos.CENTER);
		
		pozadina.setTop(top);
		pozadina.setCenter(flights);
		pozadina.setBottom(bottom);
		
		this.scena=new Scene(pozadina);
	}
}
