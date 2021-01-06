package gui.komponente;

import java.util.ArrayList;
import java.util.List;

import dto.KartaDto;
import dto.LetoviDto;
import javafx.geometry.Pos;
import javafx.scene.control.Pagination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.FlightOperator;
import model.TicketOperator;
import wrapper.KartaPageWrapper;

public class KartaList extends BorderPane implements IRefreshable {
	private VBox vbKarte;
	private Pagination pagination;
	
	public KartaList() {
		super();
		vbKarte = new VBox(16);
		pagination = new Pagination();
		pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> setPage(newIndex.intValue()));
		BorderPane.setAlignment(pagination, Pos.BOTTOM_CENTER);
		this.setCenter(vbKarte);
		this.setBottom(pagination);
		setPage(0);
	}
	
	@Override
	public void setPage(int page) {
		if(vbKarte.getChildren().size() < 2 && page == -2 && pagination.getCurrentPageIndex() > 1) {
			page = pagination.getCurrentPageIndex() - 1;
		} else if(page < 0) {
			page = pagination.getCurrentPageIndex();
		}
		vbKarte.getChildren().clear();
		KartaPageWrapper wrapper = TicketOperator.getInstance().displayKarte(page);
		pagination.setPageCount(wrapper.getTotalPages());
		LetoviDto letoviDto = FlightOperator.getInstance().getFlightsForIds(wrapper);
		
		List<KartaDisplay> karte = new ArrayList<>();
		for(KartaDto kartaDto : wrapper.getContent()) {
			KartaDisplay kartaPrikaz = new KartaDisplay(this, kartaDto, letoviDto.getLetoviDto().get(kartaDto.getLetId()));
			karte.add(kartaPrikaz);
		}
		vbKarte.getChildren().addAll(karte);
	}

}
