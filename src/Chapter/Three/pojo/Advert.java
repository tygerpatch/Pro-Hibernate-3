package Chapter.Three.pojo;

//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 3: Building a Simple Application
//Page 43 & 44
public class Advert {

   public Advert(String title, String message, User user) {
      this.title = title;
      this.message = message;
      this.user = user;
   }

   public Advert() {
   }

   private String message;

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   private String title;

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   private User user;

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   private long id;

   protected long getId() {
      return id;
   }

   protected void setId(long id) {
      this.id = id;
   }
}
