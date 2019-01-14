package debug.geometryProcessor;

public class Triangle implements Shape {

  double length;
  double area;
  String colour;
  String name;

  public Triangle(double l, String c, String n) {
    length = l;
    colour = c;
    area = Math.sqrt(3) * l * l / 4;
    name = n;
  }

  public double getLength() {
    return length;
  }

  public String getShape() {
    return "Triangle";
  }

  public String getName() {
    return name;
  }

  public String getColour() {
    return colour;
  }

  public double getArea() {
    return area;
  }

  public void setLength(double l) {
    length = l;
    area = Math.sqrt(3) * l * l / 4;
  }

  public void setColour(String c) {
    colour = c;
  }

  public void setName(String n) {
    name = n;
  }

}
