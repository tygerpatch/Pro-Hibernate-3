package BookCatalog.Client;

import java.util.Date;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import BookCatalog.POJOs.Author;
import BookCatalog.POJOs.Book;
import BookCatalog.POJOs.ComputerBook;
import BookCatalog.POJOs.Publisher;

public class ConsoleApplication {
  public static void main(String[] args) {
    ConsoleApplication app = new ConsoleApplication();
    app.populate();
    app.display();
  }

  public void populate() {
    Session session = getSession();
    Transaction transaction = session.beginTransaction();

    Author jeff = new Author();
    jeff.setName("Jeff");
    session.save(jeff);

    Author dave = new Author();
    dave.setName("Dave");
    session.save(dave);

    Book book = new Book();

    book.setTitle("Pro Hibernate 3 ");
    book.setPages(200);
    book.setPublicationDate(new Date());

    jeff.addBook(book);
    book.addAuthor(jeff);

    dave.addBook(book);
    book.addAuthor(dave);

    ComputerBook computerBook = new ComputerBook();

    computerBook.setTitle("Building Portals with the Java Portlet API");
    computerBook.setPages(350);
    computerBook.setSoftwareName("Apache Pluto");

    jeff.addBook(computerBook);
    computerBook.addAuthor(jeff);

    dave.addBook(computerBook);
    computerBook.addAuthor(dave);

    Publisher publisher = new Publisher();

    publisher.setName("Apress");
    publisher.addBook(book);
    publisher.addBook(computerBook);

    session.save(publisher);
    transaction.commit();
  }

  public void display() {
    Session session = getSession();
    Query query = session.createQuery("FROM Publisher p");
    query.setMaxResults(1);
    Publisher publisher = (Publisher) query.uniqueResult();

    if (publisher == null) {
      System.out.println("No publishers found");
      return;
    }

    System.out.println("Publisher name: " + publisher.getName());
    Set<Book> books = publisher.getBooks();

    if (books != null) {
      for (Book _book : books) {
        System.out.println("Title: " + _book.getTitle());
        Set<Author> authors = _book.getAuthors();

        if (authors != null) {
          for (Author author : authors) {
            System.out.println("Author: " + author.getName());
          }
        }

        if (_book instanceof ComputerBook) {
          System.out.println("Software: " + ((ComputerBook) _book).getSoftwareName());
        }
      }
    }

    session.close();
  }

  // The following was derived from the book "Hibernate Made Easy" by Cameron McKenzie.
  // Specifically, HibernateUtil's getSession and getInitializedConfiguration methods.
  private static SessionFactory factory;

  private static Session getSession() {
    if (factory == null) {
      Configuration config = new AnnotationConfiguration();

      // add all of your JPA annotated classes here!!!
      config.addAnnotatedClass(Author.class);
      config.addAnnotatedClass(Book.class);
      config.addAnnotatedClass(ComputerBook.class);
      config.addAnnotatedClass(Publisher.class);

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
}
