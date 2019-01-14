package debug.geometryProcessor;

import static org.junit.Assert.*;

import org.junit.Test;

public class TriangleTest {
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
  public void getLength() {
    Triangle triangle = new Triangle(0, "blue", "Name");

    assertEquals(0, triangle.getLength(), 0.0001);

    Triangle triangle1 = new Triangle(1, "blue", "Name");

    assertEquals(1, triangle1.getLength(), 0.0001);
  }

  @Test
  public void getShape() {
    Triangle triangle = new Triangle(0, "blue", "Name");
    assertEquals("Triangle", triangle.getShape());
  }

  @Test
  public void getName() {
    Triangle triangle = new Triangle(0, "blue", "Name");
    Triangle triangle1 = new Triangle(0, "blue", null);

    assertNull(triangle1.getName());
    assertEquals("Name", triangle.getName());
  }

  @Test
  public void getColour() {
    Triangle triangle = new Triangle(0, null, "Name");
    Triangle triangle1 = new Triangle(0, "blue", "Name");

    assertNull(triangle.getColour());
    assertEquals("blue", triangle1.getColour());
  }

  @Test
  public void getArea() {
    Triangle triangle = new Triangle(1, null, "Name");

    assertEquals(Math.sqrt(3) * 1 * 1 / 4, triangle.getArea(), 0.0001);
  }

  @Test
  public void setLength() {
    Triangle triangle = new Triangle(0, null, "Name");

    assertEquals(0, triangle.getLength(), 0.001);

    triangle.setLength(1);

    assertEquals(1, triangle.getLength(), 0.0001);
  }

  @Test
  public void setColour() {
    Triangle triangle = new Triangle(0, null, "Name");

    assertNull(triangle.getColour());

    triangle.setColour("blue");

    assertEquals("blue", triangle.getColour());
  }

  @Test
  public void setName() {
    Triangle triangle = new Triangle(0, null, "Name");

    assertEquals("Name", triangle.getName());

    triangle.setName(null);

    assertNull(triangle.getName());
  }
}