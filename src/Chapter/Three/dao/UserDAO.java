package Chapter.Three.dao;

import java.util.List;
import java.util.logging.Level;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import Chapter.Three.AdException;
import Chapter.Three.pojo.User;

public class UserDAO extends DAO {
  public UserDAO() {
  }

  public User getUser(String username) throws AdException {
    try {
      Session session = HibernateHelper.getSession();

      Query query = session.createQuery("from User u where u.name= :username");
      query.setString("username", username);
      List results = query.list();

      User user = null;
      if (results.size() == 1) {
        user = (User) results.get(0);
      }

      return user;
    }
    catch (HibernateException e) {
      log.log(Level.SEVERE, "", e);
      throw new AdException("", e);
    }
  }

  public User createUser(String username, String password) throws AdException {
    try {
      User user = new User();

      user.setName(username);
      user.setPassword(password);

      Session session = HibernateHelper.getSession();
      session.save(user);
      return user;
    }
    catch (HibernateException e) {
      log.log(Level.SEVERE, "", e);
      throw new AdException("", e);
    }
  }

  public void deleteUser(String username) throws AdException {
    try {
      User user = getUser(username);
      HibernateHelper.getSession().update(user);
      HibernateHelper.getSession().delete(user);
    }
    catch (HibernateException e) {
      log.log(Level.SEVERE, "", e);
      throw new AdException("", e);
    }
  }
}