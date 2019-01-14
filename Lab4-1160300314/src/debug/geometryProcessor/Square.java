package debug.geometryProcessor;

public class Square implements Shape {

  double length;
  double area;
  String colour;
  String name;

  public Square(double l, String c, String n) {
    length = l;
    area = l * l;
    colour = c;
    name = n;
  }

  public double getLength() {
    return length;
  }

  public String getShape() {
    return "Square";
  }

  public String getName() {
    return name;
  }

  public String getColour() {
    return colour;
  }

  @Override
  public double getArea() {
    return this.area;
  }

  public void setLength(double l) {
    length = l;
    area = this.length * this.length;
  }

  public void setColour(String c) {
    colour = c;
  }

  public void setName(String n) {
    name = n;
  }

}
