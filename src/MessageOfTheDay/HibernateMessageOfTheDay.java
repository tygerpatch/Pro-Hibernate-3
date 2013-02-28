package MessageOfTheDay;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import Plain_Old_Java_Objects.MessageOfTheDay;

//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 1 : An Introduction to Hibernate 3
public class HibernateMessageOfTheDay {

  public static void main(String[] args) {
    // Configure Hibernate so it can handle the annotated MessageOfTheDay JavaBean
    // (page 66, "Hibernate Made Easy", Cameron McKenzie)
    AnnotationConfiguration config = new AnnotationConfiguration();
    config.addAnnotatedClass(MessageOfTheDay.class);
    config.configure();

    SessionFactory sessionFactory = config.buildSessionFactory();
    Session session = sessionFactory.getCurrentSession();
    // Note: SessionFactory#openSession creates a Session object

    Transaction transaction = session.beginTransaction();
    MessageOfTheDay messageOfTheDay = (MessageOfTheDay) session.get(MessageOfTheDay.class, 42);
    transaction.commit();

    if (messageOfTheDay != null) {
      System.out.println(messageOfTheDay.getId() + ": " + messageOfTheDay.getMessage());
    }
    else {
      System.out.println("No such message");
    }

    // session.close();
    // throws org.hibernate.SessionException: Session was already closed
  }
}
