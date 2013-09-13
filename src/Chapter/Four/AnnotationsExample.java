package Chapter.Four;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import Chapter.Four.pojo.Author;
import Chapter.Four.pojo.Book;
import Chapter.Four.pojo.ComputerBook;
import Chapter.Four.pojo.Publisher;

public class AnnotationsExample {
  public static void main(String[] args) {
    AnnotationConfiguration configuration = new AnnotationConfiguration();

    configuration.addAnnotatedClass(Author.class);
    configuration.addAnnotatedClass(Book.class);
    configuration.addAnnotatedClass(ComputerBook.class);
    configuration.addAnnotatedClass(Publisher.class);

    configuration.configure();

    SchemaExport schemaExport = new SchemaExport(configuration);

    boolean script = true;  // print the DDL to the console
    boolean export = true;  // export the script to the database

    schemaExport.create(script, export);

    // populate database
    Author jeff = new Author();
    jeff.setName("Jeff");

    Author dave = new Author();
    dave.setName("Dave");

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

    SessionFactory sessionFactory = configuration.buildSessionFactory();
    Session session = sessionFactory.openSession();

    Transaction transaction = session.beginTransaction();

    session.save(jeff);
    session.save(dave);
    session.save(publisher);

    transaction.commit();

    // display
    Query query = session.createQuery("FROM Publisher p");
    query.setMaxResults(1);
    publisher = (Publisher) query.uniqueResult();

    if (publisher == null) {
      System.out.println("No publishers found");
      return;
    }

    System.out.println("Publisher name: " + publisher.getName());
    Set<Book> books = publisher.getBooks();

    if (books != null) {
      for(Book _book : books){
        System.out.println("Title: " + _book.getTitle());
        Set<Author> authors = _book.getAuthors();

        if (authors != null) {
          for(Author author : authors){
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

}
