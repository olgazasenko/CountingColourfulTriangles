/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package countingtriangles;

import java.awt.Color;

/**
 *
 * @author Olga
 */
public class SplitAndCount {

    CircularArrayList<Point2D> sortedAstar;
    CircularArrayList<Red> Red;
    CircularArrayList<Blue> Blue;
    CircularArrayList<Green> Green;
    int blueGreenCount[]; //amount of Red points between Blue[i] and nextGreen
    int greenBlueCount[];
    int blueBlueCount[];
    int greenGreenCount[];
    int TB[];
    int TG[];
    int T = 0;

    SplitAndCount(CircularArrayList<Point2D> sortedAstar, int countRed, int countBlue, int countGreen) {
        this.sortedAstar = sortedAstar;
        Red = new CircularArrayList(countRed);
        Blue = new CircularArrayList(countBlue);
        blueGreenCount = new int[countBlue];
        Green = new CircularArrayList(countGreen);
        greenBlueCount = new int[countGreen];
        blueBlueCount = new int[countBlue];
        greenGreenCount = new int[countGreen];
        TB = new int[countBlue];
        TG = new int[countGreen];
    }

    public void split() throws Exception {
        Point2D temp;
        int t;
        for (int i = 1; i < sortedAstar.size(); i++) { //not include the centroid!
            temp = sortedAstar.get(i);
            switch (temp.color.hashCode()) {
                case -65536: //red
                    Red.append(new Red(temp, i));
                    temp.changeIndex(Red.getTail() - 1);
                    break;
                case -16776961: //blue
                    t = i + 1; //first find nextGreen
                    while (sortedAstar.get(t).color != Color.GREEN) {
                        t++;
                        if (sortedAstar.get(t).color == Color.RED) {
                            blueGreenCount[Blue.getTail()]++;
                        }
                    }
                    Blue.append(new Blue(temp, i, sortedAstar.wrapIndex(t)));
                    temp.changeIndex(Blue.getTail() - 1);
                    break;
                case -16711936: //green
                    t = i + 1;
                    while (sortedAstar.get(t).color != Color.BLUE) { //looking for nextBlue
                        t++;
                        if (sortedAstar.get(t).color == Color.RED) {
                            greenBlueCount[Green.getTail()]++;
                        }
                    }
                    Green.append(new Green(temp, i, sortedAstar.wrapIndex(t)));
                    temp.changeIndex(Green.getTail() - 1);
                    break;
            }
        }
    }

    public void printG() {
        for (int i = Green.getHead(); i < Green.getTail(); i++) {
            Green temp = Green.get(i);
            System.out.print(temp.pointer + ")  " + temp.x + " " + temp.y + " " + temp.color.toString() + " next - " + temp.nextBlue + " semiCircle - " + temp.semiCircle + "\n");
        }
        System.out.println();
    }

    public void printB() {
        for (int i = Blue.getHead(); i < Blue.getTail(); i++) {
            Blue temp = Blue.get(i);
            System.out.print(temp.pointer + ")  " + temp.x + " " + temp.y + " " + temp.color.toString() + " next - " + temp.nextGreen + " semiCircle - " + temp.semiCircle + "\n");
        }
        System.out.println();
    }

    public void findSemiCircles() {
        for (int i = 0; i < Blue.size(); i++) {
            Blue temp = Blue.get(i);
            temp.semiCircle = findSemiCircleForBlue(temp, sortedAstar.get(temp.nextGreen).index, Green.wrapIndex(sortedAstar.get(temp.nextGreen).index - 1), Green.size());
        }
        for (int i = 0; i < Green.size(); i++) {
            Green temp = Green.get(i);
            temp.semiCircle = findSemiCircleForGreen(temp, sortedAstar.get(temp.nextBlue).index, Blue.wrapIndex(sortedAstar.get(temp.nextBlue).index - 1), Blue.size());
        }
        System.out.println("Green:");
        printG();
        System.out.println("Blue:");
        printB();

    }

    private int findSemiCircleForBlue(Point2D bluePoint, int start, int end, int total) {
        if (start == end) {
            return -1; //doesn't exist
        }
        int mid = total / 2;
        if (sortedAstar.get(0).angleBetween1(bluePoint, Green.get(start + mid)) < 180) {
            if (sortedAstar.get(0).angleBetween1(bluePoint, Green.get(start + mid + 1)) >= 180 || sortedAstar.get(0).angleBetween1(bluePoint, Green.get(start + mid + 1)) < sortedAstar.get(0).angleBetween1(bluePoint, Green.get(start + mid))) { //found or has gone the full circle
                return Green.wrapIndex(start + mid);
            } else {
                return findSemiCircleForBlue(bluePoint, start + mid, end, total - mid);
            }
        } else {
            if (sortedAstar.get(0).angleBetween1(bluePoint, Green.get(start + mid - 1)) < 180) { //found
                return Green.wrapIndex(start + mid - 1);
            } else {
                return findSemiCircleForBlue(bluePoint, start, start + mid, mid);
            }

        }
    }

