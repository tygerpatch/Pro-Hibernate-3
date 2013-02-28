package Chapter.Three.Cameron_Mckenzie;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import Chapter.Four.pojo.Author;
import Chapter.Four.pojo.Book;
import Chapter.Four.pojo.ComputerBook;
import Chapter.Four.pojo.Publisher;
import Chapter.Seven.pojo.Product;
import Chapter.Seven.pojo.Software;
import Chapter.Seven.pojo.Supplier;
import Chapter.Three.pojo.Advert;
import Chapter.Three.pojo.Category;
import Chapter.Three.pojo.Phone;
import Chapter.Three.pojo.User;
import Plain_Old_Java_Objects.MessageOfTheDay;

// Title: Hibernate Made Easy
// Author: Cameron McKenzie

// Page: 126 start of HibernateUtil
// Page: 126 getInitializedConfiguration method
// Page: 127 update getInitializedConfiguration method with reference to future classes
// Page: 128 recreateDatabase method
// Page: 128 static void main method
// Page: 129 getSession method
// Page: 131 beginTransaction method
// Page: 131 commitTransaction method
// Page: 132 closeSession method
// Page: 133 rollbackTransaction
// Page: 268 uncommented line adding Snafu to AnnotationConfiguration object
// Page: 279 uncommented line adding FooBar to AnnotationConfiguration object
// Page: 288 uncommented line adding Thing to AnnotationConfiguration object
// Page: 300 added Interest class to AnnotationConfiguration object
// Page: 303 added Fracture class to AnnotationConfiguration object
// Page: 305 added Prison class to AnnotationConfiguration object
// Page: 360 added LeftManyStudent and RightManyCourse classes to AnnotationConfiguration object
// Page: 384 added the following classes to AnnotationConfiguration object : Client, ClientDetail, Address, Skill

// Configured to work pojos from book "Pro Hibernate 3"

public class HibernateUtil {

  public static Configuration getInitializedConfiguration() {
    AnnotationConfiguration config = new AnnotationConfiguration();

    // add all of your JPA annotated classes here!!!
    config.addAnnotatedClass(User.class);

    // Future classes we will be creating.
    // Keep them commented out for now.

    // Chapter 1
    config.addAnnotatedClass(MessageOfTheDay.class);

    // Chapter 3
    config.addAnnotatedClass(Advert.class);
    config.addAnnotatedClass(Category.class);
    config.addAnnotatedClass(Phone.class);
    config.addAnnotatedClass(User.class);

    // Chapter 4
    config.addAnnotatedClass(Author.class);
    config.addAnnotatedClass(Book.class);
    config.addAnnotatedClass(ComputerBook.class);
    config.addAnnotatedClass(Publisher.class);

    // Chapter 7
    config.addAnnotatedClass(Product.class);
    config.addAnnotatedClass(Software.class);
    config.addAnnotatedClass(Supplier.class);

    config.configure();
    return config;
  }

  public static void recreateDatabase() {
    Configuration config = HibernateUtil.getInitializedConfiguration();
    new SchemaExport(config).create(true, true);
  }

  public static void main(String[] args) {
    HibernateUtil.recreateDatabase();
  }

  private static SessionFactory factory;

  public static Session getSession() {
    if (factory == null) {
      Configuration config = HibernateUtil.getInitializedConfiguration();
      factory = config.buildSessionFactory();
    }

    Session hibernateSession = factory.getCurrentSession();
    return hibernateSession;
  }

  public static Session beginTransaction() {
    Session hibernateSession = HibernateUtil.getSession();
    hibernateSession.beginTransaction();
    return hibernateSession;
  }

  public static void commitTransaction() {
    HibernateUtil.getSession().getTransaction().commit();
  }

  public static void closeSession() {
    HibernateUtil.getSession().close();
  }

  public static void rollbackTransaction() {
    HibernateUtil.getSession().getTransaction().rollback();
  }
}
