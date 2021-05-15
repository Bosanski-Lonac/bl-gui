package gui;

import gui.komponente.IProgressable;
import javafx.application.Platform;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.Timer;
import java.util.TimerTask;

public class LaunchDialog extends Dialog<Boolean> implements IProgressable {
    private Label lblHint;
    private ProgressIndicator indicator;
    private VBox vBox;
    private Timer timer;

    public LaunchDialog() {
        super();

        BorderPane pozadina = new BorderPane();

        lblHint = new Label("Servisi se učitavaju...");
        indicator = new ProgressIndicator();
        indicator.setProgress(0);
        vBox = new VBox(10, lblHint, indicator);

        timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> fail());
            }
        };

        timer.schedule(task, 300000L);

        pozadina.setCenter(vBox);
        getDialogPane().setContent(pozadina);
        getDialogPane().setPrefWidth(400);
        getDialogPane().setPrefHeight(400);
        setTitle("Inicijalizacija...");
    }

    public void addProgress(double progress) {
        setProgress(indicator.getProgress() + progress);
    }

    public void setProgress(double progress) {
        indicator.setProgress(progress);
        if (indicator.getProgress() >= 1) {
            timer.cancel();
            this.setResult(true);
            this.close();
        }
    }

    public void fail() {
        timer.cancel();
        this.setResult(false);
        this.close();
    }
}
