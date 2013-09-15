package HQL.Client;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import HQL.POJOs.Product;
import HQL.POJOs.Software;
import HQL.POJOs.Supplier;

//import java.util.List;
//import javax.persistence.Query;

//import org.hibernate.cfg.AnnotationConfiguration;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.tool.hbm2ddl.SchemaExport;
//
//import Chapter.Three.Cameron_Mckenzie.HibernateUtil;
//import Criteria.POJOs.Product;
//import Criteria.POJOs.Software;
//import Criteria.POJOs.Supplier;

public class HQLExample {

  public static void main(String args[]) {
    HQLExample example = new HQLExample();

    System.out.println("=== Populate Database ===");
    example.populate();

    // *** Methods that use displayProductsList
    System.out.println("=== Execute Simple HQL ===");
    example.executeSimpleHQL();

    System.out.println("=== Execute Fully Qualified HQL ===");
    example.executeFullyQualifiedHQL();

    System.out.println("=== Execute Delete HQL ===");
    example.executeDeleteHQL();

    System.out.println("=== Execute Order Two Properties HQL ===");
    example.executeOrderTwoPropertiesHQL();

    System.out.println("=== Execute Order HQL ===");
    example.executeOrderHQL();

    System.out.println("=== Execute Unique Result HQL ===");
    example.executeUniqueResultHQL();

    System.out.println("=== Execute Paging HQL ===");
    example.executePagingHQL();

    System.out.println("=== Execute Object Named Paramters HQL ===");
    example.executeObjectNamedParametersHQL();

    System.out.println("=== Execute Named Parameters HQL ===");
    example.executeNamedParametersHQL();

    System.out.println("=== Execute HQL for Restrictions ===");
    example.executeHQLForRestrictions();

    System.out.println("=== Execute Criteria for Restrictions ===");
    example.executeCriteriaForRestrictions();

    // *** Methods that use displaySupplierList

    System.out.println("=== Execute Update HQL ===");
    example.executeUpdateHQL();

    System.out.println("=== Execute Select SQL ===");
    example.executeSelectSQL();

    System.out.println("=== Execute Fetch Association HQL ===");
    example.executeFetchAssociationsHQL();

    // *** Methods that use displayObjectsList
    System.out.println("=== Execute Count HQL ===");
    example.executeCountHQL();

    System.out.println("=== Execute Association Objects HQL ===");
    example.executeAssociationObjectsHQL();

    System.out.println("=== Execute Association HQL ===");
    example.executeAssociationsHQL();

    System.out.println("=== Execute Projection HQL ===");
    example.executeProjectionHQL();

//    // *** Methods that use displayObjectList
//
//    System.out.println("=== Execute Scalar SQL ===");
//    example.executeScalarSQL();
//
//    System.out.println("=== Execute Named Query ===");
//    example.executeNamedQuery();
  }

  // *** Populate the database

  public void populate() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Supplier superCorp = new Supplier("SuperCorp");
    session.save(superCorp);

    Product opticalWheelMouse = new Product();
    session.save(opticalWheelMouse);

    opticalWheelMouse.setName("Mouse");
    opticalWheelMouse.setDescription("Optical Wheel Mouse");
    opticalWheelMouse.setPrice(20.0);

    opticalWheelMouse.setSupplier(superCorp);
    superCorp.addProduct(opticalWheelMouse);

    Product oneButtonMouse = new Product();
    session.save(oneButtonMouse);

    oneButtonMouse.setName("Mouse");
    oneButtonMouse.setDescription("One Button Mouse");
    oneButtonMouse.setPrice(22.0);

    oneButtonMouse.setSupplier(superCorp);
    superCorp.addProduct(oneButtonMouse);

    Software webBrowser = new Software();
    session.save(webBrowser);

    webBrowser.setName("Web Browser");
    webBrowser.setDescription("Blocks Pop-ups");
    webBrowser.setVersion("2.0");

    webBrowser.setSupplier(superCorp);
    superCorp.addProduct(webBrowser);

