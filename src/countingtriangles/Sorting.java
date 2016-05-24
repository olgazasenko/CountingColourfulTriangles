/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package countingtriangles;

import java.util.Arrays;

/**
 *
 * @author Olga
 */
public class Sorting {
    Point2D input[];
    CircularArrayList<Point2D> output;
    
    Sorting(CircularArrayList<Point2D> A){
        input = A.toArray(new Point2D[A.size()]);
    }
    
    public CircularArrayList sortByPolarAngle(){
        Arrays.sort(input, 1, input.length, input[0].POLAR_ORDER);
        System.out.println("Sorted A*:");
        for (int i=0; i<input.length; i++)
            System.out.print(i +")  " + input[i].x+" "+input[i].y+" "+input[i].color.toString()+"\n");
        output = new CircularArrayList(input);
        return output;
    }
    
}
