package Billboard.Client;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import Billboard.POJOs.Advert;
import Billboard.POJOs.Category;
import Billboard.POJOs.Phone;
import Billboard.POJOs.User;

public class ConsoleApplication {

  public static void main(String[] args) {
    ConsoleApplication app = new ConsoleApplication();
    app.populateDatabase();
    app.displayDatabase();
  }

  public void populateDatabase() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    User dave = new User("dminter", "london");
    session.save(dave);

    dave.addPhone("Home", "0208 000 000");
    dave.addPhone("Work", "0207 000 000");
    dave.addPhone("Mobile", "07973 000 000");

    User jeff = new User("jlinwood", "austin");
    session.save(jeff);

    jeff.addPhone("Cell", "555 000 001");
    jeff.addPhone("Home", "555 000 002");
    jeff.addPhone("Work", "555 000 003");

    Category computing = new Category("Computing");
    session.save(computing);

    computing.addAdvert("Sinclair Spectrum for Sale", "48k, original box and packaging.", dave);
    computing.addAdvert("IBM PC for sale", "Original, not clone. 640Kb.", dave);
    computing.addAdvert("Apple II for sale", "Complete with paddles. Call after 5pm", dave);

    computing.addAdvert("Atari 2600 wanted", "Will pay up to $10", jeff);
    computing.addAdvert("Timex 2000 for sale", "Some games, $30", jeff);
    computing.addAdvert("Laptop", "Unwanted gift from disgruntled author. Otherwise good condition", jeff);

    Category instruments = new Category("Instruments");
    session.save(instruments);

    instruments.addAdvert("Elderly baby Grand Piano for sale", "Overstrung. Badly out of tune.", dave);
    instruments.addAdvert("Trombone for sale", "Slide missing. £1 + £30 p&p", dave);
    instruments.addAdvert("Marimba wanted", "Will offer up to £100", dave);

    instruments.addAdvert("Piccolo", "Tarnished but good sound.", jeff);
    instruments.addAdvert("Slightly used triangle", "Not quite triangular anymore. $1", jeff);
    instruments.addAdvert("Timpani set", "$30 total, call for postage", jeff);

    transaction.commit();
  }

  // The following was derived from the book "Hibernate Made Easy" by Cameron McKenzie.
  // Specifically, HibernateUtil's getSession and getInitializedConfiguration methods.
  private static SessionFactory factory;

  private static Session getSession() {
    if (factory == null) {
      Configuration config = new AnnotationConfiguration();

      // add all of your JPA annotated classes here!!!
      config.addAnnotatedClass(User.class);
      config.addAnnotatedClass(Phone.class);
      config.addAnnotatedClass(Advert.class);
      config.addAnnotatedClass(Category.class);

      config.configure();

      // generate the tables
      new SchemaExport(config).create(true, true);
      factory = config.buildSessionFactory();
    }

    return factory.getCurrentSession();
  }

  // The following was derived from the source code for the book "Pro Hibernate 3" by Dave Minter and Jeff Linwood.
  // Specifically, the CategoryDAO's createUser method, which is located in the Chapter03 folder.
  public List<Category> getAllCategories() {
    Query query = getSession().createQuery("from Category");
    return query.list();
  }

  public void displayDatabase() {
    Transaction transaction = getSession().beginTransaction();

    for (Category category : getAllCategories()) {
      System.out.println("========");
      System.out.println("Category: " + category.getTitle());

      for (Advert advert : category.getAdverts()) {
        System.out.println("  " + advert.getTitle());
        System.out.println("  " + advert.getMessage());

        User user = advert.getUser();
        System.out.println(" Contact: " + user.getName());

        for (Phone phone : getPhone(user)) {
          System.out.println(" " + phone.getComment() + ": " + phone.getNumber());
        }

        System.out.println("--------");
      }
    }

    transaction.commit();
  }

  // The following was derived from the source code for the book "Pro Hibernate 3" by Dave Minter and Jeff Linwood.
  // Specifically, the PhoneDAO's getPhone method, which is located in the Chapter03 folder.
  public List<Phone> getPhone(User user) {
    Query query = getSession().createQuery("from Phone phone where phone.user = :user");
    query.setEntity("user", user);
    return query.list();
  }
}
