package debug.geometryProcessor;

import java.util.Random;

public class Main {

  public static void main(String[] args) {

    // Create a 3x3 array of shapes
    //Shape[][] shapes = new Shape[3][((int) (Math.random() * 33))];
    Shape[][] shapes = new Shape[3][new Random().nextInt(33)];
    // Instantiate shape objects with one of each type in each row
    for (int i = 0; i < shapes.length; i++) {
      for (int n = 0; n < shapes[i].length; n++) {
        if (n % 3 == 0) {
          shapes[i][n] = new Circle(Math.random() * 10, "Red", "Circle" + i + n);
        } else if (n % 3 == 1) {
          shapes[i][n] = new Square(Math.random() * 10, "Green", "Square" + i + n);
        } else if (n % 3 == 2) {
          shapes[i][n] = new Triangle(Math.random() * 10, "Blue", "Triangle" + i + n);
        }

      }
    }

    System.out.println("\nPrinting all shapes grouped by array\n");
    //Prints all Shapes
    for (int i = 0; i < shapes.length; i++) {
      for (int n = 0; n < shapes[i].length; n++) {
        System.out.println("Shape: " + shapes[i][n].getShape() + ", Name: " + shapes[i][n].getName()
            + ", Area: " + shapes[i][n].getArea() + ", Colour: " + shapes[i][n].getColour());
      }
    }

    System.out.println("\nPrinting out shapes grouped by type...\n");

    //Prints all Circles
    for (int i = 0; i < 3; i++) {
      for (int n = 0; n < shapes[i].length; n++) {
        if (n % 3 == 0) {
          System.out
              .println("Shape: " + shapes[i][n].getShape() + ", Name: " + shapes[i][n].getName()
                  + ", Area: " + shapes[i][n].getArea() + ", Colour: " + shapes[i][n].getColour());
        }

      }
    }

    //Prints all Squares
    for (int i = 0; i < 3; i++) {
      for (int n = 0; n < shapes[i].length; n++) {
        if (n % 3 == 1) {
          System.out
              .println("Shape: " + shapes[i][n].getShape() + ", Name: " + shapes[i][n].getName()
                  + ", Area: " + shapes[i][n].getArea() + ", Colour: " + shapes[i][n].getColour());
        }

      }
    }

    //Prints all Triangles
    for (int i = 0; i < 3; i++) {
      for (int n = 0; n < shapes[i].length; n++) {
        if (n % 3 == 2) {
          System.out
              .println("Shape: " + shapes[i][n].getShape() + ", Name: " + shapes[i][n].getName()
                  + ", Area: " + shapes[i][n].getArea() + ", Colour: " + shapes[i][n].getColour());
        }

      }
    }

  }

}
