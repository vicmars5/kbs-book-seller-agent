package bookseller;

import org.json.JSONObject;
import org.json.JSONArray;

public class BookSellerJson {
  private static final String JSON_EXAMPLE = "{\"books\":[{\"id\":1,\"name\":\"El Conde de Monte-Cristo\",\"pricing\":100},{\"id\":2,\"name\":\"Game of Thrones\",\"pricing\":100},{\"id\":3,\"name\":\"Los Tres Mosqueteros\",\"pricing\":130},{\"id\":4,\"name\":\"La Biblia\",\"pricing\":300}]}";

  public void getBooks() {
		JSONObject object = new JSONObject(BookSellerJson.JSON_EXAMPLE);
		JSONArray booksArray = object.getJSONArray("books");
		for (int i = 0; i < booksArray.length(); i++) {
			JSONObject bookJson = booksArray.getJSONObject(i);
			Book book = new Book();
			book.setId(bookJson.getInt("id"));
			book.setName(bookJson.getString("name"));
			book.setPricing(bookJson.getDouble("pricing"));
			System.out.println(book.toString());
		}
  }
}
