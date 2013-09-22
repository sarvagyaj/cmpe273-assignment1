package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {
	private long isbn;
	@NotEmpty
	private String title;

	@JsonProperty("publication-date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date publication_date;
	private String language;

	@JsonProperty("num-pages")
	private int num_pages;

	private String status;
	@NotEmpty
	private ArrayList<Author> authors;
	private ArrayList<Review> reviews;

	public Book() {
	}

	public Book(long isbn, String title, Date date, int num_pages, String status) {
		this.isbn = isbn;
		this.title = title;
		this.publication_date = date;
		this.num_pages = num_pages;
		this.status = status;
		// this.authors = new ArrayList<String>(authorsList);

	}

	/**
	 * @return the isbn
	 */
	public long getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn
	 *            the isbn to set
	 */
	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
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

	public ArrayList<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<Author> author) {
		this.authors = author;
	}

	public ArrayList<Review> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}
	
	
}
