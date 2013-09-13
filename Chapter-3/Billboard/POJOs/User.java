package Billboard.POJOs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

// Title: Pro Hibernate 3
// Authors: Dave Minter, Jeff Linwood
// Chapter 3: Building a Simple Application

@Entity
public class User {

  private String name, password;
  private long id;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Id
  @GeneratedValue
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User() {
  }

  public User(String name, String password) {
    this.name = name;
    this.password = password;
  }

  private List<Phone> phones = new ArrayList<Phone>();

  @OneToMany(
    mappedBy = "user",
    targetEntity = Phone.class,
    cascade = CascadeType.ALL)
  public List<Phone> getPhones( ) {
    return phones;
  }

  public void setPhones(List<Phone> phones) {
    this.phones = phones;
  }

  public boolean addPhone(String comment, String number) {
    Phone phone = new Phone(comment, number);
    phone.setUser(this); // TODO: eliminate this statement
    return addPhone(phone);
  }

  public boolean addPhone(Phone phone) {
    return phones.add(phone);
  }
}
