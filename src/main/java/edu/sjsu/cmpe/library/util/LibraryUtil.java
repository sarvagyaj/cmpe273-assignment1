package edu.sjsu.cmpe.library.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.sjsu.cmpe.library.dao.LibraryDAO;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.response.BookResponse;

public class LibraryUtil {

	public static BookResponse getBookResponse(Book book) {
		BookResponse bookResponse = new BookResponse();
		bookResponse.setIsbn(book.getIsbn());
		bookResponse.setTitle(book.getTitle());
		bookResponse.setPublication_date(book.getPublication_date());
		bookResponse.setLanguage(book.getLanguage());
		bookResponse.setNum_pages(book.getNum_pages());
		bookResponse.setStatus(book.getStatus());

		if (LibraryDAO.getReviewMap().containsKey(book.getIsbn())) {
			List<LinkDto> reviewLinks = new ArrayList<LinkDto>();
			for (int i = 0; i < LibraryDAO.getReviewMap().get(book.getIsbn())
					.size(); i++) {
				reviewLinks.add(LibraryDAO.getReviewMap().get(book.getIsbn())
						.get(i).getLinks().get(0));
			}
			bookResponse.setReviews(reviewLinks);
		} else {
			bookResponse.setReviews(new ArrayList<LinkDto>());
		}
		

		List<LinkDto> authorLinks = new ArrayList<LinkDto>();
		Iterator<AuthorDto> authorIterator = LibraryDAO.getAuthorMap()
				.get(book.getIsbn()).iterator();
		while (authorIterator.hasNext()) {
			authorLinks.add(authorIterator.next().getLinks().get(0));
		}
		bookResponse.setAuthors(authorLinks);

		return bookResponse;

	}
}
