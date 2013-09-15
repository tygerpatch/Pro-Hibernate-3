package Chapter.Seven;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import Chapter.Seven.pojo.Product;
import Chapter.Seven.pojo.Software;
import Chapter.Seven.pojo.Supplier;

public class CriteriaExample {

  public static void main(String args[]) {
    CriteriaExample example = new CriteriaExample();

    System.out.println("=== Populate Database ===");
    example.populate();

    // *** Methods that use displaySupplierList
    System.out.println("=== Execute One-To-Many Associations Criteria ===");
    example.executeOneToManyAssociationsCriteria();

    System.out.println("=== Execute Association Sorting Criteria ===");
    example.executeAssociationsSortingCriteria();

    System.out.println("=== Execute QBE Criteria ===");
    example.executeQBECriteria();

    // *** Methods that use displayObjectList
    System.out.println("=== Execute Row Count Criteria ===");
    example.executeRowCountCriteria();

    // *** Methods that use displayObjectsList
    System.out.println("=== Execute Group By Criteria ===");
    example.executeGroupByCriteria();

    System.out.println("=== Execute Projection Criteria ===");
    example.executeProjectionCriteria();

    System.out.println("=== Execute Aggregate Criteria ===");
    example.executeAggregatesCriteria();

    // *** Methods that use displayProductsList

    System.out.println("=== Execute QBE Advanced Criteria ===");
    example.executeQBEAdvancedCriteria();

    System.out.println("=== Execute Not Null Or Zero QBE Criteria ===");
    example.executeNotNullOrZeroQBECriteria();

    System.out.println("=== Execute Many To One Association Criteria ===");
    example.executeManyToOneAssociationsCriteria();

    System.out.println("=== Execute Order Criteria ===");
    example.executeOrderCriteria();

    // System.out.println("=== Execute Non-Unique Result Exception Criteria ===");
    //example.executeUniqueResultExceptionCriteria();
    // BUG: throws org.hibernate.NonUniqueResultException: query did not return a unique result

    System.out.println("=== Execute Unique Result Exception Criteria ===");
    example.executeUniqueResultCriteria();

    System.out.println("=== Execute Paging Criteria ===");
    example.executePagingCriteria();

    System.out.println("=== Execute SQL Criteria ===");
    example.executeSQLCriteria();

    System.out.println("=== Execute Disjunction Criteria ===");
    example.executeDisjunctionCriteria();

    System.out.println("=== Execute And-Or Criteria ===");
    example.executeAndOrCriteria();

    System.out.println("=== Execute Or Criteria ===");
    example.executeOrCriteria();

    System.out.println("=== Execute And Criteria ===");
    example.executeAndCriteria();

    System.out.println("=== Execute Greater-Than Criteria ===");
    example.executeGreaterThanCriteria();

    System.out.println("=== Execute Null Criteria ===");
    example.executeNullCriteria();

    System.out.println("=== Execute ilike Match Mode Criteria ===");
    example.executeILikeMatchModeCriteria();

    System.out.println("=== Execute Like Pattern Criteria ===");
    example.executeLikePatternCriteria();

    System.out.println("=== Execute Not-Equals Criteria ===");
    example.executeNotEqualsCriteria();

    System.out.println("=== Execute Equals Criteria ===");
    example.executeEqualsCriteria();

    System.out.println("=== Execute Simple Criteria ===");
    example.executeSimpleCriteria();

    System.out.println("=== Execute Distinct Criteria ===");
    example.executeDistinctCriteria();
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

  // *** Methods that used use displaySupplierList

  public void executeOneToManyAssociationsCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT *
    // FROM Supplier s
    Criteria criteria = session.createCriteria(Supplier.class);

    // INNER JOIN Product p
    // ON s.id = p.supplier_id
    // WHERE p.price > 25;
    criteria.createCriteria("products").add(Restrictions.gt("price", new Double(25.0)));

    displaySupplierList(criteria.list());
    transaction.commit();
  }

  private void displaySupplierList(List<Supplier> suppliers) {
    if(suppliers.isEmpty()) {
      System.out.println("No suppliers to display.");
      return;
    }

    Supplier supplier;
    for(int i = 0; i < suppliers.size(); i++) {
      supplier = suppliers.get(i);
      System.out.println(supplier.getName());

      for(Product product : supplier.getProducts()) {
        System.out.println(product.getName() + ": " + product.getDescription() + " $" + product.getPrice());
      }

      // We've display this supplier's products, we don't need to display them again.
      suppliers.remove(supplier);
      System.out.println();
    }
  }

  public void executeAssociationsSortingCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT *
    // FROM Supplier s
    Criteria criteria = session.createCriteria(Supplier.class);

    // INNER JOIN Product p
    // ON s.id = p.supplier_id
    Criteria productCriteria = criteria.createCriteria("products");

    // WHERE p.price BETWEEN 20 AND 30
    productCriteria.add(Restrictions.between("price", (double) 20, (double) 30));

