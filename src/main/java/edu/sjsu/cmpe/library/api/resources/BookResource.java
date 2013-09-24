package edu.sjsu.cmpe.library.api.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.validator.constraints.NotEmpty;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.dao.LibraryDAO;
import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.response.AuthorResponse;
import edu.sjsu.cmpe.library.response.BookResponse;
import edu.sjsu.cmpe.library.response.GetBook;
import edu.sjsu.cmpe.library.response.ReviewResponse;
import edu.sjsu.cmpe.library.util.LibraryUtil;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
	private AtomicLong isbnCounter;

	public BookResource() {
		isbnCounter = new AtomicLong();
	}

	@POST
	public Response createBook(@Valid Book requestBook) {
		if (requestBook == null) {
			String message = "Book provided in payload should not be null";
			return Response.status(403).entity(message).build();
		}
		long isbn = isbnCounter.incrementAndGet();
		requestBook.setIsbn(isbn);

		List<AuthorDto> authorDtoList = new ArrayList<AuthorDto>();
		for (int authorCounter = 0; authorCounter < requestBook.getAuthors()
				.size(); authorCounter++) {
			int authorID = authorCounter + 1;

			Author author = new Author();
			author.setAuthorID(authorID);
			author.setName(requestBook.getAuthors().get(authorCounter)
					.getName());

			AuthorDto authorDto = new AuthorDto();
			authorDto.setAuthor(author);
			authorDto.addLink(new LinkDto("view-author", "/books/" + isbn
					+ "/authors/" + authorID, "GET"));
			authorDtoList.add(authorDto);

		}
		LibraryDAO.getAuthorMap().put(isbn, authorDtoList);

		BookDto bookDto = new BookDto(requestBook);
		bookDto.addLink(new LinkDto("view-book", "/books/"
				+ requestBook.getIsbn(), "GET"));
		bookDto.addLink(new LinkDto("update-book", "/books/"
				+ requestBook.getIsbn(), "PUT"));
		bookDto.addLink(new LinkDto("delete-book", "/books/"
				+ requestBook.getIsbn(), "DELETE"));
		bookDto.addLink(new LinkDto("create-review", "/books/"
				+ requestBook.getIsbn(), "POST"));
		LibraryDAO.getBookMap().put(isbn, bookDto);
		return Response.status(201).entity(bookDto.getLinks()).build();

	}

	@GET
	@Path("/{isbn}")
	@Timed(name = "view-book")
	public Response getBookByIsbn(@Valid @PathParam("isbn") LongParam isbn) {
		BookDto bookDto = LibraryDAO.getBookMap().get(isbn.get());
		if (bookDto == null) {
			String message = "Given isbn is not present in the system. Please enter valid isbn.";
			return Response.status(404).entity(message).build();
		}
		BookResponse newBook = LibraryUtil.getBookResponse(bookDto.getBook());
		GetBook getBook = new GetBook();
		getBook.setBook(newBook);
		getBook.setLinks(LibraryDAO.getBookMap().get(isbn.get()).getLinks());
		return Response.ok(getBook).build();

	}

	@DELETE
	@Path("/{isbn}")
	public Response deleteBookByIsbn(@Valid @PathParam("isbn") LongParam isbn) {
		if (!LibraryDAO.getBookMap().containsKey(isbn.get())) {
			String message = "Given isbn is not present in the system. Please enter valid isbn.";
			return Response.status(404).entity(message).build();
		}
		LibraryDAO.getBookMap().remove(isbn.get());
		LibraryDAO.getAuthorMap().remove(isbn.get());
		LibraryDAO.getReviewMap().remove(isbn.get());
		LinkDto link = new LinkDto("create-book", "/books", "POST");
		return Response.ok(link).build();

	}

	@PUT
	@Path("/{isbn}")
	public Response updateBookByIsbn(@Valid @PathParam("isbn") LongParam isbn,
			@Valid @NotEmpty @QueryParam("status") String status) {
		BookDto bookDto = LibraryDAO.getBookMap().get(isbn.get());
		if (bookDto == null) {
			String message = "No book exist with given ISBN.";
			return Response.status(404).entity(message).build();
		} else if (status.equalsIgnoreCase("available")
				|| status.equalsIgnoreCase("lost")
				|| status.equalsIgnoreCase("checked-out")
				|| status.equalsIgnoreCase("in-queue")) {
			bookDto.getBook().setStatus(status);
			return Response.ok(bookDto.getLinks()).build();
		}
		String message = "status can only be available|checked-out|in-queue|lost";
		return Response.status(404).entity(message).build();
	}

	@GET
	@Path("/{isbn}/authors/{id}")
	public Response getAuthorById(@Valid @PathParam("isbn") LongParam isbn,
			@Valid @PathParam("id") int authorID) {
		List<AuthorDto> authorDtoList = LibraryDAO.getAuthorMap().get(
				isbn.get());
		if (authorDtoList == null) {
			String message = "ISBN does not exist.";
			return Response.status(404).entity(message).build();
		} else if (authorID < 0 || authorID > authorDtoList.size()) {
			String message = "AuthorID does not exist.";
			return Response.status(404).entity(message).build();
		}
		AuthorDto authorDto = authorDtoList.get(authorID - 1);
		return Response.ok(authorDto).build();
	}

	@GET
	@Path("/{isbn}/authors")
	public Response getAllAuthorByIsbn(@Valid @PathParam("isbn") LongParam isbn) {
		AuthorResponse authorResponse = new AuthorResponse();
		List<Author> authorList = new ArrayList<Author>();
		List<AuthorDto> authorDtoList = LibraryDAO.getAuthorMap().get(
				isbn.get());
		if (authorDtoList == null) {
			String message = "ISBN does not exist";
			return Response.status(404).entity(message).build();
		}
		for (AuthorDto authorDto : authorDtoList) {
			Author author = authorDto.getAuthor();
			authorList.add(author);
		}
		authorResponse.setAuthors(authorList);
		authorResponse.setLinks(new LinksDto());
		return Response.ok(authorResponse).build();

	}

	@POST
	@Path("/{isbn}/reviews")
	public Response createBookReview(@Valid Review requestReview,
			@Valid @PathParam("isbn") LongParam isbn) {
		List<ReviewDto> reviewDtoList;
		ReviewDto reviewDto = new ReviewDto();
		int reviewID = 0;
		if (LibraryDAO.getReviewMap().containsKey(isbn.get())) {
			reviewID = LibraryDAO.getReviewMap().get(isbn.get()).size();
			reviewDtoList = LibraryDAO.getReviewMap().get(isbn.get());
		} else {
			reviewDtoList = new ArrayList<ReviewDto>();
			LibraryDAO
					.getBookMap()
					.get(isbn.get())
					.addLink(
							new LinkDto("view-all-reviews", "/books/"
									+ isbn.get() + "/reviews", "GET"));
		}
		reviewID = reviewID + 1;

		requestReview.setId(reviewID);
		reviewDto.setReview(requestReview);
		reviewDto.addLink(new LinkDto("view-review", "/books/" + isbn.get()
				+ "/reviews/" + reviewID, "GET"));
		reviewDtoList.add(reviewDto);
		LibraryDAO.getReviewMap().put(isbn.get(), reviewDtoList);

		return Response.status(201).entity(reviewDto.getLinks()).build();
	}

	@GET
	@Path("/{isbn}/reviews/{id}")
	public Response getReviewById(@Valid @PathParam("isbn") LongParam isbn,
			@Valid @PathParam("id") int reviewID) {
		List<ReviewDto> reviewDtoList = LibraryDAO.getReviewMap().get(
				isbn.get());
		if (!LibraryDAO.getBookMap().containsKey(isbn.get())) {
			String message = "Book does not exist.";
			return Response.status(404).entity(message).build();
		} else if (reviewDtoList == null) {
			String message = "There are no reviews for this book at this time.";
			return Response.status(404).entity(message).build();
		} else if (reviewID < 0 || reviewID > reviewDtoList.size()) {
			String message = "Review does not exist.";
			return Response.status(404).entity(message).build();
		}
		ReviewDto reviewDto = reviewDtoList.get(reviewID - 1);
		return Response.ok(reviewDto).build();

	}

	@GET
	@Path("/{isbn}/reviews")
	public Response getAllReviewByIsbn(@Valid @PathParam("isbn") LongParam isbn) {

		List<ReviewDto> reviewDtoList = LibraryDAO.getReviewMap().get(
				isbn.get());
		if (!LibraryDAO.getBookMap().containsKey(isbn.get())) {
			String message = "Book does not exist.";
			return Response.status(404).entity(message).build();
		} else if (reviewDtoList == null) {
			String message = "There are no reviews at this time for this book.";
			return Response.status(404).entity(message).build();
		}

		ReviewResponse reviewResponse = new ReviewResponse();
		List<Review> reviewList = new ArrayList<Review>();

		for (ReviewDto reviewDto : reviewDtoList) {
			Review review = reviewDto.getReview();
			reviewList.add(review);
		}

		reviewResponse.setReviews(reviewList);
		reviewResponse.setLinks(new LinksDto());
		return Response.ok(reviewResponse).build();

	}
}
