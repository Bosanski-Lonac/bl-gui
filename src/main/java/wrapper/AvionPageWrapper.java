package wrapper;

import java.util.List;

import dto.AvionDto;

public class AvionPageWrapper {
	private List<AvionDto> content;
	private Integer totalPages;

	public List<AvionDto> getContent() {
		return content;
	}

	public void setContent(List<AvionDto> content) {
		this.content = content;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
}
