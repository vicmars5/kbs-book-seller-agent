package bookseller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;
import java.lang.StringBuilder;

import java.io.IOException;

class BookSellerCsv {
  private static final String FILE_PATH = "/home/victor/school/books.csv";
  private static final int KEY_ID = 0;
  private static final int KEY_NAME = 1;
  private static final int KEY_PRICING = 2;
  private static final int KEY_STOCK = 3;

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
      int stock = Integer.parseInt(fields[BookSellerCsv.KEY_STOCK]);
      
      Book book = new Book();
      book.setId(id);
      book.setName(name);
      book.setPricing(pricing);
      book.setStock(stock);
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

  private void writeFile (String content) throws IOException {
    Files.write(
      Paths.get(BookSellerCsv.FILE_PATH),
      content.getBytes(),
      StandardOpenOption.TRUNCATE_EXISTING
    );
  }

  public void discountFromStock(String name) {
    try {
      Book[] books = this.getBooks();
      for (Book book : books) {
        if (book.getName().equals(name)) {
          book.setStock(book.getStock() - 1);
        }
      }
    
      StringBuilder csvContentBuilder = new StringBuilder();
      for (int i = 0; i < books.length; i++) {
        Book book = books[i];
        csvContentBuilder.append(
          "" +
          book.getId() + "," +
          book.getName() + "," +
          book.getPricing() + "," +
          book.getStock()
        );
        if (i != (books.length - 1)) {
          csvContentBuilder.append("\n");
        }
      }
      this.writeFile(csvContentBuilder.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
