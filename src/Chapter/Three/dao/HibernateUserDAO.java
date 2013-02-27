package Chapter.Three.dao;

import Chapter.Three.Cameron_Mckenzie.HibernateDAO;
import Chapter.Three.dao.interfaces.UserDAO;
import Chapter.Three.pojo.User;

public class HibernateUserDAO extends HibernateDAO<User, Long> implements UserDAO {

  public HibernateUserDAO() {
    super(User.class);
  }

  public User createUser(String username, String password) {
    User user = new User();
    user.setName(username);
    user.setPassword(password);
    this.save(user);
    return user;
  }
}
