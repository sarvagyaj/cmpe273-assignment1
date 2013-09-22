package edu.sjsu.cmpe.library.response;

import java.util.List;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.dto.LinksDto;

public class AuthorResponse {
	private List<Author> authors;
	private LinksDto links;
	public List<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	public LinksDto getLinks() {
		return links;
	}
	public void setLinks(LinksDto links) {
		this.links = links;
	}
	
	
}
