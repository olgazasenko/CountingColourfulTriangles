/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package countingtriangles;

/**
 *
 * @author Olga
 */
public class Blue extends Point2D{
    int nextGreen; //in the general array
    int semiCircle; //green, in the green array
    
    Blue(Point2D p, int pointer, int nextGreen){
        this.x = p.x;
        this.y = p.y;
        this.color = p.color;
        this.pointer = pointer;
        this.nextGreen = nextGreen;
    }
    
    
}
