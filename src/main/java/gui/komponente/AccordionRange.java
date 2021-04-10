package gui.komponente;

import dto.LetoviInfoDto;
import javafx.geometry.Insets;
import model.FlightOperator;
import org.controlsfx.control.RangeSlider;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class AccordionRange extends TitledPane {
	private RangeSlider range;

	public AccordionRange(String name, LetoviInfoDto letoviInfoDto) {
		super();
		this.setText(name);
		initRange(name, letoviInfoDto);

		range.setShowTickLabels(true);
		range.setShowTickMarks(true);
		range.setBlockIncrement(1f);
		range.setMajorTickUnit(10f);
		range.setDisable(false);
		range.setPadding(new Insets(0, 5, 0, 5));
		this.setContent(range);
	}

	public void initRange(String name, LetoviInfoDto letoviInfoDto){
		if (name.equals("Trajanje")) {
			range = new RangeSlider(letoviInfoDto.getMinDuzina(), letoviInfoDto.getMaxDuzina(), letoviInfoDto.getMinDuzina(), letoviInfoDto.getMaxDuzina());
		} else if (name.equals("Cena")) {
			range = new RangeSlider(letoviInfoDto.getMinCena().intValueExact(), letoviInfoDto.getMaxCena().intValueExact(), letoviInfoDto.getMinCena().intValueExact(), letoviInfoDto.getMaxCena().intValueExact());
		} else {
			range = new RangeSlider(letoviInfoDto.getMinDaljina(), letoviInfoDto.getMaxDaljina(), letoviInfoDto.getMinDaljina(), letoviInfoDto.getMaxDaljina());
		}
	}

	public void resetRange() {
		range.setLowValue(range.getMin());
		range.setHighValue(range.getMax());
	}
	
	public int getLow() {
		return (int)range.getLowValue();
	}
	
	public int getHigh() {
		return (int)range.getHighValue();
	}
}
