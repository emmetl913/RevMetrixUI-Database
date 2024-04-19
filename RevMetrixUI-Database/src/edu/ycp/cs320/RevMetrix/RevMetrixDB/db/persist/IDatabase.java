package edu.ycp.cs320.RevMetrix.RevMetrixDB.db.persist;



public interface IDatabase {
	/*
	 * public List<Pair<Author, Book>> findAuthorAndBookByTitle(String title);
	 * public List<Pair<Author, Book>> findAuthorAndBookByAuthorLastName(String
	 * lastName); public Integer insertBookIntoBooksTable(String title, String isbn,
	 * int published, String lastName, String firstName); public List<Pair<Author,
	 * Book>> findAllBooksWithAuthors(); public List<Author> findAllAuthors();
	 * public List<Author> removeBookByTitle(String title);
	 */		
	
	public Integer insertNewAccountinDB(String email, String password, String username);

	public Integer insertNewBallInDB(int account_id, float weight, String name, Boolean righthand, String brand, String color);
}
