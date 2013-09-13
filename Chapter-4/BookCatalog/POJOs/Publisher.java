package BookCatalog.POJOs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 4 : Using Annotations with Hibernate
//Page 73

@Entity
public class Publisher {
  private List<Book> books = new ArrayList<Book>();

  @OneToMany(
    mappedBy = "publisher",
    targetEntity = Book.class,
    cascade = CascadeType.ALL)
  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

  public boolean addBook(Book book) {
    book.setPublisher(this); // TODO: eliminate this statement
    return books.add(book);
  }

  protected String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  protected int id;

  @Id
  @GeneratedValue
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
