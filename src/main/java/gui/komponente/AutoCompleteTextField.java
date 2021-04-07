package gui.komponente;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class AutoCompleteTextField extends TextField {
    private SortedSet<String> elementi;
    private ContextMenu popup;

    public AutoCompleteTextField() {
        super();
        elementi = new TreeSet<>();
        popup = new ContextMenu();

        textProperty().addListener((observableValue, s, t1) -> {
            if (getText().length() == 0) {
                popup.hide();
            } else {
                List<String> rezultati = new LinkedList<>();
                rezultati.addAll(elementi.subSet(getText(), getText() + Character.MAX_VALUE));
                if (elementi.size() > 0) {
                    popuniPopup(rezultati);
                    if (!popup.isShowing()) {
                        popup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    popup.hide();
                }
            }
        });

        focusedProperty().addListener((observableValue, aBoolean, aBoolean2) -> {
            if (getText() != null) {
                popup.hide();
            }
        });
    }

    private void popuniPopup(List<String> rezultati) {
        List<CustomMenuItem> elementi = new LinkedList<>();
        for (int i = 0; i < rezultati.size(); i++) {
            String rezultat = rezultati.get(i);
            Label lblElement = new Label(rezultat);
            CustomMenuItem element = new CustomMenuItem(lblElement, true);
            element.setOnAction(actionEvent -> {
                setText(rezultat);
                popup.hide();
            });
            elementi.add(element);
        }
        popup.getItems().clear();
        popup.getItems().addAll(elementi);
    }

    public SortedSet<String> getElementi() {
        return elementi;
    }
}
