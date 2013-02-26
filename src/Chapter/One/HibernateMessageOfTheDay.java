package Chapter.One;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 1 : An Introduction to Hibernate 3
public class HibernateMessageOfTheDay {

  public static void main(String[] args) {
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = sessionFactory.openSession();
    MessageOfTheDay messageOfTheDay = (MessageOfTheDay) session.get(MessageOfTheDay.class, 42);

    if (messageOfTheDay != null) {
      System.out.println(messageOfTheDay.getMessage());
    }
    else {
      System.out.println("No such message");
    }

    session.close();
  }
}
