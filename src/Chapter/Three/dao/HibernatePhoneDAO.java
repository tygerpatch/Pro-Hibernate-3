package Chapter.Three.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import Chapter.Three.Cameron_Mckenzie.HibernateDAO;
import Chapter.Three.Cameron_Mckenzie.HibernateUtil;
import Chapter.Three.dao.interfaces.PhoneDAO;
import Chapter.Three.pojo.Category;
import Chapter.Three.pojo.Phone;
import Chapter.Three.pojo.User;

public class HibernatePhoneDAO extends HibernateDAO<Phone, Long> implements PhoneDAO {

  public HibernatePhoneDAO() {
    super(Phone.class);
  }

  public List<Phone> getPhone(User user) {
    Session session = HibernateUtil.getSession();
    Query query = session.createQuery("FROM Phone p WHERE p.user= :user");
    query.setEntity("user", user);
    List<Phone> list = query.list();
    return list;
  }

  public Phone createPhone(String comment, String number, User user) {
    Phone phone = new Phone();

    phone.setUser(user);
    phone.setNumber(number);
    phone.setComment(comment);

    this.save(phone);
    return phone;
  }
}
