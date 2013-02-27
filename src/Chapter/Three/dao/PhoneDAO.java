package Chapter.Three.dao;

import java.util.List;
import java.util.logging.Level;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import Chapter.Three.AdException;
import Chapter.Three.pojo.Phone;
import Chapter.Three.pojo.User;

public class PhoneDAO extends DAO {
  public PhoneDAO() {
  }

  public List getPhone(User user) throws AdException {
    try {
      Session session = HibernateHelper.getSession();
      Query query = session.createQuery("from Phone p where p.user= :user");
      query.setEntity("user", user);
      List results = query.list();
      return results;
    }
    catch (HibernateException e) {
      log.log(Level.SEVERE, "", e);
      throw new AdException("", e);
    }
  }

  public Phone createPhone(String comment, String number, User user) throws AdException {
    try {
      Phone phone = new Phone();

      phone.setUser(user);
      phone.setNumber(number);
      phone.setComment(comment);

      HibernateHelper.getSession().save(phone);
      return phone;
    }
    catch (HibernateException e) {
      log.log(Level.SEVERE, "", e);
      throw new AdException("", e);
    }
  }

  public void deletePhone(Phone phone) throws AdException {
    try {
      HibernateHelper.getSession().update(phone);
      HibernateHelper.getSession().delete(phone);
    }
    catch (HibernateException e) {
      log.log(Level.SEVERE, "", e);
      throw new AdException("", e);
    }
  }
}
