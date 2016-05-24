/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package countingtriangles;

/**
 *
 * @author Olga
 */
public class Red extends Point2D{
    
    Red(Point2D p, int pointer){
        this.x = p.x;
        this.y = p.y;
        this.color = p.color;
        this.pointer = pointer;
    }
    
}
