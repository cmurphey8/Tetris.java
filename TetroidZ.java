/**********************************************************************************
 *
 *  This block is complete: a good reference for other blocks!
 *              
 **********************************************************************************/
import java.awt.Color;

public class TetroidZ extends Shape {
    private double[] x;         // x position for blocks
    private double[] y;         // y position for blocks
    private int rotation;       // 2 phases total
    private static final int phases = 2;
    private static final Color C = StdDraw.RED;      // this tetroid color

    //******************************************************************
    //  CONSTRUCTORS
    //*******************************************************************/

    public TetroidZ(double x, double y) {
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
            for (int i = 0; i < 2; i++) {
                this.x[i] = x + i;
                this.y[i] = y;  
                this.x[i + 2] = x + 1 + i;
                this.y[i + 2] = y - 1;  
            }
        }
        else {
            for (int i = 0; i < 2; i++) {
                this.x[i] = x;
                this.y[i] = y + i;  
                this.x[i + 2] = x + 1;
                this.y[i + 2] = y + 1 + i;  
            }
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