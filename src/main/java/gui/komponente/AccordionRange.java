package gui.komponente;

import org.controlsfx.control.RangeSlider;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class AccordionRange extends TitledPane {
	private CheckBox filter;
	private RangeSlider range;
	private VBox box;
	
	public AccordionRange(String name) {
		super();
		this.setText(name);
		filter = new CheckBox("Koristi filter");
		filter.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				range.setDisable(!newValue);
			}
		});
		range = new RangeSlider(0, 500, 120, 420);
		range.setShowTickLabels(true);
		range.setShowTickMarks(true);
		range.setBlockIncrement(1f);
		range.setMajorTickUnit(10f);
		range.setDisable(true);
		box = new VBox(filter, range);
		this.setContent(box);
	}
	
	public boolean isSelected() {
		return filter.isSelected();
	}
	
	public int getLow() {
		return (int)range.getLowValue();
	}
	
	public int getHigh() {
		return (int)range.getHighValue();
	}
}
