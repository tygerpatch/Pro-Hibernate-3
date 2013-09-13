package BookCatalog.POJOs;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 4 : Using Annotations with Hibernate
//Page 75 & 76

@Entity
public class ComputerBook extends Book {
  protected String softwareName;

  public String getSoftwareName() {
    return softwareName;
  }

  public void setSoftwareName(String softwareName) {
    this.softwareName = softwareName;
  }
}
