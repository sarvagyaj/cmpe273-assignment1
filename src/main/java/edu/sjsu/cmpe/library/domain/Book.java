package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {
	private long isbn;

	@NotEmpty(message = "title cannot be null or empty")
	@JsonProperty
	private String title;

	@JsonProperty("publication-date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	@NotNull(message = "publication-date cannot be empty")
	private Date publication_date;

	private String language;

	@JsonProperty("num-pages")
	@Min(0)
	private int num_pages = 0;

	@NotNull(message = "status cannot be null or empty if present")
	@Pattern(regexp = "available|checked-out|in-queue|lost")
	private String status = "available";

	@NotEmpty(message = "author list cannot be empty")
	@Valid
	private ArrayList<Author> authors;

	private ArrayList<Review> reviews;

	public Book() {
		super();
	}

	public Book(long isbn, String title, Date publication_date, String status) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.publication_date = publication_date;
		this.status = status;
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