    Supplier megaInc = new Supplier("MegaInc");
    session.save(megaInc);

    Product keyboard = new Product();
    session.save(keyboard);

    keyboard.setName("Keyboard");
    keyboard.setDescription("101 Keys");
    keyboard.setPrice(30.0);

    keyboard.setSupplier(megaInc);
    megaInc.addProduct(keyboard);

    Software email = new Software();
    session.save(email);

    email.setName("Email");
    email.setDescription("Blocks spam");
    email.setPrice(49.99);
    email.setVersion("4.1 RMX Edition");

    email.setSupplier(megaInc);
    megaInc.addProduct(email);

    transaction.commit();
  }

  // The following was derived from the book "Hibernate Made Easy" by Cameron McKenzie.
  // Specifically, HibernateUtil's getSession and getInitializedConfiguration methods.
  private static SessionFactory factory;

  private static Session getSession() {
    if (factory == null) {
      Configuration config = new AnnotationConfiguration();

      // add all of your JPA annotated classes here!!!
      config.addAnnotatedClass(Product.class);
      config.addAnnotatedClass(Software.class);
      config.addAnnotatedClass(Supplier.class);

      config.configure();

      // generate the tables
      SchemaExport schemaExport = new SchemaExport(config);

      boolean script = true; // print the DDL to the console
      boolean export = true; // export the script to the database

      schemaExport.create(script, export);
      factory = config.buildSessionFactory();
    }

    return factory.getCurrentSession();
  }

  // *** Methods that use displayProductsList

  public void executeSimpleHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("from Product");

    displayProductsList(query.list());
    transaction.commit();
  }

  private void displayProductsList(List<Product> list) {
    if(list.isEmpty()) {
      System.out.println("No products to display.");
      return;
    }

    for(Product product : list) {
      System.out.print(product.getSupplier().getName());
      System.out.print("\t");
      System.out.print(product.getName());
      System.out.print("\t");
      System.out.print(product.getPrice());
      System.out.print("\t");
      System.out.println(product.getDescription());
    }
  }

  public void executeFullyQualifiedHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("FROM HQL.POJOs.Product");

    displayProductsList(query.list());
    transaction.commit();
  }

  public void executeDeleteHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("DELETE FROM Product WHERE name = :name");
    query.setString("name", "Mouse");

    int rowCount = query.executeUpdate();
    System.out.println("Rows affected: " + rowCount);

    // See the results of the update
    query = session.createQuery("from Product");

    displayProductsList(query.list());
    transaction.commit();
  }

  public void executeOrderTwoPropertiesHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("FROM Product p ORDER BY p.supplier.name ASC, p.price ASC");

    displayProductsList(query.list());
    transaction.commit();
  }

  public void executeOrderHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("FROM Product p WHERE p.price > 25.0 ORDER BY p.price DESC");

    displayProductsList(query.list());
    transaction.commit();
  }

  public void executeUniqueResultHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("FROM Product WHERE price > 25.0");
    query.setMaxResults(1);

    Product product = (Product) query.uniqueResult();

    // test for null here if needed
    List<Product> results = new ArrayList<Product>();
    results.add(product);

    displayProductsList(results);
    transaction.commit();
  }

  public void executeCriteriaForRestrictions() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Criterion price = Restrictions.gt("price", new Double(25.0));
    Criterion name = Restrictions.like("name", "Mou%");
    LogicalExpression orExp = Restrictions.or(price, name);

    Criteria criteria = session.createCriteria(Product.class);
    criteria.add(orExp);
    criteria.add(Restrictions.ilike("description", "blocks%"));

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeHQLForRestrictions() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("FROM Product WHERE price > 25.0 AND name LIKE 'Mou%'");

    displayProductsList(query.list());
    transaction.commit();
  }

  public void executeNamedParametersHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("FROM Product WHERE price > :price");
    query.setDouble("price", 25.0);

    displayProductsList(query.list());
    transaction.commit();
  }

  public void executeObjectNamedParametersHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("FROM Product AS product WHERE product.supplier = :supplier");

    Supplier supplier = (Supplier) session.createQuery("FROM Supplier WHERE name = 'MegaInc'").list().get(0);
    query.setEntity("supplier", supplier);

    displayProductsList(query.list());
    transaction.commit();
  }

  public void executePagingHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("FROM Product");
    query.setFirstResult(1);
    query.setMaxResults(2);

    displayProductsList(query.list());
    transaction.commit();
  }

  // *** Methods that use displaySupplierList

  public void executeUpdateHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("UPDATE Supplier SET name = :newName WHERE name = :name");
    query.setString("name", "SuperCorp");
    query.setString("newName", "MegaCorp");

    int rowCount = query.executeUpdate();
    System.out.println("Rows affected: " + rowCount);
    // See the results of the update
    query = session.createQuery("FROM Supplier");

    displaySupplierList(query.list());
    transaction.commit();
  }

  private void displaySupplierList(List<Supplier> list) {
    if(list.isEmpty()) {
      System.out.println("No suppliers to display.");
      return;
    }

    for(Supplier supplier : list) {
      System.out.println(supplier.getName());
    }
  }

  public void executeSelectSQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    SQLQuery query = session.createSQLQuery("SELECT {supplier.*} FROM Supplier supplier");
    query.addEntity("supplier", Supplier.class);

    displaySupplierList(query.list());
    transaction.commit();
  }

  public void executeFetchAssociationsHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("FROM Supplier s INNER JOIN FETCH s.products AS p");

    displaySupplierList(query.list());
    transaction.commit();
  }

  // *** Methods that use displayObjectList

