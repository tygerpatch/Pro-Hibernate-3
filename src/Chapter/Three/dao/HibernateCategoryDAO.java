package Chapter.Three.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import Chapter.Three.Cameron_Mckenzie.HibernateDAO;
import Chapter.Three.Cameron_Mckenzie.HibernateUtil;
import Chapter.Three.dao.interfaces.CategoryDAO;
import Chapter.Three.pojo.Category;

public class HibernateCategoryDAO extends HibernateDAO<Category, Long> implements CategoryDAO {

  public HibernateCategoryDAO() {
    super(Category.class);
  }

  public List<Category> getAllCategories() {
    Session session = HibernateUtil.getSession();
    Query query = session.createQuery("FROM Category");
    List<Category> list = query.list();
    return list;
  }

  public Category createCategory(String title) {
    Category category = new Category();
    category.setTitle(title);
    this.save(category);
    return category;
  }
}
