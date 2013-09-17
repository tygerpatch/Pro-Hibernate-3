package Filter.Annotation.POJOs;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity
@FilterDef(
    name = "myFilter",
    parameters = @ParamDef(name = "activatedParam", type = "boolean"))
public class AUser {
  private int id;
  protected String username;
  private boolean activated;

  public boolean isActivated() {
    return activated;
  }

  public void setActivated(boolean activated) {
    this.activated = activated;
  }

  @Id
  @GeneratedValue
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
