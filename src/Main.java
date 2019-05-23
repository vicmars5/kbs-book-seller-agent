package bookseller;

import bookseller.BookSellerPostgres;
import bookseller.Book;

public class Main {
  public static void main (String args[]) {
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
}
