package Billboard.Client;

import java.util.ArrayList;
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
    Transaction transaction = getSession().beginTransaction();

    User dave = createUser("dminter", "london");
    User jeff = createUser("jlinwood", "austin");

    createPhone("Mobile", "07973 000 000", dave);
    createPhone("Home", "0208 000 000", dave);
    createPhone("Work", "0207 000 000", dave);
    createPhone("Cell", "555 000 001", jeff);
    createPhone("Home", "555 000 002", jeff);
    createPhone("Work", "555 000 003", jeff);

    List<Advert> adverts = new ArrayList<Advert>();

    adverts.add(createAdvert("Sinclair Spectrum for Sale", "48k, original box and packaging.", dave));
    adverts.add(createAdvert("IBM PC for sale", "Original, not clone. 640Kb.", dave));
    adverts.add(createAdvert("Apple II for sale", "Complete with paddles. Call after 5pm", dave));
    adverts.add(createAdvert("Atari 2600 wanted", "Will pay up to $10", jeff));
    adverts.add(createAdvert("Timex 2000 for sale", "Some games, $30", jeff));
    adverts.add(createAdvert("Laptop", "Unwanted gift from disgruntled author. Otherwise good condition", jeff));

    Category category = createCategory("Computing");
    // TODO: category.addAdvert(createAdvert("Sinclair Spectrum for Sale", "48k, original box and packaging.", dave));
    category.setAdverts(adverts);

    adverts = new ArrayList<Advert>();

    adverts.add(createAdvert("Elderly baby Grand Piano for sale", "Overstrung. Badly out of tune.", dave));
    adverts.add(createAdvert("Trombone for sale", "Slide missing. £1 + £30 p&p", dave));
    adverts.add(createAdvert("Marimba wanted", "Will offer up to £100", dave));
    adverts.add(createAdvert("Piccolo", "Tarnished but good sound.", jeff));
    adverts.add(createAdvert("Slightly used triangle", "Not quite triangular anymore. $1", jeff));
    adverts.add(createAdvert("Timpani set", "$30 total, call for postage", jeff));

    category = createCategory("Instruments");
    category.setAdverts(adverts);

    transaction.commit();
  }

  // The following was derived from the book "Hibernate Made Easy" by Cameron McKenzie.
  // Specifically, HibernateUtil's getSession and getInitializedConfiguration methods.
  private static SessionFactory factory;

  public static Session getSession() {
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
  // Specifically, the UserDAO's createUser method, which is located in the Chapter03 folder.
  public User createUser(String userName, String password) {
    // TODO: rename createUser to just create and move it into the User class; User.create("dminter", "london")
    User user = new User();
    user.setName(userName);
    user.setPassword(password);

    Session session = getSession();
    session.save(user);
    return user;
  }

  // The following was derived from the source code for the book "Pro Hibernate 3" by Dave Minter and Jeff Linwood.
  // Specifically, the PhoneDAO's createUser method, which is located in the Chapter03 folder.
  public Phone createPhone(String comment, String number, User user) {
    // TODO: rename createPhone to just create and move it into the Phone class; Phone.create("dminter", "london")
    // TODO: give user class an addPhoneNumber(String number, String comment) method; dave.addPhoneNumber("07973 000 000", "Mobile")
    Phone phone = new Phone();
    phone.setUser(user);
    phone.setNumber(number);
    phone.setComment(comment);

    Session session = getSession();
    session.save(phone);
    return phone;
  }

  // The following was derived from the source code for the book "Pro Hibernate 3" by Dave Minter and Jeff Linwood.
  // Specifically, the AdvertDAO's createUser method, which is located in the Chapter03 folder.
  public Advert createAdvert(String title, String message, User user) {
    // TODO: rename createAdvert to just create and move it into the Advert class
    // TODO: give user class an addAdvert(String title, String message) method;
    // ex. jeff.addAdvert("Laptop", "Unwanted gift from disgruntled author.  Otherwise good condition.")
    Advert advert = new Advert();
    advert.setTitle(title);
    advert.setMessage(message);
    advert.setUser(user);

    Session session = getSession();
    session.save(advert);
    return advert;
  }

  // The following was derived from the source code for the book "Pro Hibernate 3" by Dave Minter and Jeff Linwood.
  // Specifically, the CategoryDAO's createUser method, which is located in the Chapter03 folder.
  public Category createCategory(String title) {
    // TODO: rename createCategory to just create and move it into the Category class
    Category category = new Category();
    category.setTitle(title);

    Session session = getSession();
    session.save(category);
    return category;
  }

  // The following was derived from the source code for the book "Pro Hibernate 3" by Dave Minter and Jeff Linwood.
  // Specifically, the CategoryDAO's createUser method, which is located in the Chapter03 folder.
  public List<Category> getAllCategories() {
    Session session = getSession();
    Transaction transaction = getSession().beginTransaction();
    Query query = session.createQuery("from Category");
    List<Category> list = query.list();
    transaction.commit();
    return list;
  }

  public void displayDatabase() {
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
  }

  // The following was derived from the source code for the book "Pro Hibernate 3" by Dave Minter and Jeff Linwood.
  // Specifically, the PhoneDAO's getPhone method, which is located in the Chapter03 folder.
  public List<Phone> getPhone(User user) {
    // TODO: this should be in the User class; jeff.getPhones()
    Session session = getSession();
    Transaction transaction = getSession().beginTransaction();
    Query query = session.createQuery("from Phone phone where phone.user = :user");
    query.setEntity("user", user);
    List<Phone> list = query.list();
    transaction.commit();
    return list;
  }
}
