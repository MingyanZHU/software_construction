package debug.geometryProcessor;

import static org.junit.Assert.*;

import org.junit.Test;

public class SquareTest {

  /*
    Test strategy:
      Partition for inputs of getShape(): there is no other inputs.
      Partition for inputs of getName(): null string, normal name.
      Partition for inputs of getColor(): null string, normal string color.
      Partition fot inputs of getLength(): 0, positive number.
      Partition for inputs of getArea(): negative number, positive number.
      Partition for inputs of setLength(): 0, positive number.
      Partition for inputs of setName(): null string, normal string name.
      Partition for inputs of setColor(): null string, normal string color.
     */
  @Test
  public void getShape() {
    Square square = new Square(1, "Blue", "name");

    assertEquals("Square", square.getShape());
  }

  @Test
  public void getName() {
    Square square = new Square(1, "Blue", null);

    assertEquals(null, square.getName());

    Square square1 = new Square(1, "Blue", "circle");

    assertEquals("circle", square1.getName());
  }

  @Test
  public void getColour() {
    Square square = new Square(1, null, "Circle");

    assertEquals(null, square.getColour());

    Square square1 = new Square(1, "Blue", "circle");

    assertEquals("Blue", square1.getColour());
  }


  @Test
  public void getArea() {
    double length = 1;
    Square square = new Square(length, "blue", "name");

    assertEquals(length * length, square.getArea(), 0.0001);
  }

  @Test
  public void setLength() {
    Square square = new Square(0, "blue", "Name");

    assertEquals(0, square.getLength(), 0.0001);

    square.setLength(1);

    assertEquals(1, square.getLength(), 0.0001);
    assertEquals(1, square.getArea(), 0.00001);
  }

  @Test
  public void setColour() {
    Square square = new Square(1, "blue", "Circlr");

    assertEquals("blue", square.getColour());

    square.setColour("pink");

    assertEquals("pink", square.getColour());
  }

  @Test
  public void setName() {
    Square square = new Square(1, "blue", "Circle");

    assertEquals("blue", square.getColour());

    square.setName("name");
    assertEquals("name", square.getName());
  }
}