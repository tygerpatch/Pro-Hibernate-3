package Chapter.Three.pojo;

import java.util.Set;

//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 3: Building a Simple Application
//Page 42
public class Category {

   public Category() {
   }

   private Set<Advert> adverts;

   public Set<Advert> getAdverts() {
      return adverts;
   }

   public void setAdverts(Set<Advert> adverts) {
      this.adverts = adverts;
   }

   public boolean addAdvert(Advert advert) {
     return adverts.add(advert);
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
