package edu.sjsu.cmpe.library.response;

import java.util.List;

import edu.sjsu.cmpe.library.dto.LinkDto;

public class GetBook {
	private BookResponse book;
	private List<LinkDto> links;

	public BookResponse getBook() {
		return book;
	}

	public void setBook(BookResponse book) {
		this.book = book;
	}

	public List<LinkDto> getLinks() {
		return links;
	}

	public void setLinks(List<LinkDto> links) {
		this.links = links;
	}

}
