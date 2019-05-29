package bookseller;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import bookseller.Book;

public class BookSellerPostgres {

  private static final String DB_HOST = "jdbc:postgresql://127.0.0.1:5432/boook_seller";
  private static final String DB_USER = "postgres";
  private static final String DB_PASSWORD = "postgres";
  private static final String DB_TABLE_BOOKS = "books";
  private Connection connection = null;

  public BookSellerPostgres () {
	  System.out.println("Book seller postgres!");
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
			e.printStackTrace();
			return;
    }

    Connection connection = null;
    try {
      connection = DriverManager.getConnection(
        BookSellerPostgres.DB_HOST,
        BookSellerPostgres.DB_USER,
        BookSellerPostgres.DB_PASSWORD
      );
    } catch (SQLException e) {
			System.out.println("Connection failed");
      e.printStackTrace();
      return;
    }

    if (connection != null) {
      this.connection = connection;
    } else {
      this.connection = null;
    }
  }

  public boolean isConnected() {
    return this.connection != null;
  }

  public Book[] getBooks() {
    List<Book> books = new ArrayList<Book>();
    try {
      Statement statement = this.connection.createStatement();
      ResultSet booksResultSet = statement.executeQuery("SELECT * FROM books");

      while (booksResultSet.next()) {
        Book book = new Book();
        book.setId(booksResultSet.getInt(1));
        book.setName(booksResultSet.getString(2));
        book.setPricing(booksResultSet.getDouble(3));
        book.setStock(booksResultSet.getInt(4));
        books.add(book);
      }

      return books.toArray(new Book[books.size()]);
    } catch (SQLException e) {
      e.printStackTrace();
      return new Book[0];
    }
  }

  public void updateBookByName (String name, int stock) {
    try {
      PreparedStatement statement = this.connection.prepareStatement("UPDATE books SET stock=? WHERE name=?");
      statement.setInt(1, stock);
      statement.setString(2, name);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
