package gui.komponente;

import dto.LetoviInfoDto;
import model.FlightOperator;
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
	
	public AccordionRange(String name, LetoviInfoDto letoviInfoDto) {
		super();
		this.setText(name);
		filter = new CheckBox("Koristi filter");
		filter.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				range.setDisable(!newValue);
			}
		});

		setRange(name, letoviInfoDto);

		range.setShowTickLabels(true);
		range.setShowTickMarks(true);
		range.setBlockIncrement(1f);
		range.setMajorTickUnit(10f);
		range.setDisable(true);
		box = new VBox(filter, range);
		this.setContent(box);
	}

	public void setRange(String name, LetoviInfoDto letoviInfoDto){
		if (name.equals("Trajanje")) {
			range = new RangeSlider(letoviInfoDto.getMinDuzina(), letoviInfoDto.getMaxDuzina(), letoviInfoDto.getMinDuzina(), letoviInfoDto.getMaxDuzina());
		} else if (name.equals("Cena")) {
			range = new RangeSlider(letoviInfoDto.getMinCena().intValueExact(), letoviInfoDto.getMaxCena().intValueExact(), letoviInfoDto.getMinCena().intValueExact(), letoviInfoDto.getMaxCena().intValueExact());
		} else {
			range = new RangeSlider(letoviInfoDto.getMinDaljina(), letoviInfoDto.getMaxDaljina(), letoviInfoDto.getMinDaljina(), letoviInfoDto.getMaxDaljina());
		}
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
