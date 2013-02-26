package Chapter.One;

//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 1 : An Introduction to Hibernate 3

public class MessageOfTheDayException extends Exception {

  public MessageOfTheDayException(String message) {
    super(message);
  }

  public MessageOfTheDayException(String message, Throwable cause) {
    super(message, cause);
  }
}
