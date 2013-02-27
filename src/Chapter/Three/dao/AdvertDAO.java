package Chapter.Three.dao;

import java.util.logging.Level;

import org.hibernate.HibernateException;

import Chapter.Three.AdException;
import Chapter.Three.pojo.Advert;
import Chapter.Three.pojo.User;

public class AdvertDAO extends DAO {
  public AdvertDAO() {
  }

  public Advert createAdvert(String title, String message, User user) throws AdException {
    try {
      Advert advert = new Advert();

      advert.setTitle(title);
      advert.setMessage(message);
      advert.setUser(user);

      HibernateHelper.getSession().save(advert);
      return advert;
    }
    catch (HibernateException e) {
      log.log(Level.SEVERE, "", e);
      throw new AdException("", e);
    }
  }

  public void deleteAdvert(Advert advert) throws AdException {
    try {
      HibernateHelper.getSession().update(advert); // Attach advert
      HibernateHelper.getSession().delete(advert); // Delete it from the database
    }
    catch (HibernateException e) {
      log.log(Level.SEVERE, "", e);
      throw new AdException("", e);
    }
  }
}
