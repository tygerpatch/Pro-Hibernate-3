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

// Title: Pro Hibernate 3
// Authors: Dave Minter, Jeff Linwood
// Chapter 3: Building a Simple Application

@Entity
public class Category {

  private String title;
  private long id;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Id
  @GeneratedValue
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  private List<Advert> adverts;

  @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
  @JoinTable(name = "categories_adverts", joinColumns = { @JoinColumn(name = "category_id") }, inverseJoinColumns = { @JoinColumn(name = "advert_id") })
  public List<Advert> getAdverts() {
    return adverts;
  }

  public void setAdverts(List<Advert> adverts) {
    this.adverts = adverts;
  }

  // convenience method
  public boolean addAdvert(Advert advert) {
    return adverts.add(advert);
  }
}
