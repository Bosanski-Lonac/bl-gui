package gui.komponente;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class AutoCompleteTextField extends TextField {
    private SortedSet<String> stavke;
    private ContextMenu popup;

    public AutoCompleteTextField() {
        super();
        stavke = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        popup = new ContextMenu();

        textProperty().addListener((observableValue, s, t1) -> {
            if (getText().length() == 0) {
                popup.hide();
            } else {
                if (stavke.size() > 0) {
                    List<String> filtriraneStavke = stavke.stream()
                    .filter(destinacija -> destinacija.toLowerCase().startsWith(getText().toLowerCase()))
                    .collect(Collectors.toList());
                    popuniPopup(filtriraneStavke);
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

    private void popuniPopup(Collection<String> rezultati) {
        List<CustomMenuItem> grafickeStavke = new LinkedList<>();
        for(String stavka : rezultati) {
            Label lblStavka = new Label(stavka);
            CustomMenuItem grafickaStavka = new CustomMenuItem(lblStavka, true);
            grafickaStavka.setOnAction(actionEvent -> {
                setText(stavka);
                popup.hide();
            });
            grafickeStavke.add(grafickaStavka);
        }
        popup.getItems().clear();
        popup.getItems().addAll(grafickeStavke);
    }

    public void setStavke(SortedSet<String> stavke) {
        this.stavke = stavke;
    }
}
