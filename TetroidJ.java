/**********************************************************************************
 *
 *  This block is complete: a good reference for other blocks!
 *              
 **********************************************************************************/
import java.awt.Color;

public class TetroidJ extends Shape {
    private double[] x;         // x position for blocks
    private double[] y;         // y position for blocks
    private int rotation;       // 4 phases total
    private static final int phases = 4;
    private static final Color C = StdDraw.BLUE;     // this tetroid color

    //******************************************************************
    //  CONSTRUCTORS
    //*******************************************************************/

    public TetroidJ(double x, double y) {
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
                for (int i = 0; i < 3; i++) {
                    this.x[i] = x + i;
                    this.y[i] = y;    
                }
                this.x[3] = x;
                this.y[3] = y + 1;
                break;
            case 1:
                for (int i = 0; i < 3; i++) {
                    this.x[i] = x;
                    this.y[i] = y - i;    
                }
                this.x[3] = x + 1;
                this.y[3] = y;
                break;
            case 2: 
                for (int i = 0; i < 3; i++) {
                    this.x[i] = x - i;
                    this.y[i] = y;    
                }
                this.x[3] = x;
                this.y[3] = y - 1;
                break;
            case 3:
                for (int i = 0; i < 3; i++) {
                    this.x[i] = x;
                    this.y[i] = y + i;    
                }
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
