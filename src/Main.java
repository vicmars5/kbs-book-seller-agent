package bookseller;

import bookseller.BookSellerPostgres;
import bookseller.BookSellerJson;
import bookseller.BookSellerCsv;
import bookseller.Book;

import java.io.IOException;

public class Main {
  private static BookSellerPostgres bookSellerPostgres = null;

  public static void main (String args[]) {
    
    Main.bookSellerPostgres = new BookSellerPostgres();

    System.out.println("Get postgres books");
    Main.getPostgresData();
    System.out.println("Update postgres books");
    Main.updatePostgresData();
    System.out.println("Updated.");

    System.out.println("Get JSON books");
    Main.getJsonData();

    System.out.println("Get CSV books");
    Main.getCsvData();
  }

  private static void getPostgresData() {
    if (!Main.bookSellerPostgres.isConnected()) {
      System.out.println("Sorry we couldn't connected to the database.");
      return;
    }
    
    Book[] books = Main.bookSellerPostgres.getBooks();
    for (Book book : books) {
      System.out.println(book.toString());
    }
  }

  private static void updatePostgresData() {
    Main.bookSellerPostgres.updateBookByName("La Biblia", 10);
  }

  private static void getJsonData () {
    BookSellerJson bookSellerJson = new BookSellerJson();
    try {
      Book[] books = bookSellerJson.getBooks();
      for (Book book : books) {
        System.out.println(book.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getCsvData() {
    BookSellerCsv bookSellerCsv = new BookSellerCsv();
    try {
      Book[] books = bookSellerCsv.getBooks();
      for (Book book : books) {
        System.out.println(book.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
