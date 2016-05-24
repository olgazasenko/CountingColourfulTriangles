/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package countingtriangles;
  
import java.util.*;
  
/**
 * If you use this code, please retain this comment block.
 * @author Isak du Preez
 * isak at du-preez dot com
 * www.du-preez.com
 */
public class CircularArrayList<Point2D>
        extends AbstractList<Point2D> implements RandomAccess {
  
    private final int n; // buffer length
    private final List<Point2D> buf; // a List implementing RandomAccess
    private int head = 0;
    private int tail = 0;
  
    public CircularArrayList(int capacity) {
        n = capacity + 1;
        buf = new ArrayList<>(Collections.nCopies(n, (Point2D) null));
    }
    
    public CircularArrayList(Point2D[] input){
        n = input.length + 1;
        buf = new ArrayList<>(Collections.nCopies(n, (Point2D) null));
        for (int i=0; i<input.length; i++){
            this.append( input[i]);
        }
    }
  
    public int capacity() {
        return n - 1;
    }
  
    public int wrapIndex(int i) {
        int m = i % (n - 1);
        if (m < 0) { // java modulus can be negative
            m += (n - 1);
        }
        return m;
    }
  
    // This method is O(n) but will never be called if the
    // CircularArrayList is used in its typical/intended role.
    private void shiftBlock(int startIndex, int endIndex) {
        assert (endIndex > startIndex);
        for (int i = endIndex - 1; i >= startIndex; i--) {
            set(i + 1, get(i));
        }
    }
  
    @Override
    public int size() {
        return tail - head + (tail < head ? n : 0);
    }
  
    @Override
    public Point2D get(int i) {
        return buf.get(wrapIndex(head + i ));
    }
  
    @Override
    public Point2D set(int i, Point2D e) {
        if (i < 0 || i > size()) {
            throw new IndexOutOfBoundsException();
        }
        return buf.set(wrapIndex(head + i), e);
    }
  
    public void append(Point2D e) {
        int s = size();
        if (s == n - 1) {
            throw new IllegalStateException("Cannot add element."
                    + " CircularArrayList is filled to capacity.");
        }
        set(tail, e);
        tail = tail + 1; //wrapIndex(tail + 1);
    }
  
    @Override
    public Point2D remove(int i) {
        int s = size();
        if (i < 0 || i >= s) {
            throw new IndexOutOfBoundsException();
        }
        Point2D e = get(i);
        if (i > 0) {
            shiftBlock(0, i);
        }
        head = wrapIndex(head + 1);
        return e;
    }
    
    public int getHead(){
        return this.head;
    }
    
    public int getTail(){
        return this.tail;
    }
   
}
