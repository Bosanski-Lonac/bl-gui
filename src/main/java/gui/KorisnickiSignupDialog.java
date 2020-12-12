package gui;

import controller.DoKorisnickiSignup;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class KorisnickiSignupDialog extends Dialog<Boolean> {
	private Label emailLbl;
	private TextField emailTf;
	private Label passwordLbl;
	private TextField passwordTf;
	private Label brojPasosaLbl;
	private TextField brojPasosaTf;
	private Label imeLbl;
	private TextField imeTf;
	private Label prezimeLbl;
	private TextField prezimeTf;
	
	private Button ok;
	private Button cancel;
	
	private HBox buttons;
	
	public KorisnickiSignupDialog() {
		super();
		
		BorderPane pozadina=new BorderPane();
		
		emailLbl=new Label("Email");
		emailTf=new TextField();
		passwordLbl=new Label("Password");
		passwordTf=new TextField();
		brojPasosaLbl=new Label("Broj pasosa");
		brojPasosaTf=new TextField();
		imeLbl=new Label("Ime");
		imeTf=new TextField();
		prezimeLbl=new Label("Prezime");
		prezimeTf=new TextField();
		
		GridPane forma=new GridPane();
		forma.setVgap(8);
		forma.setHgap(8);
		forma.setAlignment(Pos.CENTER);
		
		forma.add(emailLbl, 0, 0);
		forma.add(emailTf, 0, 1);
		forma.add(passwordLbl, 0, 2);
		forma.add(passwordTf, 0, 3);
		forma.add(brojPasosaLbl, 0, 4);
		forma.add(brojPasosaTf, 0, 5);
		forma.add(imeLbl, 0, 6);
		forma.add(imeTf, 0, 7);
		forma.add(prezimeLbl, 0, 8);
		forma.add(prezimeTf, 0, 9);
		
		pozadina.setCenter(forma);
		
		ok=new Button("Ok");
		ok.setOnAction(new DoKorisnickiSignup(this));
		cancel=new Button("Cancel");
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Stage stage=(Stage)cancel.getScene().getWindow();
				stage.close();
			}
		});
		
		buttons=new HBox(ok, cancel);
		buttons.setAlignment(Pos.CENTER);
		
		pozadina.setBottom(buttons);
		
		getDialogPane().setContent(pozadina);
		getDialogPane().setPrefWidth(400);
		getDialogPane().setPrefHeight(600);
		setTitle("Korisnicki sign up");
	}
	
	public String getEmail() {
		return emailTf.getText();
	}
	
	public String getPassword() {
		return passwordTf.getText();
	}
	
	public String getBrojPasosa() {
		return brojPasosaTf.getText();
	}
	
	public String getIme() {
		return imeTf.getText();
	}
	
	public String getPrezime() {
		return prezimeTf.getText();
	}
}
