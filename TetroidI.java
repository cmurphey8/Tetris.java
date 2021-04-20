/**********************************************************************************
 *
 *  This block is complete: a good reference for other blocks!
 *              
 **********************************************************************************/
import java.awt.Color;

public class TetroidI extends Shape {
    private double[] x;         // x position for blocks
    private double[] y;         // y position for blocks
    private int rotation;       // 2 phases total
    private static final int phases = 2;
    private static final Color C = StdDraw.CYAN;     // this tetroid color

    //******************************************************************
    //  CONSTRUCTORS
    //*******************************************************************/

    public TetroidI(double x, double y) {
        this.x = new double[4];
        this.y = new double[4];
        rotation = 0;
        hover(x, y);
    }

    //******************************************************************
    //  MUTATORS
    //*******************************************************************/

    public void hover(double x, double y) {
        if (rotation == 0) {
            for (int i = 0; i < 4; i++) {
                this.x[i] = x + i;
                this.y[i] = y;                    
            }
            // swap(0, 1);
        }
        else {
            for (int i = 0; i < 4; i++) {
                this.x[i] = x;
                this.y[i] = y + i;    
            }
            // swap(0,1);
        }
    }

    public void rotate() {
        rotation = (rotation + 1) % phases;
    }

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
        return C;
    }

    public int getRotation() { 
        return this.rotation;
    }
}
