package debug.geometryProcessor;

import static org.junit.Assert.*;

import org.junit.Test;

public class CircleTest {

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
    Circle circle = new Circle(1, "Blue", "name");

    assertEquals("Circle", circle.getShape());
  }

  @Test
  public void getName() {
    Circle circle = new Circle(1, "Blue", null);

    assertEquals(null, circle.getName());

    Circle circle1 = new Circle(1, "Blue", "circle");

    assertEquals("circle", circle1.getName());
  }

  @Test
  public void getColour() {
    Circle circle = new Circle(1, null, "Circle");

    assertEquals(null, circle.getColour());

    Circle circle1 = new Circle(1, "blue", "Circle");

    assertEquals("blue", circle1.getColour());
  }

  @Test
  public void getLength() {
    Circle circle = new Circle(0, "blue", "Name");

    assertEquals(0, circle.getLength(), 0.0001);
  }

  @Test
  public void getArea() {
    double length = 1;
    Circle circle = new Circle(length, "blue", "name");

    assertEquals(Math.PI * (length / 2) * (length / 2), circle.getArea(), 0.0001);
  }

  @Test
  public void setLength() {
    Circle circle = new Circle(0, "blue", "Name");

    assertEquals(0, circle.getLength(), 0.0001);

    circle.setLength(1);

    assertEquals(1, circle.getLength(), 0.0001);
  }

  @Test
  public void setColour() {
    Circle circle1 = new Circle(1, "blue", "Circlr");

    assertEquals("blue", circle1.getColour());

    circle1.setColour("pink");

    assertEquals("pink", circle1.getColour());
  }

  @Test
  public void setName() {
    Circle circle = new Circle(1, "blue", "Circle");

    assertEquals("blue", circle.getColour());

    circle.setName("name");
    assertEquals("name", circle.getName());
  }
}