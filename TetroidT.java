/**********************************************************************************
 *
 *  This block is complete: a good reference for other blocks!
 *              
 **********************************************************************************/
import java.awt.Color;

public class TetroidT extends Shape {
    private double[] x;         // x position for blocks
    private double[] y;         // y position for blocks
    private int rotation;       // 4 phases total
    private static final int phases = 4;
    private static final Color C = StdDraw.MAGENTA;  // this tetroid color

    //******************************************************************
    //  CONSTRUCTORS
    //*******************************************************************/

    public TetroidT(double x, double y) {
        this.x = new double[4];
        this.y = new double[4];
        rotation = 0;
        hover(x, y);
    }

    //******************************************************************
    //  MUTATORS
    //*******************************************************************/

   public void hover(double x, double y) {
        switch (rotation) {
            case 0:
                this.x[0] = x;
                this.y[0] = y;
                
                this.x[1] = x - 1;
                this.y[1] = y;
                
                this.x[2] = x + 1;
                this.y[2] = y;
                
                this.x[3] = x;
                this.y[3] = y + 1;
                break;
            case 1:
                this.x[0] = x;
                this.y[0] = y;
                
                this.x[1] = x;
                this.y[1] = y + 1;
                
                this.x[2] = x;
                this.y[2] = y - 1;
                
                this.x[3] = x + 1;
                this.y[3] = y;
                break;
            case 2: 
                this.x[0] = x;
                this.y[0] = y;
                
                this.x[1] = x - 1;
                this.y[1] = y;
                
                this.x[2] = x + 1;
                this.y[2] = y;
                
                this.x[3] = x;
                this.y[3] = y - 1;
                break;
            case 3:
                this.x[0] = x;
                this.y[0] = y;
                
                this.x[1] = x;
                this.y[1] = y + 1;
                
                this.x[2] = x;
                this.y[2] = y - 1;
                
                this.x[3] = x - 1;
                this.y[3] = y;
                break;
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
