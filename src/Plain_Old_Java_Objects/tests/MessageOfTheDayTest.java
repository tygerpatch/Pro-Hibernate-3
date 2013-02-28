package Plain_Old_Java_Objects.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import Plain_Old_Java_Objects.MessageOfTheDay;

public class MessageOfTheDayTest {

  // TODO: test setting and getting the id
  // TODO: test setting and getting the message

  @Test
  public void testMessage() {
    String expected = "Hello World";

    // -- recreate the MessageOfTheDay table
    AnnotationConfiguration config = new AnnotationConfiguration();
    config.addAnnotatedClass(MessageOfTheDay.class);
    config.configure();

    boolean script = true;
    boolean export = true;
    new SchemaExport(config).create(script, export);

    // -- Populate MessageOfTheDay table with test data
    SessionFactory sessionFactory = config.buildSessionFactory();
    Session session = sessionFactory.getCurrentSession();

    Transaction transaction = session.beginTransaction();
    MessageOfTheDay messageOfTheDay = new MessageOfTheDay();
    messageOfTheDay.setMessage(expected);
    session.saveOrUpdate(messageOfTheDay);
    transaction.commit();

    // -- nullify MessageOfTheDay so were certain were using the database
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

// *** previous attemmpts at finding a unique result
// --
//transaction = session.beginTransaction();
//Criteria criteria = session.createCriteria(MessageOfTheDay.class);
//criteria.add(Restrictions.eqProperty("message", expected));
//criteria.setMaxResults(1);
//messageOfTheDay = (MessageOfTheDay) criteria.uniqueResult();
// --
// messageOfTheDay = (MessageOfTheDay) session.get(MessageOfTheDay.class, 0);
