/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package countingtriangles;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olga
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        ReadData data = new ReadData();
        data.read();
        CircularArrayList A = data.A;
        CircularArrayList Astar = data.Astar;
        CircularArrayList sortedAstar = new Sorting(Astar).sortByPolarAngle();
        SplitAndCount split = new SplitAndCount(sortedAstar, data.countRed, data.countBlue, data.countGreen);
        try {
            split.split();
        } catch (Exception ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        split.findSemiCircles();
        split.countRedPointsInIntervals();
        split.countTriangles();
        Paint paint = new Paint(split.Red, split.Blue, split.Green, split.sortedAstar);
        paint.paint();
    }

}
