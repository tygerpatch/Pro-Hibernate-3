package Chapter.One;

import java.io.Serializable;

// Title: Pro Hibernate 3
// Authors: Dave Minter, Jeff Linwood
// pages 4, 5
public class MessageOfTheDay implements Serializable {
  protected MessageOfTheDay() {
  }

  public MessageOfTheDay(int id, String message) {
    this.id = id;
    this.message = message;
  }

  public int getId() {
    return id;
  }

  public String getMessage() {
    return message;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  private int id;
  private String message;
}
