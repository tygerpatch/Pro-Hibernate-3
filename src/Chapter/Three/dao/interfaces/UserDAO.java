package Chapter.Three.dao.interfaces;

import Chapter.Three.Cameron_Mckenzie.GenericDAO;
import Chapter.Three.pojo.User;

public interface UserDAO extends GenericDAO<User, Long> {
  public User createUser(String username, String password);
}
