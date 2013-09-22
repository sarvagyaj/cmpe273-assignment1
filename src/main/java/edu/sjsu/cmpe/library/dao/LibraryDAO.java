package edu.sjsu.cmpe.library.dao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;

public class LibraryDAO {
	private static final Map<Long, BookDto> bookMap = new ConcurrentHashMap<Long, BookDto>();
	private static final Map<Long, List<AuthorDto>> authorMap = new ConcurrentHashMap<Long, List<AuthorDto>>();
	private final static Map<Long, List<ReviewDto>> reviewMap = new ConcurrentHashMap<Long, List<ReviewDto>>();

	public static Map<Long, BookDto> getBookMap() {
		return bookMap;
	}

	public static Map<Long, List<AuthorDto>> getAuthorMap() {
		return authorMap;
	}

	public static Map<Long, List<ReviewDto>> getReviewMap() {
		return reviewMap;
	}

}
