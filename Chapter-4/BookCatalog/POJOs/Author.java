package BookCatalog.POJOs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 4 : Using Annotations with Hibernate
//Page 79 & 80

@Entity
public class Author {

  public Author() {
  }

  public Author(String name) {
    this.name = name;
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

  private List<Book> books = new ArrayList<Book>();

  @ManyToMany
  @JoinTable(
    name = "authors_books",
    joinColumns = {@JoinColumn(name = "author_id")},
    inverseJoinColumns = {@JoinColumn(name = "book_id")})
  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

  // Convenience method, added by Todd Gerspacher
  public boolean addBook(Book book) {
    return books.add(book);
  }

//
//  protected String email;
//
//  public String getEmail() {
//    return email;
//  }
//
//  public void setEmail(String email) {
//    this.email = email;
//  }

}
