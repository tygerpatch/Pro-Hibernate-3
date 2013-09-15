package Criteria.POJOs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Supplier {

  public Supplier() {
  }

  public Supplier(String name) {
    this.name = name;
  }

  private int id;

  @Id
  @GeneratedValue
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

  private List<Product> products = new ArrayList<Product>();

  @OneToMany(mappedBy = "supplier", targetEntity = Product.class, cascade = CascadeType.ALL)
  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public boolean addProduct(Product product) {
    // product.setSupplier(this);
    return products.add(product);
  }
}
