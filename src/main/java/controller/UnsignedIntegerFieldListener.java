package controller;

import gui.UnsignedIntegerField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class UnsignedIntegerFieldListener implements ChangeListener<String> {

	private UnsignedIntegerField field;
	
	public UnsignedIntegerFieldListener(UnsignedIntegerField field) {
		this.field = field;
	}
	
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
        	Integer.parseUnsignedInt(newValue);
        } catch (NumberFormatException e) {
            field.setText(oldValue);
        }
	}

}
