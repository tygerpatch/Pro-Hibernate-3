package Filter.Client;

import java.util.List;

import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import Filter.POJOs.FilterUser;
import Filter.Utils.HibernateHelper;

public class SimpleXMLFilterExample {
  public static void main(String args[]) {
    Session session = HibernateHelper.getSession();
    //Session session = getSession();

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
    Filter filter = session.enableFilter("activatedFilter");
    filter.setParameter("activatedParam", new Boolean(true));
    System.out.println("===ACTIVATED USERS===");
    displayUsers(session);

    // Show non-activated users
    filter.setParameter("activatedParam", new Boolean(false));
    System.out.println("===NON-ACTIVATED USERS===");
    displayUsers(session);

    session.close();
  }

  public static void displayUsers(Session session) {
    Transaction trans = session.beginTransaction();
    Query query = session.createQuery("from FilterUser");

    List<FilterUser> users = (List<FilterUser>) query.list();
    for(FilterUser user : users) {
      System.out.print(user.getUsername() + " is ");

      if (user.isActivated()) {
        System.out.println("activated.");
      }
      else {
        System.out.println("not activated.");
      }
    }

    trans.commit();

  }

  public static void insertUser(String name, boolean activated, Session session) {
    Transaction trans = session.beginTransaction();

    FilterUser user = new FilterUser();
    user.setUsername(name);
    user.setActivated(activated);
    session.save(user);

    trans.commit();
  }
}
