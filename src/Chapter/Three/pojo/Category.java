package Chapter.Three.pojo;

import java.util.HashSet;
import java.util.Set;

//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 3: Building a Simple Application
//Page 42
public class Category {

   public Category(String title) {
      this.title = title;
      this.adverts = new HashSet();
   }

   Category() {
   }

   private Set adverts;

   public Set getAdverts() {
      return adverts;
   }

   public void setAdverts(Set adverts) {
      this.adverts = adverts;
   }

   private String title;

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   private long id;

   protected long getId() {
      return id;
   }

   protected void setId(long id) {
      this.id = id;
   }
}
