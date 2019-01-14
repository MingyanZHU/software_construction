package debug.geometryProcessor;

public interface Shape {

  //Getter Methods for properties shared by all shapes
  public String getShape();

  public String getName();

  public String getColour();

  public double getArea();

  //Setter Methods for properties shared by all shapes
  public void setLength(double l);

  public void setColour(String c);

  public void setName(String n);

}
