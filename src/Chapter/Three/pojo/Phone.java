package Chapter.Three.pojo;

//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 3: Building a Simple Application
//Page 41 & 41
public class Phone {

   public Phone() {
   }

   private String comment;

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   private String number;

   public String getNumber() {
      return number;
   }

   public void setNumber(String number) {
      this.number = number;
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
