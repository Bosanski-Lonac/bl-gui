package wrapper;

import java.util.List;

import dto.KartaDto;

public class KartaPageWrapper {
	private List<KartaDto> content;
	private Integer totalPages;
	
	public List<KartaDto> getContent() {
		return content;
	}
	public void setContent(List<KartaDto> content) {
		this.content = content;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
}
