package bookseller;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class BookSellerJson {
  private static final String FILE_PATH = "/home/victor/school/books.json";

  public Book[] getBooks () throws IOException {
    String fileContent = this.readFile();
		JSONObject object = new JSONObject(fileContent);

		JSONArray booksArray = object.getJSONArray("books");
	
		List<Book> books = new ArrayList<Book>();
		for (int i = 0; i < booksArray.length(); i++) {
			JSONObject bookJson = booksArray.getJSONObject(i);
			Book book = new Book();
			book.setId(bookJson.getInt("id"));
			book.setName(bookJson.getString("name"));
			book.setPricing(bookJson.getDouble("pricing"));
			book.setStock(bookJson.getInt("stock"));
			books.add(book);
		}
		return books.toArray(new Book[books.size()]);
  }

  // helpfull resource https://www.mkyong.com/java8/java-8-stream-read-a-file-line-by-line/
  public String readFile () throws IOException {
    Stream<String> stream = Files.lines(Paths.get(BookSellerJson.FILE_PATH));
    String fileContent = stream.reduce("", (acumulator, l) -> acumulator + l + "\n");
    return fileContent;
  }

  private void writeFile (String content) throws IOException {
    Files.write(
      Paths.get(BookSellerJson.FILE_PATH),
      content.getBytes(),
      StandardOpenOption.TRUNCATE_EXISTING
    );
  }

  public void discountFromStock(String name) {
    try {
      String fileContent = this.readFile();
      JSONObject object = new JSONObject(fileContent);
		  JSONArray books = object.getJSONArray("books");
      
      for (int i = 0; i < books.length(); i++) {
        JSONObject book = books.getJSONObject(i);
        if (book.getString("name").equals(name)) {
          System.out.println("Book found");
          int stock = book.getInt("stock");
          book.put("stock", stock - 1);
          books.put(i, book);
        }
      }

      object.put("books", books);
      this.writeFile(object.toString(2));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
