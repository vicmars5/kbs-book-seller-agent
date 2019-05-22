package io.github.vicmars5.BookSellerPostgres;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class BookSellerPostgres {
  public static void main (String args[]) {
    System.out.println("Hello world");

    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
			System.out.println(
          "Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!"
      );
			e.printStackTrace();
			return;
    }
  }
}
