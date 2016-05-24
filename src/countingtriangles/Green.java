/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package countingtriangles;

/**
 *
 * @author Olga
 */
public class Green extends Point2D{
    int nextBlue;
    int semiCircle; //blue
    
    Green(Point2D p, int pointer, int nextGreen){
        this.x = p.x;
        this.y = p.y;
        this.color = p.color;
        this.pointer = pointer;
        this.nextBlue = nextGreen;//index in sortedAstar
    }
    
}
