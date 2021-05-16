package gui;

import gui.komponente.IProgressable;
import javafx.application.Platform;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.util.Timer;
import java.util.TimerTask;

public class LaunchDialog extends Dialog<Boolean> implements IProgressable {
    private static final double threshold = Math.pow(10, -12);

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
                Platform.runLater(() -> finish(false));
            }
        };

        timer.schedule(task, 180000L);

        pozadina.setCenter(vBox);
        getDialogPane().setContent(pozadina);
        getDialogPane().setPrefWidth(400);
        getDialogPane().setPrefHeight(400);
        Window window = getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> finish(false));
        setTitle("Inicijalizacija...");
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
