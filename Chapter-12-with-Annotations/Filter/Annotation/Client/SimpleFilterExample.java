package Filter.Annotation.Client;

import java.util.List;

import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import Filter.Annotation.POJOs.AUser;

public class SimpleFilterExample {
  public static void main(String args[]) {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // insert the users
    insertUser("ray", true, session);
    insertUser("jason", true, session);
    insertUser("beth", false, session);
    insertUser("judy", false, session);
    insertUser("rob", false, session);

    // Show all users
    System.out.println("===ALL USERS===");
    displayUsers(session);

    // Show activated users
    Filter filter = session.enableFilter("myFilter");
    filter.setParameter("activatedParam", new Boolean(true));
    System.out.println("===ACTIVATED USERS===");
    displayUsers(session);

    // Show non-activated users
    filter.setParameter("activatedParam", new Boolean(false));
    System.out.println("===NON-ACTIVATED USERS===");
    displayUsers(session);

    //session.close();
    transaction.commit();
  }

  public static void displayUsers(Session session) {
    Query query = session.createQuery("from AUser");

    List<AUser> users = (List<AUser>) query.list();
    for(AUser user : users) {
      System.out.print(user.getUsername() + " is ");

      if (user.isActivated()) {
        System.out.println("activated.");
      }
      else {
        System.out.println("not activated.");
      }
    }
  }

  public static void insertUser(String name, boolean activated, Session session) {
    AUser user = new AUser();
    user.setUsername(name);
    user.setActivated(activated);
    session.save(user);
  }

  // The following was derived from the book "Hibernate Made Easy" by Cameron McKenzie.
  // Specifically, HibernateUtil's getSession and getInitializedConfiguration methods.
  private static SessionFactory factory;

  private static Session getSession() {
    if (factory == null) {
      Configuration config = new AnnotationConfiguration();

      // add all of your JPA annotated classes here!!!
      config.addAnnotatedClass(AUser.class);

      config.configure();

      // generate the tables
      new SchemaExport(config).create(true, true);
      factory = config.buildSessionFactory();
    }

    return factory.getCurrentSession();
  }
}
