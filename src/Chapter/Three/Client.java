package Chapter.Three;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import Chapter.Three.Cameron_Mckenzie.HibernateUtil;
import Chapter.Three.dao.HibernateAdvertDAO;
import Chapter.Three.dao.HibernateCategoryDAO;
import Chapter.Three.dao.HibernatePhoneDAO;
import Chapter.Three.dao.HibernateUserDAO;
import Chapter.Three.dao.interfaces.AdvertDAO;
import Chapter.Three.dao.interfaces.CategoryDAO;
import Chapter.Three.dao.interfaces.PhoneDAO;
import Chapter.Three.dao.interfaces.UserDAO;
import Chapter.Three.pojo.Advert;
import Chapter.Three.pojo.Category;
import Chapter.Three.pojo.Phone;
import Chapter.Three.pojo.User;

public class Client {
  public static void main(String[] args) {
    // *** Populate Database
    Session session = HibernateUtil.getSession();
    Transaction transaction = session.beginTransaction();

    UserDAO userDAO = new HibernateUserDAO();

    User dave = userDAO.createUser("dminter", "london");
    User jeff = userDAO.createUser("jlinwood", "austin");

    PhoneDAO phoneDAO = new HibernatePhoneDAO();
    phoneDAO.createPhone("Mobile", "07973 000 000", dave);
    phoneDAO.createPhone("Home", "0208 000 000", dave);
    phoneDAO.createPhone("Work", "0207 000 000", dave);
    phoneDAO.createPhone("Cell", "555 000 001", jeff);
    phoneDAO.createPhone("Home", "555 000 002", jeff);
    phoneDAO.createPhone("Work", "555 000 003", jeff);

    AdvertDAO advertDAO = new HibernateAdvertDAO();

    Set<Advert> adverts = new HashSet<Advert>();

    adverts.add(advertDAO.createAdvert("Sinclair Spectrum for Sale", "48k, original box and packaging.", dave));
    adverts.add(advertDAO.createAdvert("IBM PC for sale", "Original, not clone. 640Kb.", dave));
    adverts.add(advertDAO.createAdvert("Apple II for sale", "Complete with paddles. Call after 5pm", dave));
    adverts.add(advertDAO.createAdvert("Atari 2600 wanted", "Will pay up to $10", jeff));
    adverts.add(advertDAO.createAdvert("Timex 2000 for sale", "Some games, $30", jeff));
    adverts.add(advertDAO.createAdvert("Laptop","Unwanted gift from disgruntled author. Otherwise good condition",jeff));

    CategoryDAO categoryDAO = new HibernateCategoryDAO();

    Category category = categoryDAO.createCategory("Computing");
    category.setAdverts(adverts);

    categoryDAO.save(category);

    adverts = new HashSet<Advert>();

    adverts.add(advertDAO.createAdvert("Elderly baby Grand Piano for sale", "Overstrung. Badly out of tune.", dave));
    adverts.add(advertDAO.createAdvert("Trombone for sale", "Slide missing. £1 + £30 p&p", dave));
    adverts.add(advertDAO.createAdvert("Marimba wanted", "Will offer up to £100", dave));
    adverts.add(advertDAO.createAdvert("Piccolo", "Tarnished but good sound.", jeff));
    adverts.add(advertDAO.createAdvert("Slightly used triangle","Not quite triangular anymore. $1", jeff));
    adverts.add(advertDAO.createAdvert("Timpani set", "$30 total, call for postage", jeff));

    category = categoryDAO.createCategory("Instruments");
    category.setAdverts(adverts);

    categoryDAO.save(category);

    // *** Display Database
    for(Category _category : categoryDAO.getAllCategories()){
      System.out.println("========");
      System.out.println("Category: " + _category.getTitle());

      for(Advert _advert : _category.getAdverts()) {
        System.out.println("  " + _advert.getTitle());
        System.out.println("  " + _advert.getMessage());

        User _user = _advert.getUser();
        System.out.println(" Contact: " + _user.getName());

        for(Phone _phone : phoneDAO.getPhone(_user)){
          System.out.println(" " + _phone.getComment() + ": " + _phone.getNumber());
        }

        System.out.println("--------");
      }
    }
  }
}
