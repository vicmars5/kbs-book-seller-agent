package bookseller;

import bookseller.BookSellerPostgres;
import bookseller.BookSellerJson;
import bookseller.Book;

public class Main {
  public static void main (String args[]) {
    System.out.println("Get postgres books");
    Main.getPostgresData();

    System.out.println("Get JSON books");
    Main.getJsonData();
  }

  private static void getPostgresData() {
    BookSellerPostgres bookSellerPostgres = new BookSellerPostgres();

    if (!bookSellerPostgres.isConnected()) {
      System.out.println("Sorry we couldn't connected to the database.");
      return;
    }
    
    Book[] books = bookSellerPostgres.getBooks();
    for (Book book : books) {
      System.out.println(book.toString());
    }
  }

  private static void getJsonData () {
    BookSellerJson bookSellerJson = new BookSellerJson();
    Book[] books = bookSellerJson.getBooks();
		for (Book book : books) {
      System.out.println(book.toString());
		}
  }
}
