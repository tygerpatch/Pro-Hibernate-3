package Chapter.Four;

import org.hibernate.cfg.AnnotationConfiguration;

import Chapter.Four.pojo.Author;
import Chapter.Four.pojo.Book;
import Chapter.Four.pojo.ComputerBook;
import Chapter.Four.pojo.Publisher;

public class ExampleConfiguration {
  public static AnnotationConfiguration getConfig() {
    AnnotationConfiguration cfg = new AnnotationConfiguration();
    cfg.addAnnotatedClass(Author.class);
    cfg.addAnnotatedClass(Book.class);
    cfg.addAnnotatedClass(ComputerBook.class);
    cfg.addAnnotatedClass(Publisher.class);
    return cfg;
  }
}
