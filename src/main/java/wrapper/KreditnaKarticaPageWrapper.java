package wrapper;

import java.util.List;

import dto.KreditnaKarticaDto;

public class KreditnaKarticaPageWrapper {
	private List<KreditnaKarticaDto> content;
	private Integer totalPages;

	public List<KreditnaKarticaDto> getContent() {
		return content;
	}

	public void setContent(List<KreditnaKarticaDto> content) {
		this.content = content;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
}
