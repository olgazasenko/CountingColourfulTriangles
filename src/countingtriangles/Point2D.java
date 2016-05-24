/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package countingtriangles;

import java.awt.Color;
import java.util.Comparator;

/**
 *
 * @author Olga
 */
public class Point2D implements Comparable<Point2D> {

    /**
     * Compares two points by polar angle (between 0 and 2pi) with respect to
     * this point.
     */
    public final Comparator<Point2D> POLAR_ORDER = new PolarOrder();
    public double x;    // x coordinate
    public double y;    // y coordinate
    public Color color;
    int index; //index in the corresponding color array
    int pointer; //pointing back from the color array

    public Point2D() {
    }

    public Point2D(double x, double y, Color c) {
        if (x == 0.0) {
            x = 0.0;  // convert -0.0 to +0.0
        }
        if (y == 0.0) {
            y = 0.0;  // convert -0.0 to +0.0
        }
        this.x = x;
        this.y = y;
        this.color = c;
    }

    public Point2D(double x, double y) {
        if (x == 0.0) {
            x = 0.0;  // convert -0.0 to +0.0
        }
        if (y == 0.0) {
            y = 0.0;  // convert -0.0 to +0.0
        }
        this.x = x;
        this.y = y;
    }

    public void addColor(Color c) {
        this.color = c;
    }

    /**
     * Is a->b->c a counterclockwise turn?
     *
     * @param a first point
     * @param b second point
     * @param c third point
     * @return { -1, 0, +1 } if a->b->c is a { clockwise, collinear;
     * counterclockwise } turn.
     */
    public static int ccw(Point2D a, Point2D b, Point2D c) {
        double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        if (area2 < 0) {
            return -1;
        } else if (area2 > 0) {
            return +1;
        } else {
            return 0;
        }
    }

    @Override
    public int compareTo(Point2D o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // compare other points relative to polar angle (between 0 and 2pi) they make with this Point
    private class PolarOrder implements Comparator<Point2D> {

        @Override
        public int compare(Point2D q1, Point2D q2) {
            double dx1 = q1.x - x;
            double dy1 = q1.y - y;
            double dx2 = q2.x - x;
            double dy2 = q2.y - y;

            if ((dy1 == 0 && dy2 == 0) || (dx1 == 0 && dx2 == 0)) {// 3-collinear and horizontal or vertical
                System.out.println("Two points are collinear to the cenroid");
                System.exit(0);
            }
            if (dy1 >= 0 && dy2 < 0) {
                return -1;    // q1 above; q2 below
            } else if (dy2 >= 0 && dy1 < 0) {
                return 1;    // q1 below; q2 above
            } /* else if (dy1 == 0 && dy2 == 0) {            
             if      (dx1 >= 0 && dx2 < 0) return -1;
             else if (dx2 >= 0 && dx1 < 0) return +1;
             else                          return  0;
                
             }*/ else {
                return -ccw(Point2D.this, q1, q2);     // both above or below
            }
        }
    }

    /**
     * Returns the angle between that1 point, this point and that2 point.
     *
     * @param that1 the first other point
     * @param that2 the second other point
     * @return the angle between that1 point, this point and that2 point
     */
    public double angleBetween(Point2D that1, Point2D that2) {
        double dx1 = this.x - that1.x;
        double dy1 = this.y - that1.y;
        double dx2 = this.x - that2.x;
        double dy2 = this.y - that2.y;
        double cosine = (dx1 * dx2 + dy1 * dy2) / (Math.sqrt(dx1 * dx1 + dy1 * dy1) * Math.sqrt(dx2 * dx2 + dy2 * dy2));
        return Math.acos(cosine);
    }

    public double angleBetween1(Point2D current, Point2D previous) {
        double ang = Math.toDegrees(Math.atan2(current.x - this.x, current.y - this.y)
                - Math.atan2(previous.x - this.x, previous.y - this.y));
        if (ang < 0){
            ang += 360;
        }
        return ang;
    }

    public double signAngle(Point2D that1, Point2D that2) {
        double dx1 = that1.x - this.x;
        double dy1 = that1.y - this.y;
        double dx2 = that2.x - this.x;
        double dy2 = that2.y - this.y;
        double dot = dx1 * dx2 + dy1 * dy2;
        double cross = dx1 * dy2 - dy1 * dx2;
        double angle = Math.atan2(cross, dot);
        angle *= (-180 / Math.PI);
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    public void changeIndex(int index) {
        this.index = index;
    }

    public boolean liesWithin(Point2D p) {
        return true;
    }
}
