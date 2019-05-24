package bookseller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import java.io.IOException;

class BookSellerCsv {
  private static final String FILE_PATH = "/home/victor/school/books.csv";
  private static final int KEY_ID = 0;
  private static final int KEY_NAME = 1;
  private static final int KEY_PRICING = 2;

  public Book[] getBooks () throws IOException {
    String fileContent = this.readFile();
    String[] rows = fileContent.split("\n");

    Book[] books = new Book[rows.length];
    for (int i = 0; i < rows.length; i++) {
      String row = rows[i];
      String[] fields = row.split(",");
      
      int id = Integer.parseInt(fields[BookSellerCsv.KEY_ID]);
      String name = fields[BookSellerCsv.KEY_NAME];
      double pricing = Double.parseDouble(fields[BookSellerCsv.KEY_PRICING]);
      
      Book book = new Book();
      book.setId(id);
      book.setName(name);
      book.setPricing(pricing);
      books[i] = book;
    }

    return books;
  }

  // helpfull resource https://www.mkyong.com/java8/java-8-stream-read-a-file-line-by-line/
  public String readFile () throws IOException {
    Stream<String> stream = Files.lines(Paths.get(BookSellerCsv.FILE_PATH));
    String fileContent = stream.reduce("", (acumulator, l) -> acumulator + l + "\n");
    return fileContent;
  }
}
