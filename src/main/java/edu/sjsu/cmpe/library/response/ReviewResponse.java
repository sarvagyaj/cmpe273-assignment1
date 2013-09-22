package edu.sjsu.cmpe.library.response;

import java.util.List;

import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.LinksDto;

public class ReviewResponse {
	private List<Review> reviews;
	private LinksDto links;
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	public LinksDto getLinks() {
		return links;
	}
	public void setLinks(LinksDto links) {
		this.links = links;
	}
	
	
}
