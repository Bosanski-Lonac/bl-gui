package gui;

import controller.SignupAction;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class MainView extends Stage {
	private static MainView instance=null;
	
	private Button signupBtn;
	private CheckBox cbAdmin;
	
	private Label title;
	private Label usernameLbl;
	private TextField usernameTf;
	private Label signupLbl;
	private TextField signupTf;
	
	private HBox hbOptions;
	
	private MainView() {
		BorderPane pozadina=new BorderPane();
		
		signupBtn=new Button("Sign up");
		signupBtn.setOnAction(new SignupAction());
		cbAdmin=new CheckBox("Admin");
		
		cbAdmin.selectedProperty().addListener(new ChangeListener<Boolean>() {
	           public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
	               if(new_val) {
	            	   title.setText("Admin login:");
	            	   usernameLbl.setText("Username");
	               }else {
	            	   title.setText("Korisnicki login:");
	            	   usernameLbl.setText("Email");
	               }
	            }
	        });
		
		Region spacing=new Region();
		HBox.setHgrow(spacing, Priority.ALWAYS);
		
		hbOptions=new HBox(signupBtn, spacing, cbAdmin);
		
		pozadina.setTop(hbOptions);
		
		title=new Label("Korisnicki login:");
		usernameLbl=new Label("Email");
		usernameTf=new TextField();
		signupLbl=new Label("Sign up");
		signupTf=new TextField();
		
		GridPane forma=new GridPane();
		forma.setHgap(8);
		forma.setVgap(8);
		forma.setAlignment(Pos.CENTER);
		
		forma.add(title, 0, 0);
		forma.add(usernameLbl, 0, 1);
		forma.add(usernameTf, 0, 2);
		forma.add(signupLbl, 0, 3);
		forma.add(signupTf, 0, 4);
		
		pozadina.setCenter(forma);
		
		Scene scena=new Scene(pozadina);
		setScene(scena);
		setWidth(400);
		setHeight(400);
		setTitle("Login or sign up");
		show();
	}
	
	public CheckBox getCbAdmin() {
		return cbAdmin;
	}
	
	public static MainView getInstance() {
		if(instance==null) {
			instance=new MainView();
		}
		return instance;
	}
}
