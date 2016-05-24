/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package countingtriangles;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author Olga
 */
public class ReadData {
    CircularArrayList<Point2D> A;
    CircularArrayList<Point2D> Astar;
    int countRed = 0;
    int countGreen = 0;
    int countBlue = 0;
    int size = 0;
    
    ReadData(){
        A = new CircularArrayList<>(100);
    }
    
    void read() throws FileNotFoundException{
        Scanner in = new Scanner(new FileReader("E:\\Documents\\NetBeansProjects\\CountingTriangles\\src\\datapoins1.txt"));
        while (in.hasNext()){
           A.append(new Point2D(in.nextDouble(), in.nextDouble(),  decode(in.next())));
           size++;
        }
        System.out.println("Original array:");
        print(A);
        reflect();
        System.out.println("Reflected array:");
        print(Astar);
    }
    
    Color decode(String s){
        switch(s){
            case "red": countRed++; return Color.RED;
            case "blue": countBlue++; return Color.BLUE;
            case "green": countGreen++; return Color.GREEN;            
        }
        System.out.println("Color misspelled.");
        return Color.BLACK;
    }
    
    public void print(CircularArrayList A){
        for (int i=A.getHead(); i<A.getTail(); i++){
            Point2D temp = (Point2D) A.get(i);
            System.out.print(i+")  "+temp.x+" "+temp.y+" "+temp.color.toString()+"\n");
        }
        System.out.println();
    }
    
    public void reflect(){
        Astar = new CircularArrayList<>(size);
        double x, y, x0, y0;
        Color c;
        x0 = A.get(0).x;
        y0 = A.get(0).y;
        for (int i = 0; i<A.size(); i++){
            x = A.get(i).x;
            y = A.get(i).y;
            c = A.get(i).color;
            if (c == Color.RED){
              Astar.append(new Point2D(2*x0 - x, 2*y0 - y, c)); 
           } else{
               Astar.append(new Point2D(x, y, c));
           }
        }
    }
}
