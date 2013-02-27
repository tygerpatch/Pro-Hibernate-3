package Chapter.Three.dao;

import Chapter.Three.Cameron_Mckenzie.HibernateDAO;
import Chapter.Three.dao.interfaces.AdvertDAO;
import Chapter.Three.pojo.Advert;
import Chapter.Three.pojo.User;

public class HibernateAdvertDAO extends HibernateDAO<Advert, Long> implements AdvertDAO {

  public HibernateAdvertDAO() {
    super(Advert.class);
  }

  public Advert createAdvert(String title, String message, User user) {
    Advert advert = new Advert();

    advert.setTitle(title);
    advert.setMessage(message);
    advert.setUser(user);

    this.save(advert);
    return advert;
  }
}
