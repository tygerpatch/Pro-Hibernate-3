package TutorialsPoint;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

// This class was derived from the following resource:

// (Tutorials Point, "Hibernate Interceptors",
// http://www.tutorialspoint.com/hibernate/hibernate_interceptors.htm)

public class ManageEmployee {

  public static void main(String[] args) {
    ManageEmployee manageEmployee = new ManageEmployee();

    // Add few employee records in database
    Integer empID1 = manageEmployee.addEmployee("Zara", "Ali", 1000);
    Integer empID2 = manageEmployee.addEmployee("Daisy", "Das", 5000);
    Integer empID3 = manageEmployee.addEmployee("John", "Paul", 10000);

    // List down all the employees
    manageEmployee.listEmployees();

    // Update employee's records
    manageEmployee.updateEmployee(empID1, 5000);

    // Delete an employee from the database
    manageEmployee.deleteEmployee(empID2);

    // List down new list of the employees
    manageEmployee.listEmployees();
  }

  // Method to CREATE an employee in the database
  public Integer addEmployee(String firstName, String lastName, int salary) {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Employee employee = new Employee(firstName, lastName, salary);
    Integer employeeID = (Integer) session.save(employee);

    transaction.commit();
    return employeeID;
  }

  // Method to READ all the employees
  public void listEmployees() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    // SELECT *
    // FROM Supplier s
    List<Employee> employees = session.createCriteria(Employee.class).list();

    for(Employee employee : employees) {
      System.out.print("First Name: " + employee.getFirstName());
      System.out.print("  Last Name: " + employee.getLastName());
      System.out.println("  Salary: " + employee.getSalary());
    }

    transaction.commit();
  }

  // Method to UPDATE salary for an employee
  public void updateEmployee(Integer EmployeeID, int salary) {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Employee employee = (Employee) session.get(Employee.class, EmployeeID);
    employee.setSalary(salary);
    session.update(employee);

    transaction.commit();
  }

  // Method to DELETE an employee from the records
  public void deleteEmployee(Integer EmployeeID) {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Employee employee = (Employee) session.get(Employee.class, EmployeeID);
    session.delete(employee);

    transaction.commit();
  }

  // The following was derived from the book "Hibernate Made Easy" by Cameron McKenzie.
  // Specifically, HibernateUtil's getSession and getInitializedConfiguration methods.
  private static SessionFactory factory;

  private static Session getSession() {
    if (factory == null) {
      Configuration config = new AnnotationConfiguration();

      // add all of your JPA annotated classes here!!!
      config.addAnnotatedClass(Employee.class);

      config.configure();

      // generate the tables
      new SchemaExport(config).create(true, true);
      factory = config.buildSessionFactory();

      // Apply this interceptor at a global level...
      config.setInterceptor(new MyInterceptor());
    }

    return factory.getCurrentSession();
  }

}
