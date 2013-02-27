package Chapter.Four;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import Chapter.Four.pojo.Author;
import Chapter.Four.pojo.Book;
import Chapter.Four.pojo.ComputerBook;
import Chapter.Four.pojo.Publisher;

public class GenerateSchema {

  public static void main(String[] args) {
    // Amended to allow a path (to the hibernate.cfg.xml) to be passed to the configuration.
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
  }
}
