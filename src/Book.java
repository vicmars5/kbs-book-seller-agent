package bookseller;

public class Book {
  private int id;
  private String name;
  private double pricing;

  public void setId (int id) {
    this.id = id;
  }

  public void setName (String name) {
    this.name = name;
  }

  public void setPricing (double pricing) {
    this.pricing = pricing;
  }

  public int getId () {
    return this.id;
  }

  public String getName () {
    return this.name;
  }

  public double getPricing () {
    return this.pricing;
  }

  public String toString() {
    return String.valueOf(this.id) + " - " 
      + this.name + " - "
      + String.valueOf(this.pricing);
  }
}
