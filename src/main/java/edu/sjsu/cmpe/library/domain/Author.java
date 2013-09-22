package edu.sjsu.cmpe.library.domain;


public class Author {
	private long authorID;
	private String name;

	public Author() {
	}

	public Author(long key, String name, long isbn) {
		this.authorID = key;
		this.name = name;

	}

	public long getAuthorID() {
		return authorID;
	}

	public void setAuthorID(long key) {
		this.authorID = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
