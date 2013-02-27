package Chapter.Seven.pojo;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private List products = new ArrayList();

  public List getProducts() {
    return products;
  }

  public void setProducts(List products) {
    this.products = products;
  }

  public boolean addProduct(Product product) {
    return products.add(product);
  }
}
