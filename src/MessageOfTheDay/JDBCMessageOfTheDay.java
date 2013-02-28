package MessageOfTheDay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    Statement statement = null;

    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_jdbc", "root", "password");
      statement = connection.createStatement( );
      ResultSet resultSet = statement.executeQuery("SELECT message, id FROM MessageOfTheDay");

      if (resultSet.next()) {
        System.out.println(resultSet.getString("id") + ": " + resultSet.getString("message"));
      }
    }
    catch (SQLException sql) {
      sql.printStackTrace();
    }
    finally {
      if (statement != null) {
        try {
          statement.close();
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
