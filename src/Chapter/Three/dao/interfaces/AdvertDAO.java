package Chapter.Three.dao.interfaces;

import Chapter.Three.Cameron_Mckenzie.GenericDAO;
import Chapter.Three.pojo.Advert;
import Chapter.Three.pojo.User;

public interface AdvertDAO extends GenericDAO<Advert, Long> {
  public Advert createAdvert(String title, String message, User user);
}
