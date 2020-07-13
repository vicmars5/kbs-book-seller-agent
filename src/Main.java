package bookseller;

import bookseller.BookSellerPostgres;
import bookseller.BookSellerJson; import bookseller.BookSellerCsv;
import bookseller.Book;

import java.io.IOException;

public class Main {
  private static BookSellerPostgres bookSellerPostgres = null;
  private static BookSellerJson bookSellerJson = null;
  private static BookSellerCsv bookSellerCsv = null;

  public static void main (String args[]) {
    
    Main.bookSellerJson = new BookSellerJson();
    Main.bookSellerPostgres = new BookSellerPostgres();

    System.out.println("Get postgres books");
    Main.getPostgresData();
    //System.out.println("Update postgres books");
    //Main.updatePostgresData();
    //System.out.println("Updated.");

    System.out.println("Get JSON books");
    Main.getJsonData();
    //System.out.println("Update JSON books");
    //Main.updateJsonData();
    //System.out.println("Updated.");

    System.out.println("Get CSV books");
    Main.getCsvData();
    //System.out.prntln("Update CSV books");
    //Main.updateCsvData();
    //System.out.println("Updated.");
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
    Main.bookSellerPostgres.discountFromStock("La Biblia");
  }

  private static void getJsonData () {
    try {
      Book[] books = Main.bookSellerJson.getBooks();
      for (Book book : books) {
        System.out.println(book.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void updateJsonData () {
    Main.bookSellerJson.discountFromStock("La Biblia");
  }

  private static void getCsvData() {
    Main.bookSellerCsv = new BookSellerCsv();
    try {
      Book[] books = bookSellerCsv.getBooks();
      for (Book book : books) {
        System.out.println(book.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private static void updateCsvData () {
    Main.bookSellerCsv.discountFromStock("La Biblia");
  }
}
