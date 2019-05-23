package bookseller;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class BookSellerJson {
  private static final String JSON_EXAMPLE = "{\"books\":[{\"id\":1,\"name\":\"El Conde de Monte-Cristo\",\"pricing\":100},{\"id\":2,\"name\":\"Game of Thrones\",\"pricing\":100},{\"id\":3,\"name\":\"Los Tres Mosqueteros\",\"pricing\":130},{\"id\":4,\"name\":\"La Biblia\",\"pricing\":300}]}";

  public Book[] getBooks() {
		List<Book> books = new ArrayList<Book>();
		JSONObject object = new JSONObject(BookSellerJson.JSON_EXAMPLE);
		JSONArray booksArray = object.getJSONArray("books");
	
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
}
