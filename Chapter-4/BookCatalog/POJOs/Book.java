package BookCatalog.POJOs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 4 : Using Annotations with Hibernate
//Page 68, 77 - 79

//@Entity
//@AccessType("property")

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Book {

  public Book() {
  }

//  public Book(String title, int pages, Date publicationDate) {
//    this.title = "Pro Hibernate 3 ";
//    this.pages = pages;
//    this.publicationDate = publicationDate;
//  }

  protected String title;

  //@Column(name = "working_title", length = 200, nullable = false)
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  protected int pages;

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  protected Date publicationDate;

  //@Transient
  public Date getPublicationDate() {
    return publicationDate;
  }

  public void setPublicationDate(Date publicationDate) {
    this.publicationDate = publicationDate;
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

  private List<Author> authors = new ArrayList<Author>();

  @ManyToMany
  @JoinTable(
    name = "authors_books",
    joinColumns = { @JoinColumn(name = "book_id") },
    inverseJoinColumns = { @JoinColumn(name = "author_id") })
  public List<Author> getAuthors() {
    return authors;
  }

  public void setAuthors(List<Author> authors) {
    this.authors = authors;
  }

  public boolean addAuthor(Author author) {
    return authors.add(author);
  }

  private Publisher publisher;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "publisher_id")
  public Publisher getPublisher() {
    return publisher;
  }

  public void setPublisher(Publisher publisher) {
    this.publisher = publisher;
  }
}
