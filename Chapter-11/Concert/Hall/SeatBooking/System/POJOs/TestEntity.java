package Concert.Hall.SeatBooking.System.POJOs;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// Used by the EventExerciser test as the data item.
public class TestEntity {

  public TestEntity() {
  }

  public TestEntity(String value) {
    this.value = value;
  }

  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  private int id;

  @Id
  @GeneratedValue
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
