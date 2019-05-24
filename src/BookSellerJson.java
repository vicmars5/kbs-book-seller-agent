package bookseller;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

public class BookSellerJson {
  private static final String JSON_EXAMPLE = "{\"books\":[{\"id\":1,\"name\":\"El Conde de Monte-Cristo\",\"pricing\":100},{\"id\":2,\"name\":\"Game of Thrones\",\"pricing\":100},{\"id\":3,\"name\":\"Los Tres Mosqueteros\",\"pricing\":130},{\"id\":4,\"name\":\"La Biblia\",\"pricing\":300}]}";
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
}
