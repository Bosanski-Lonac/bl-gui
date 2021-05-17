package gui;

import gui.komponente.IProgressable;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Window;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class LaunchDialog extends Dialog<Boolean> implements IProgressable {
    private static final double threshold = Math.pow(10, -12);

    private Label lbHint;
    private Text lbInfo;
    private ProgressIndicator indicator;
    private VBox vBox;
    private Timer timer;

    public LaunchDialog() {
        super();

        BorderPane pozadina = new BorderPane();

        lbHint = new Label("Ovaj proces moze potrajati par minuta.\rMolimo sacekajte!");
        lbInfo = new Text("Ovo je demo verzija aplikacije koja je prilozena za testiranje upotrebljivosti. Pri popunjavanju podataka, koristite svoju pravu email adresu ali nemojte da koristite prave brojeve za kreditne kartice i pasose. Posle nekog vremena koriscenja aplikacije, izaci ce notifikacija za anketu, koju bismo Vas zamolili da popunite.");
        indicator = new ProgressIndicator();
        indicator.setProgress(0);
        indicator.setMinHeight(80);
        indicator.setMinWidth(80);

        lbHint.setStyle("-fx-font-size: 14");
        lbInfo.setStyle("-fx-font-size: 14");
        lbHint.setTextAlignment(TextAlignment.CENTER);
        lbInfo.setTextAlignment(TextAlignment.CENTER);
        lbInfo.setWrappingWidth(700);

        vBox = new VBox(20, lbHint, indicator, lbInfo);
        VBox.setVgrow(vBox, Priority.ALWAYS);
        vBox.setAlignment(Pos.CENTER);

        timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> finish(false));
            }
        };

        timer.schedule(task, 180000L);

        pozadina.setCenter(vBox);
        getDialogPane().setContent(pozadina);
        getDialogPane().setPrefWidth(800);
        getDialogPane().setPrefHeight(400);
        Window window = getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> finish(false));
        setTitle("Ucitavanje servisa");
    }

    public void addProgress(double progress) {
        setProgress(indicator.getProgress() + progress);
    }

    public void setProgress(double progress) {
        indicator.setProgress(progress);
        if (Math.abs(indicator.getProgress() - 1.0) < threshold) {
            finish(true);
        }
    }

    public void finish(boolean success) {
        timer.cancel();
        this.setResult(success);
        this.close();
    }
}
