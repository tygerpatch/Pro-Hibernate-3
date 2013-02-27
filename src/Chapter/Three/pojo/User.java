package Chapter.Three.pojo;

//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 3: Building a Simple Application
//Page 40
public class User {

   public User(String name, String password) {
      this.name = name;
      this.password = password;
   }

   User() {
   }

   private String name;   

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   private String password;

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   private long id;   

   protected long getId() {
      return id;
   }

   protected void setId(long id) {
      this.id = id;
   }
}
