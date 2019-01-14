/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class TurtleSoup {

	/**
	 * Draw a square.
	 * 
	 * @param turtle
	 *            the turtle context
	 * @param sideLength
	 *            length of each side
	 */
	public static void drawSquare(Turtle turtle, int sideLength) {
		// throw new RuntimeException("implement me!");
		// turtle = new DrawableTurtle();
		turtle.forward(sideLength);
		turtle.turn(90);
		turtle.forward(sideLength);
		turtle.turn(90);
		turtle.forward(sideLength);
		turtle.turn(90);
		turtle.forward(sideLength);
		// turtle.draw();
	}

	/**
	 * Determine inside angles of a regular polygon.
	 * 
	 * There is a simple formula for calculating the inside angles of a polygon;
	 * you should derive it and use it here.
	 * 
	 * @param sides
	 *            number of sides, where sides must be > 2
	 * @return angle in degrees, where 0 <= angle < 360
	 */
	public static double calculateRegularPolygonAngle(int sides) {
		// throw new RuntimeException("implement me!");
		return (sides - 2) * 180.00 / sides;
	}

	/**
	 * Determine number of sides given the size of interior angles of a regular
	 * polygon.
	 * 
	 * There is a simple formula for this; you should derive it and use it here.
	 * Make sure you *properly round* the answer before you return it (see
	 * java.lang.Math). HINT: it is easier if you think about the exterior
	 * angles.
	 * 
	 * @param angle
	 *            size of interior angles in degrees, where 0 < angle < 180
	 * @return the integer number of sides
	 */
	public static int calculatePolygonSidesFromAngle(double angle) {
		// throw new RuntimeException("implement me!");
		// System.out.println((360/(180.00-angle)));
		return (int) (360 / (180.00 - angle) + 0.5);
	}

	/**
	 * Given the number of sides, draw a regular polygon.
	 * 
	 * (0,0) is the lower-left corner of the polygon; use only right-hand turns
	 * to draw.
	 * 
	 * @param turtle
	 *            the turtle context
	 * @param sides
	 *            number of sides of the polygon to draw
	 * @param sideLength
	 *            length of each side
	 */
	public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
		// throw new RuntimeException("implement me!");
		turtle.forward(sideLength);
		double degrees = 360.00 / sides;
		for (int i = 1; i < sides; i++) {
			turtle.turn(degrees);
			turtle.forward(sideLength);
		}
	}

	/**
	 * Given the current direction, current location, and a target location,
	 * calculate the heading towards the target point.
	 * 
	 * The return value is the angle input to turn() that would point the turtle
	 * in the direction of the target point (targetX,targetY), given that the
	 * turtle is already at the point (currentX,currentY) and is facing at angle
	 * currentHeading. The angle must be expressed in degrees, where 0 <= angle
	 * < 360.
	 *
	 * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math
	 * libraries
	 * 
	 * @param currentHeading
	 *            current direction as clockwise from north
	 * @param currentX
	 *            current location x-coordinate
	 * @param currentY
	 *            current location y-coordinate
	 * @param targetX
	 *            target point x-coordinate
	 * @param targetY
	 *            target point y-coordinate
	 * @return adjustment to heading (right turn amount) to get to target point,
	 *         must be 0 <= angle < 360
	 */
	public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY, int targetX,
			int targetY) {
		// throw new RuntimeException("implement me!");
		if(currentX == targetX && currentY == targetY)
			return 0.0;
		double degreess = Math.atan2(targetY - currentY, targetX - currentX) / Math.PI * 180.00;
		if (degreess < 0)
			degreess += 360.00;
		currentHeading = 90.00 - currentHeading;
		if (currentHeading < 0)
			currentHeading += 360.00;
		double ans = currentHeading - degreess;
		if (ans < 0)
			ans += 360.00;
		return ans;
	}

	/**
	 * Given a sequence of points, calculate the heading adjustments needed to
	 * get from each point to the next.
	 * 
	 * Assumes that the turtle starts at the first point given, facing up (i.e.
	 * 0 degrees). For each subsequent point, assumes that the turtle is still
	 * facing in the direction it was facing when it moved to the previous
	 * point. You should use calculateHeadingToPoint() to implement this
	 * function.
	 * 
	 * @param xCoords
	 *            list of x-coordinates (must be same length as yCoords)
	 * @param yCoords
	 *            list of y-coordinates (must be same length as xCoords)
	 * @return list of heading adjustments between points, of size 0 if (# of
	 *         points) == 0, otherwise of size (# of points) - 1
	 */
	public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
		// throw new RuntimeException("implement me!");
		List<Double> ans = new ArrayList<>();
		int length = xCoords.size();
		double temp = 0;
		for (int i = 1; i < length; i++) {
			temp = calculateHeadingToPoint(temp, xCoords.get(i - 1), yCoords.get(i - 1), xCoords.get(i),
					yCoords.get(i));
			ans.add(temp);
		}
		return ans;
	}

	/**
	 * Draw your personal, custom art.
	 * 
	 * Many interesting images can be drawn using the simple implementation of a
	 * turtle. For this function, draw something interesting; the complexity can
	 * be as little or as much as you want.
	 * 
	 * @param turtle
	 *            the turtle context
	 */
	public static void drawPersonalArt(Turtle turtle) {
		// throw new RuntimeException("implement me!");
		// turtle.color(PenColor.ORANGE);
		// for(int i = 0;i<50;i++){
		// turtle.forward(50);
		// turtle.turn(123);
		// }
		// turtle.forward(120);
		for (int i = 0; i < 600; i++) {
			turtle.forward(i);
			if (i < 100)
				turtle.color(PenColor.YELLOW);
			else if (i < 200)
				turtle.color(PenColor.MAGENTA);
			else if (i < 300)
				turtle.color(PenColor.GREEN);
			else if (i < 400)
				turtle.color(PenColor.RED);
			else if (i < 500)
				turtle.color(PenColor.ORANGE);
			else {
				turtle.color(PenColor.BLUE);
			}
			turtle.turn(91);
		}
	}

	/**
	 * Main method.
	 * 
	 * This is the method that runs when you run "java TurtleSoup".
	 * 
	 * @param args
	 *            unused
	 */
	public static void main(String args[]) {
		DrawableTurtle turtle = new DrawableTurtle();

//		 drawSquare(turtle, 40);
//		int length = 40;
		// for(int i = 3;i<=6;i++){
		// }
//		 drawRegularPolygon(turtle, 8, length);
		drawPersonalArt(turtle);
		turtle.draw();
		// System.out.println(calculateHeadingToPoint(0.0, 0, 0, 0, 1));
		// System.out.println(calculateHeadingToPoint(30, 0, 1, 0, 0));
		// System.out.println(calculateHeadingToPoint(0.0, 0, 0, 1, 0));
		// System.out.println(calculateHeadingToPoint(1.0, 4, 5, 4, 6));
		// for(int i = 3;i<=100;i++){
		// System.out.println(i + "\t" +
		// calculatePolygonSidesFromAngle(TurtleSoup.calculateRegularPolygonAngle(i)));
		// }
		// calculatePolygonSidesFromAngle(128.57);
		// System.out.println(TurtleSoup.calculateRegularPolygonAngle(3));
		// System.out.println(TurtleSoup.calculateRegularPolygonAngle(5));
		// System.out.println(TurtleSoup.calculateRegularPolygonAngle(7));
		// draw the window
	}

}
