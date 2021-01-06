package gui.komponente;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.IntegerStringConverter;

public class UnsignedIntegerField extends TextField {
	public UnsignedIntegerField() {
		super();
		
		UnaryOperator<Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            try {
                Integer.parseUnsignedInt(newText);
                return change;
            } catch (NumberFormatException e) {
            }
            return null;
        };

        this.setTextFormatter(
            new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
	}
}
