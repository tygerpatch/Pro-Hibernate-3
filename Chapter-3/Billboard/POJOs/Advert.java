package Billboard.POJOs;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

// Title: Pro Hibernate 3
// Authors: Dave Minter, Jeff Linwood
// Chapter 3: Building a Simple Application

@Entity
public class Advert {
  private String message, title;
  private User user;
  private long id;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @ManyToOne
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Id
  @GeneratedValue
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  private List<Category> categories;

  @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
  @JoinTable(name = "categories_adverts", joinColumns = { @JoinColumn(name = "advert_id") }, inverseJoinColumns = { @JoinColumn(name = "category_id") })
  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public Advert() {
  }

  public Advert(String title, String message, User user) {
    this.title = title;
    this.message = message;
    this.user = user;
  }
}
