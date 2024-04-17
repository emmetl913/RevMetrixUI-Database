package db.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import db.persist.ReadCSV;
import edu.ycp.cs320.RevMetrix.model.Account;

public class InitialData {

	// reads initial Author data from CSV file and returns a List of Authors
	public static List<Account> getAccounts() throws IOException
	{
		List<Account> accountList = new ArrayList<Account>();
		ReadCSV readAccounts = new ReadCSV("account.csv");
		try
		{
			Integer accountId = 1;
			while(true)
			{
				List<String> tuple = readAccounts.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();

				// read author ID from CSV file, but don't use it
				// it's there for reference purposes, just make sure that it is correct
				// when setting up the BookAuthors CSV file				
				Integer.parseInt(i.next());
				// auto-generate author ID, instead
				int temp1 = accountId++;
				String temp2 = i.next();
				String temp3 = i.next();
				String temp4 = i.next();
				Account account = new Account(temp2, temp3, temp4);
				
				accountList.add(account);
			}
			return accountList;
		}finally {
			readAccounts.close();
		}
	}
	
	/*
	 * public static List<Author> getAuthors() throws IOException { List<Author>
	 * authorList = new ArrayList<Author>(); ReadCSV readAuthors = new
	 * ReadCSV("authors.csv"); try { // auto-generated primary key for authors table
	 * Integer authorId = 1; while (true) { List<String> tuple = readAuthors.next();
	 * if (tuple == null) { break; } Iterator<String> i = tuple.iterator(); Author
	 * author = new Author();
	 * 
	 * // read author ID from CSV file, but don't use it // it's there for reference
	 * purposes, just make sure that it is correct // when setting up the
	 * BookAuthors CSV file Integer.parseInt(i.next()); // auto-generate author ID,
	 * instead author.setAuthorId(authorId++); author.setLastname(i.next());
	 * author.setFirstname(i.next()); authorList.add(author); }
	 * System.out.println("authorList loaded from CSV file"); return authorList; }
	 * finally { readAuthors.close(); } }
	 */
	// reads initial Book data from CSV file and returns a List of Books
	/*
	 * public static List<Book> getBooks() throws IOException { List<Book> bookList
	 * = new ArrayList<Book>(); ReadCSV readBooks = new ReadCSV("books.csv"); try {
	 * // auto-generated primary key for table books Integer bookId = 1; while
	 * (true) { List<String> tuple = readBooks.next(); if (tuple == null) { break; }
	 * Iterator<String> i = tuple.iterator(); Book book = new Book();
	 * 
	 * // read book ID from CSV file, but don't use it // it's there for reference
	 * purposes, just make sure that it is correct // when setting up the
	 * BookAuthors CSV file Integer.parseInt(i.next()); // auto-generate book ID,
	 * instead book.setBookId(bookId++); //
	 * book.setAuthorId(Integer.parseInt(i.next())); // no longer in books table
	 * book.setTitle(i.next()); book.setIsbn(i.next());
	 * book.setPublished(Integer.parseInt(i.next()));
	 * 
	 * bookList.add(book); } System.out.println("bookList loaded from CSV file");
	 * return bookList; } finally { readBooks.close(); } }
	 */
	// reads initial BookAuthor data from CSV file and returns a List of BookAuthors
	/*
	 * public static List<BookAuthor> getBookAuthors() throws IOException {
	 * List<BookAuthor> bookAuthorList = new ArrayList<BookAuthor>(); ReadCSV
	 * readBookAuthors = new ReadCSV("book_authors.csv"); try { while (true) {
	 * List<String> tuple = readBookAuthors.next(); if (tuple == null) { break; }
	 * Iterator<String> i = tuple.iterator(); BookAuthor bookAuthor = new
	 * BookAuthor(); bookAuthor.setBookId(Integer.parseInt(i.next()));
	 * bookAuthor.setAuthorId(Integer.parseInt(i.next()));
	 * bookAuthorList.add(bookAuthor); }
	 * System.out.println("bookAuthorList loaded from CSV file"); return
	 * bookAuthorList; } finally { readBookAuthors.close(); } }
	 */
}