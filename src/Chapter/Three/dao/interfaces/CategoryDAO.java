package Chapter.Three.dao.interfaces;

import java.util.List;

import Chapter.Three.Cameron_Mckenzie.GenericDAO;
import Chapter.Three.pojo.Category;

public interface CategoryDAO extends GenericDAO<Category, Long> {
  public Category createCategory(String title);
  public List<Category> getAllCategories();
}
