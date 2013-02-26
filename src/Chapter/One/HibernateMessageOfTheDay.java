package Chapter.One;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 1 : An Introduction to Hibernate 3

public class HibernateMessageOfTheDay {

  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Nope, enter one message number");
    }
    else {
      try {
        int messageId = Integer.parseInt(args[0]);
        MessageOfTheDay motd = getMotd(messageId);
        if (motd != null) {
          System.out.println(motd.getMessage());
        }
        else {
          System.out.println("No such message");
        }
      }
      catch (NumberFormatException e) {
        System.err.println("You must enter an integer - " + args[0]
            + " won't do.");
      }
      catch (MessageOfTheDayException e) {
        System.err.println("Couldn't get the message: " + e);
      }
    }
  }

  public static MessageOfTheDay getMotd(int messageId)
      throws MessageOfTheDayException {
    SessionFactory sessions = new Configuration().configure()
        .buildSessionFactory();
    Session session = sessions.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();

      MessageOfTheDay motd = (MessageOfTheDay) session.get(
          MessageOfTheDay.class, new Integer(messageId));

      tx.commit();
      tx = null;
      return motd;
    }
    catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      log.log(Level.SEVERE, "Could not acquire message", e);
      throw new MessageOfTheDayException(
          "Failed to retrieve message from the database.", e);
    }
    finally {
      session.close();
    }
  }

  private static final Logger log = Logger.getAnonymousLogger();
}
