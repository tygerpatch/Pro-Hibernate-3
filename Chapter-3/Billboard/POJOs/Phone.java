package Billboard.POJOs;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

// Title: Pro Hibernate 3
// Authors: Dave Minter, Jeff Linwood
// Chapter 3: Building a Simple Application

@Entity
public class Phone {

  private String comment, number;
  private long id;
  private User user;

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  @ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
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

  public Phone() {
  }

  public Phone(String comment, String number) {
    this.comment = comment;
    this.number = number;
  }
}
