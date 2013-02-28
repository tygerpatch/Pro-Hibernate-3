package Chapter.One;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Plain_Old_Java_Objects.MessageOfTheDay;

//Title: Pro Hibernate 3
//Authors: Dave Minter, Jeff Linwood
//Chapter 1 : An Introduction to Hibernate 3

/**
 * A class which can display a message of the day from the database -
 * illustrates the volume of boilerplate required for correct JDBC usage.
 */
public class JDBCMessageOfTheDay {

  public static void main(String[] args) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    
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


    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_jdbc", "root", "password");

      preparedStatement = connection.prepareStatement("SELECT message FROM MessageOfTheDay WHERE id = ?");
      preparedStatement.setInt(1, 42); // messageId

      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        MessageOfTheDay messageOfTheDay = new MessageOfTheDay();
        messageOfTheDay.setMessage(resultSet.getString(1));
        messageOfTheDay.setId(42);

        System.out.println(messageOfTheDay.getMessage());
      }
    }
    catch (SQLException sql) {
      sql.printStackTrace();
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
