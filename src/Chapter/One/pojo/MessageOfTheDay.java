package Chapter.One.pojo;

import java.io.Serializable;

// Title: Pro Hibernate 3
// Authors: Dave Minter, Jeff Linwood
// Chapter 1 : An Introduction to Hibernate 3
// pages 4, 5
public class MessageOfTheDay implements Serializable {

  public MessageOfTheDay() {
  }

  public MessageOfTheDay(int id, String message) {
    this.id = id;
    this.message = message;
  }

  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
