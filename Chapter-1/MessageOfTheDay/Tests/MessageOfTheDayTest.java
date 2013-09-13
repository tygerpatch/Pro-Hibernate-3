package MessageOfTheDay.Tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import MessageOfTheDay.POJOs.MessageOfTheDay;

public class MessageOfTheDayTest {

  private static Configuration configuration;

  @BeforeClass
  public static void beforeClass() {
    configuration = new AnnotationConfiguration();
    configuration.addAnnotatedClass(MessageOfTheDay.class);
    configuration.configure();

    configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/pro-hibernate-3-tests");
  }

  @Before
  public void before() {
    // -- recreate the MessageOfTheDay table
    boolean script = true;
    boolean export = true;
    new SchemaExport(configuration).create(script, export);
  }

  @Test
  public void testMessage() {
    String expected = "Hello World";

    // -- Populate MessageOfTheDay table with test data
    SessionFactory sessionFactory = configuration.buildSessionFactory();
    Session session = sessionFactory.getCurrentSession();

    Transaction transaction = session.beginTransaction();
    MessageOfTheDay messageOfTheDay = new MessageOfTheDay();
    messageOfTheDay.setMessage(expected);

    session.saveOrUpdate(messageOfTheDay);
    transaction.commit();

    // -- nullify MessageOfTheDay so that we're certain we retrieved a value from the database
    messageOfTheDay = null;

    // -- Get the MessageOfTheDay object that has the expected id
    session = sessionFactory.getCurrentSession();
    transaction = session.beginTransaction();
    String strQuery = "FROM MessageOfTheDay where message = :message";
    Query query = session.createQuery(strQuery);
    query.setString("message", expected);
    messageOfTheDay = (MessageOfTheDay) query.uniqueResult();
    transaction.commit();

    // -- Test that MessageOfTheDay object has the expected id
    String actual = messageOfTheDay.getMessage();
    assertThat(actual, is(equalTo(expected)));
  }
}