    // ORDER BY s.name ASC, p.price ASC
    criteria.addOrder(Order.asc("name"));
    productCriteria.addOrder(Order.asc("price"));

    displaySupplierList(criteria.list());
    // TODO: Why does Hibernate display the product "Web Browser", but SQL does not?  Web Browser's price isn't set.

    transaction.commit();
  }

  public void executeQBECriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Supplier supplier = new Supplier();
    supplier.setName("MegaInc");

    // SELECT *
    // FROM Supplier
    Criteria criteria = session.createCriteria(Supplier.class);

    // WHERE name = 'MegaInc'
    criteria.add(Example.create(supplier));

    displaySupplierList(criteria.list());
    transaction.commit();
  }

  // *** Methods that use displayObjectList

  public void executeRowCountCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT COUNT(*)
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);
    criteria.setProjection(Projections.rowCount());

    displayObjectList(criteria.list());
    transaction.commit();
  }

  public void displayObjectList(List<Object> list) {
    if(list.isEmpty()) {
      System.out.println("No objects to display.");
      return;
    }

    for(Object obj : list) {
      System.out.println(obj);
    }
  }

  // *** Methods that use displayObjectsList

  public void executeGroupByCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT name, price
    ProjectionList projectionList = Projections.projectionList();
    projectionList.add(Projections.groupProperty("name"));
    projectionList.add(Projections.property("price"));

    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // GROUP BY p.name
    criteria.setProjection(projectionList);

    displayObjectsList(criteria.list());
    transaction.commit();
  }

  public void executeProjectionCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT name, description
    ProjectionList projectionList = Projections.projectionList();
    projectionList.add(Projections.property("name"));
    projectionList.add(Projections.property("description"));

    // FROM Product
    Criteria criteria = session.createCriteria(Product.class);
    criteria.setProjection(projectionList);

    displayObjectsList(criteria.list());
    transaction.commit();
  }

  public void executeAggregatesCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT MAX(price), MIN(price), AVG(price), COUNT(DISTINCT description)
    ProjectionList projectionList = Projections.projectionList();

    projectionList.add(Projections.max("price"));
    projectionList.add(Projections.min("price"));
    projectionList.add(Projections.avg("price"));
    projectionList.add(Projections.countDistinct("description"));

    // From Product
    Criteria criteria = session.createCriteria(Product.class);
    criteria.setProjection(projectionList);

    displayObjectsList(criteria.list());
    transaction.commit();
  }

  private void displayObjectsList(List<Object[]> list) {
    if(list.isEmpty()) {
      System.out.println("No objects to display.");
      return;
    }

    for(Object[] obj: list) {
      for (int i = 0; i < (obj.length - 1); i++) {
        System.out.print(obj[i] + "|");
      }
      System.out.println(obj[obj.length - 1]);
    }
  }

  // **** Methods that use displayProductsList

  public void executeQBEAdvancedCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria productCriteria = session.createCriteria(Product.class);

    // INNER JOIN Supplier s
    // ON p.supplier_id = s.id
    Criteria supplierCriteria = productCriteria.createCriteria("supplier");

    // WHERE (s.name = 'SuperCorp')
    Supplier supplier = new Supplier();
    supplier.setName("SuperCorp");

    supplierCriteria.add(Example.create(supplier));

    // AND (p.name LIKE 'M%')
    Product product = new Product();
    product.setName("M%");

    Example productExample = Example.create(product);

    // TODO: Why must the price column be excluded?
    productExample.excludeProperty("price");
    productExample.enableLike();

    productCriteria.add(productExample);

    displayProductsList(productCriteria.list());
    transaction.commit();
  }

  private void displayProductsList(List<Product> list) {
    if(list.isEmpty()) {
      System.out.println("No products to display.");
      return;
    }

    // {Supplier's Name}  {Product's Name}  {Product's Price} {Product's Description}
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

  public void executeNotNullOrZeroQBECriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // select p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s
    // ON p.supplier_id = s.id

    // Note: Because we're not creating a second Criteria object that's rooted, it performs a LEFT OUTER JOIN.

    // WHERE (p.name = 'Mouse')
    Product product = new Product();
    product.setName("Mouse");

    Example example = Example.create(product);
    example.excludeZeroes();

    criteria.add(example);

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeManyToOneAssociationsCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT *
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // INNER JOIN Supplier s
    // ON p.supplier_id = s.id
    Criteria supplierCriteria = criteria.createCriteria("supplier");

    // WHERE s.name = 'MegaInc'
    supplierCriteria.add(Restrictions.eq("name", "MegaInc"));

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeOrderCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s
    // ON p.supplier_id = s.id

    // WHERE p.price > 25
    criteria.add(Restrictions.gt("price", new Double(25.0)));

    // ORDER BY p.price DESC
    criteria.addOrder(Order.desc("price"));

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeUniqueResultExceptionCriteria() {
    // BUG: throws org.hibernate.NonUniqueResultException: query did not return a unique result
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Criteria criteria = session.createCriteria(Product.class);
    Product product = (Product) criteria.uniqueResult();

    List<Product> results = new ArrayList<Product>();
    results.add(product);

    displayProductsList(results);
    transaction.commit();
  }

  public void executeUniqueResultCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name  
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);
    Criterion price = Restrictions.gt("price", new Double(25.0));

    // LEFT OUTER JOIN Supplier s
    // ON p.supplier_id = s.id

    // LIMIT 1
    criteria.setMaxResults(1);

    // TODO:
    // test for null here if needed
    List<Product> results = new ArrayList<Product>();
    Product product = (Product) criteria.uniqueResult();
    results.add(product);

    displayProductsList(results);
    transaction.commit();
  }

  public void executePagingCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s 
    // ON p.supplier_id = s.id

    // LIMIT 1, 2
    criteria.setFirstResult(1);
    criteria.setMaxResults(2);

    // "The LIMIT clause can be used to constrain the number of rows returned by the SELECT statement.
    // LIMIT takes one or two numeric arguments, which must both be nonnegative integer constants (except when using prepared statements).
    // With two arguments, the first argument specifies the offset of the first row to return, 
    // and the second specifies the maximum number of rows to return."

    // (MySQL Documentation, "13.2.8. SELECT Syntax", http://dev.mysql.com/doc/refman/5.0/en/select.html)

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeSQLCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s 
    // ON p.supplier_id = s.id

    // WHERE p.name LIKE 'Mou%'
    criteria.add(Restrictions.sqlRestriction("{alias}.name like 'Mou%'"));

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeDisjunctionCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s 
    // ON p.supplier_id = s.id

    // WHERE (p.price > 25 
    Disjunction disjunction = Restrictions.disjunction();
    disjunction.add(Restrictions.gt("price", new Double(25.0)));

    // OR p.name like 'Mou%'
    disjunction.add(Restrictions.like("name", "Mou%"));

    // OR LOWER(p.description) LIKE 'blocks%')
    disjunction.add(Restrictions.ilike("description", "blocks%"));
    criteria.add(disjunction);

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeAndOrCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s
    // ON p.supplier_id = s.id

    // WHERE (p.price > 25 OR p.name LIKE 'Mou%')
    criteria.add(Restrictions.or(Restrictions.gt("price", new Double(25.0)), Restrictions.like("name", "Mou%")));

    // AND LOWER(p.description) LIKE 'blocks%'
    criteria.add(Restrictions.ilike("description", "blocks%"));

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeOrCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s
    // ON p.supplier_id = s.id

    // WHERE (p.price > 25 OR p.name LIKE 'Mou%')
    criteria.add(Restrictions.or(Restrictions.gt("price", new Double(25.0)), Restrictions.like("name", "Mou%")));

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeAndCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s 
    // ON p.supplier_id = s.id

    // WHERE p.price > 25
    criteria.add(Restrictions.gt("price", new Double(25.0)));

    // AND p.name LIKE 'K%'
    criteria.add(Restrictions.like("name", "K%"));

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeGreaterThanCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s
    // ON p.supplier_id = s.id

    // WHERE p.price > 25
    criteria.add(Restrictions.gt("price", new Double(25.0)));

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeNullCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s
    // ON p.supplier_id = s.id

    // WHERE p.name IS NULL
    criteria.add(Restrictions.isNull("name"));

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeILikeMatchModeCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s
    // ON p.supplier_id = s.id

    // WHERE LOWER(p.name) LIKE 'browser'
    criteria.add(Restrictions.ilike("name", "browser", MatchMode.END));
    // Note: This matches the end of the String.
    // TODO: How to do this in SQL?

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeLikePatternCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s
    // ON p.supplier_id = s.id

    // WHERE p.name LIKE 'Mou%'
    criteria.add(Restrictions.like("name", "Mou%"));

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeNotEqualsCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s
    // ON p.supplier_id = s.id

    // WHERE p.name <> 'Mouse'
    criteria.add(Restrictions.ne("name", "Mouse"));

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeEqualsCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s
    // ON p.supplier_id = s.id

    // WHERE p.name = 'Mouse'
    criteria.add(Restrictions.eq("name", "Mouse"));

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeSimpleCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.versio, p.DTYPE, s.id, s.name
    // FROM Product p 
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s
    // ON p.supplier_id = s.id

    displayProductsList(criteria.list());
    transaction.commit();
  }

  public void executeDistinctCriteria() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT p.id, p.description, p.name, p.price, p.supplier_id, p.version, p.DTYPE, s.id, s.name
    // FROM Product p
    Criteria criteria = session.createCriteria(Product.class);

    // LEFT OUTER JOIN Supplier s
    // ON p.supplier_id = s.id

    // WHERE p.price > 25
    criteria.add(Restrictions.gt("price", new Double(25.0)));
    // AND p.name LIKE 'K%'
    criteria.add(Restrictions.like("name", "K%"));

    // TODO:
    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

    displayProductsList(criteria.list());
    transaction.commit();
  }
}
