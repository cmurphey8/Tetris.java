/**********************************************************************************
 *
 *  This block is complete: a good reference for other blocks!
 *              
 **********************************************************************************/
import java.awt.Color;

public class TetroidO extends Shape {
    private double[] x;         // x position for blocks
    private double[] y;         // y position for blocks
    private int rotation = 1;
    private final Color C = StdDraw.YELLOW;   // this tetroid color
    // no rotation needed for this block

    //******************************************************************
    //  CONSTRUCTORS
    //*******************************************************************/

    public TetroidO(double x, double y) {
        this.x = new double[4];
        this.y = new double[4];
        hover(x, y);
    }

    public void hover(double x, double y) {
        for (int i = 0; i < 2; i++) {
            this.x[i] = x + i;
            this.y[i] = y;    
            this.x[i + 2] = x + i;
            this.y[i + 2] = y + 1;  
        }
    }

    public void rotate() {}

    //******************************************************************
    //  ACCESSORS
    //*******************************************************************/

    public double[] getX() { 
        return this.x;
    }
    
    public double[] getY() { 
        return this.y;
    }

    public Color getC(){
        return this.C;
    }
    
    public int getRotation() { 
        return this.rotation;
    }
}
