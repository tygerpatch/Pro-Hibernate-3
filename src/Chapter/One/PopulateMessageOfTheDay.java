package Chapter.One;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 1 : An Introduction to Hibernate 3

// Populates the HSQL Database with a message
public class PopulateMessageOfTheDay {

  public static void main(String[] args) {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    }
    catch (ClassNotFoundException classNotFound) {
      classNotFound.printStackTrace();
      return;
    }
    catch (IllegalAccessException illegalAccess) {
      illegalAccess.printStackTrace();
      return;
    }
    catch (InstantiationException instantiation) {
      instantiation.printStackTrace();
      return;
    }

    Connection connection = null;
    PreparedStatement preparedStatement = null;

    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_jdbc", "root", "password");
      preparedStatement = connection.prepareStatement("insert into MessageOfTheDay (id, message) values (?, ?)");

      preparedStatement.setInt(1, 42);                                  // messageId
      preparedStatement.setString(2, "This is the message of the day"); // message
      preparedStatement.executeUpdate();
    }
    catch (SQLException sql) {
      sql.printStackTrace();
      return;
    }
    finally {
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        }
        catch (SQLException sql) {
          sql.printStackTrace();
        }
      }

      if (connection != null) {
        try {
          connection.close();
        }
        catch (SQLException sql) {
          sql.printStackTrace();
          return;
        }
      }
    }
  }
}
