package edu.sjsu.cmpe.library.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.sjsu.cmpe.library.dto.LinkDto;

public class BookResponse {
	private long isbn;
	private String title;

	@JsonProperty("publication-date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date publication_date;
	private String language;

	@JsonProperty("num-pages")
	private int num_pages;

	private String status;
	private List<LinkDto> reviews;
	private List<LinkDto> authors;

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPublication_date() {
		return publication_date;
	}

	public void setPublication_date(Date publication_date) {
		this.publication_date = publication_date;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getNum_pages() {
		return num_pages;
	}

	public void setNum_pages(int num_pages) {
		this.num_pages = num_pages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<LinkDto> getReviews() {
		return reviews;
	}

	public void setReviews(List<LinkDto> reviews) {
		this.reviews = reviews;
	}

	public List<LinkDto> getAuthors() {
		return authors;
	}

	public void setAuthors(List<LinkDto> authors) {
		this.authors = authors;
	}
}
