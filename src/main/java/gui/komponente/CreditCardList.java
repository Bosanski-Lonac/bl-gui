package gui.komponente;

import java.util.ArrayList;
import java.util.List;

import controller.ShowCCFormAction;
import dto.KreditnaKarticaDto;
import gui.SceneWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.UserOperator;
import wrapper.KreditnaKarticaPageWrapper;

public class CreditCardList extends BorderPane implements IRefreshable {
	private ToggleGroup toggleGroup;
	private VBox vbKartice;
	private Button dodajKarticu;
	private Pagination pagination;
	
	public CreditCardList(SceneWrapper scena) {
		super();
		toggleGroup = new ToggleGroup();
		vbKartice = new VBox(16);
		pagination = new Pagination();
		pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> setPage(newIndex.intValue()));
		dodajKarticu = new Button("Dodaj");
		dodajKarticu.setOnAction(new ShowCCFormAction(scena, this));
		VBox bottom = new VBox(16, dodajKarticu, pagination);
		bottom.setAlignment(Pos.CENTER);
		this.setCenter(vbKartice);
		this.setBottom(bottom);
		setPage(0);
	}
	
	@Override
	public void setPage(int page) {
		if(vbKartice.getChildren().size() < 2 && page == -2 && pagination.getCurrentPageIndex() > 1) {
			page = pagination.getCurrentPageIndex() - 1;
		} else if(page < 0) {
			page = pagination.getCurrentPageIndex();
		}
		toggleGroup.getToggles().clear();
		vbKartice.getChildren().clear();
		KreditnaKarticaPageWrapper wrapper = UserOperator.getInstance().displayCC(page);
		pagination.setPageCount(wrapper.getTotalPages());
		
		List<CreditCardDisplay> kartice = new ArrayList<>();
		for(KreditnaKarticaDto kreditnaKarticaDto : wrapper.getContent()) {
			CreditCardDisplay creditCardPrikaz = new CreditCardDisplay(this, kreditnaKarticaDto);
			kartice.add(creditCardPrikaz);
			toggleGroup.getToggles().add(creditCardPrikaz.getRadioButton());
		}
		vbKartice.getChildren().addAll(kartice);
	}
	
	public KreditnaKarticaDto getSelected() {
		if(toggleGroup.getSelectedToggle() == null) {
			return null;
		}
		return (KreditnaKarticaDto)toggleGroup.getSelectedToggle().getUserData();
	}
	
}