    private int findSemiCircleForGreen(Point2D greenPoint, int start, int end, int total) {
        if (start == end) {
            return -1; //doesn't exist
        }
        int mid = total / 2;
        if (sortedAstar.get(0).angleBetween1(greenPoint, Blue.get(start + mid)) < 180) {
            if (sortedAstar.get(0).angleBetween1(greenPoint, Blue.get(start + mid + 1)) >= 180
                    || sortedAstar.get(0).angleBetween1(greenPoint, Blue.get(start + mid + 1)) < sortedAstar.get(0).angleBetween1(greenPoint, Blue.get(start + mid))) { //found or has gone the full circle
                return Blue.wrapIndex(start + mid);
            } else {
                return findSemiCircleForGreen(greenPoint, start + mid, end, total - mid);
            }
        } else {
            if (sortedAstar.get(0).angleBetween1(greenPoint, Blue.get(start + mid - 1)) < 180) { //found
                return Blue.wrapIndex(start + mid - 1);
            } else {
                return findSemiCircleForGreen(greenPoint, start, start + mid, mid);
            }

        }
    }

    public void countRedPointsInIntervals() {
        countRedBetweenBlue();
        countRedBetweenGreen();
        System.out.println("Between Blues:");
        for (int i = 0; i < blueBlueCount.length; i++) {
            System.out.println(i + ") " + blueBlueCount[i]);
        }
        System.out.println("Between Greens:");
        for (int i = 0; i < greenGreenCount.length; i++) {
            System.out.println(i + ") " + greenGreenCount[i]);
        }
    }

    private void countRedBetweenBlue() {
        for (int i = 0; i < Blue.size(); i++) {
            int p = Blue.get(i).pointer + 1;
            while (sortedAstar.get(p).color != Color.BLUE) { //next point in the Blue array
                if (sortedAstar.get(p).color == Color.RED) {
                    blueBlueCount[i]++;
                }
                p++;
            }
        }
    }

    private void countRedBetweenGreen() {
        for (int i = 0; i < Green.size(); i++) {
            int p = Green.get(i).pointer + 1;
            while (sortedAstar.get(p).color != Color.GREEN) { //next point in the Green array
                if (sortedAstar.get(p).color == Color.RED) {
                    greenGreenCount[i]++;
                }
                p++;
            }
        }
    }

    public void countTriangles() {
        int next, semi;
        for (int i = 0; i < Blue.size(); i++) {
            TB[i] = blueGreenCount[i];
            T += TB[i];
            if (Blue.get(i).semiCircle != -1) { //no Green points in this blue point's semicircle
                next = sortedAstar.get(Blue.get(i).nextGreen).index;
                semi = Blue.get(i).semiCircle;
                if (next <= semi) {
                    for (int j = next; j < semi; j++) { //iterate through Green
                        TB[i] += (semi - j) * greenGreenCount[j];
                        T += TB[i];
                    }
                } else { //semi < next
                    int totalDifference = Green.size() - next + semi;
                    for (int j = 0; j < totalDifference; j++) {
                        TB[i] += (totalDifference - j) * greenGreenCount[Green.wrapIndex(semi + j)];
                        T += TB[i];
                    }
                }
            }
        }
        for (int i = 0; i < Green.size(); i++) {
            TG[i] = greenBlueCount[i];
            T += TG[i];
            if (Green.get(i).semiCircle != -1) {
                next = sortedAstar.get(Green.get(i).nextBlue).index;
                semi = Green.get(i).semiCircle;
                if (next <= semi) {
                    for (int j = next; j < semi; j++) { //iterate through Blue
                        TB[i] += (semi - j) * blueBlueCount[j];
                        T += TB[i];
                    }
                } else { //semi < next
                    int totalDifference = Blue.size() - next + semi;
                    for (int j = 0; j < totalDifference; j++) {
                        TB[i] += (totalDifference - j) * blueBlueCount[Blue.wrapIndex(semi + j)];
                        T += TB[i];
                    }
                }
            }
        }
        System.out.println(T);
    }
}
