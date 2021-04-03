package wrapper;

import java.util.List;

import dto.LetDto;

public class LetPageWrapper {
	private List<LetDto> content;
	private Integer totalPages;

	public List<LetDto> getContent() {
		return content;
	}

	public void setContent(List<LetDto> content) {
		this.content = content;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
}