//  public void executeNamedQuery() {
//    Session session = getSession();
//    Transaction transaction = session.beginTransaction();
//
//    Query query = session.getNamedQuery("Chapter.Seven.pojo.Product.HQLpricing");
//    displayObjectList(query.list());
//    query = session.getNamedQuery("Chapter.Seven.pojo.Product.SQLpricing");
//
//    displayObjectList(query.list());
//    transaction.commit();
//  }
//
//  public void executeScalarSQL() {
//    Session session = getSession();
//    Transaction transaction = session.beginTransaction();
//
//    SQLQuery query = session.createSQLQuery("SELECT AVG(product.price) AS avgPrice FROM Product product");
//    query.addScalar("avgPrice", Hibernate.DOUBLE); // BUG: DOUBLE cannot be resolved
//
//    displayObjectList(query.list());
//    transaction.commit();
//  }
//
//  private void displayObjectList(List<Object> list) {
//    if(list.isEmpty()) {
//      System.out.println("No objects to display.");
//      return;
//    }
//
//    for(Object obj : list) {
//      System.out.println(obj.getClass().getName());
//      System.out.println(obj);
//    }
//  }

  // *** Methods that use displayObjectsList

  public void executeProjectionHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("SELECT product.name, product.price FROM Product product");

    displayObjectsList(query.list());
    transaction.commit();
  }

  public void executeAssociationsHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("SELECT s.name, p.name, p.price FROM Product p INNER JOIN p.supplier as s");

    displayObjectsList(query.list());
    transaction.commit();
  }

  public void executeAssociationObjectsHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("FROM Product p INNER JOIN p.supplier AS s");

    displayObjectsList(query.list());
    transaction.commit();
  }

  public void executeCountHQL() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Query query = session.createQuery("SELECT MIN(product.price), MAX(product.price) FROM Product product");

    displayObjectsList(query.list());
    transaction.commit();
  }

  private void displayObjectsList(List<Object[]> list) {
    if(list.isEmpty()) {
      System.out.println("No objects to display.");
      return;
    }

    for(Object[] objArray : list) {
      System.out.println("New object");

      for (int i = 0; i < objArray.length; i++) {
        System.out.println(objArray[i]);
      }

      System.out.println();
    }
  }
}
