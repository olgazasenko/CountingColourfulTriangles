/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package countingtriangles;

import java.applet.Applet;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import org.math.plot.Plot2DPanel;

/**
 *
 * @author Olga
 */
public  class Paint extends Applet{
    Graphics2D g2;
    CircularArrayList<Red> Red;
    CircularArrayList<Blue> Blue;
    CircularArrayList<Green> Green;
    CircularArrayList<Point2D> sortedAstar;
    Plot2DPanel plot;
    
    
    Paint(CircularArrayList<Red> Red, CircularArrayList<Blue> Blue, CircularArrayList<Green> Green,  CircularArrayList<Point2D> sortedAstar){
        this.Red = Red;
        this.Blue = Blue;
        this.Green = Green;
        this.sortedAstar = sortedAstar;
        
        JFrame frame = new JFrame("a plot panel");
        frame.setSize(700, 700);
        plot = new Plot2DPanel();
        plot.setFixedBounds(0, -5, 15);
        plot.setFixedBounds(1, -5, 30);
        frame.setContentPane(plot);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void paint(){
         paintPoints();
         
    }
    
    private void paintPoints(){
        double redsReflected[][] = new double[2][Red.size()];
        double redsOriginal[][] = new double[2][Red.size()];
        double blues[][] = new double[2][Blue.size()];
        double greens[][] = new double[2][Green.size()];
        double centroid[][] = {{0},{0}};
        for (int i = 0; i<Red.size(); i++){
            redsReflected[0][i] = Red.get(i).x;
            redsReflected[1][i] = Red.get(i).y;
            redsOriginal[0][i] = 2*sortedAstar.get(0).x - Red.get(i).x;
            redsOriginal[1][i] = 2*sortedAstar.get(0).y - Red.get(i).y;
        }
        plot.addScatterPlot("plot", Color.RED, redsReflected);
        plot.addScatterPlot("plot", Color.ORANGE, redsOriginal);
        for (int i = 0; i<Blue.size(); i++){
            blues[0][i] = Blue.get(i).x;
            blues[1][i] = Blue.get(i).y;
        }
        plot.addScatterPlot("plot", Color.BLUE, blues);
        for (int i = 0; i<Green.size(); i++){
            greens[0][i] = Green.get(i).x;
            greens[1][i] = Green.get(i).y;
        }
        plot.addScatterPlot("plot", Color.GREEN, greens);
        plot.addScatterPlot("plot", Color.BLACK, centroid);
    }
}
