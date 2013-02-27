package Chapter.Three.dao.interfaces;

import java.util.List;

import Chapter.Three.Cameron_Mckenzie.GenericDAO;
import Chapter.Three.pojo.Phone;
import Chapter.Three.pojo.User;

public interface PhoneDAO extends GenericDAO<Phone, Long> {
  public Phone createPhone(String comment, String number, User user);
  public List<Phone> getPhone(User user);
}
