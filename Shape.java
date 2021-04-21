/**********************************************************************************
 *
 *  Our Shape class is complete -> Nothing to do here!
 *              
 **********************************************************************************/

import java.awt.Color;

// abstract classes do not get constructors!
public abstract class Shape extends TetraSet {

    private double[] x;
    private double[] y;
    private Color C;
    
    public void draw() {
        x = getX();
        y = getY();
        for (int i = 0; i < 4; i++) {
            drawSquare(x[i], y[i]);           
        }
    }

    public void drawSquare(double x, double y) {
        C = getC();
        StdDraw.setPenColor(C);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5); 

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.square(x + 0.5, y + 0.5, 0.5); 

        StdDraw.line(x, y, x+1, y+1);
        StdDraw.line(x, y+1, x+1, y);
        
        StdDraw.setPenColor(C);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.3);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.square(x + 0.5, y + 0.5, 0.3);   
    }

    public boolean overLaps(double x0, double y0) {
        // update x0, y0 of tetroid
        hover(x0, y0);

        // gather elements to compare
        x = getX();
        y = getY();

        // return true if tetroid indexes occupied in the blob object of set pieces
        for (int i = 0; i < 4; i++)
            if (superC[(int) y[i]][(int) x[i]] != null) 
                return true;

        // else return false                         
        return false;
    }

    public boolean inBounds(double x0, double y0) {
        // update x0, y0 of tetroid
        hover(x0, y0);

        // gather elements to compare
        x = getX();
        y = getY();

        // return false if tetroid indexes are outside of our grid
        for (int i = 0; i < 4; i++)
            if (x[i] >= gridX || x[i] < 0 || y[i] >= gridY || y[i] < 0) 
                return false;        

        // else return true
        return true;
    }

    public boolean clicked(double testX, double testY) {
        // gather elements to compare
        x = getX();
        y = getY();

        // return true if this tetroid was clicked on
        for (int i = 0; i < 4; i++){
            if ((int) x[i] == testX && (int) y[i] == testY) 
                return true;
        }
                    
        // else return false
        return false;
    }

    public void slam() {
        while (drop()) {}
    }

    public boolean drop() {
        x = getX();
        y = getY();
        int yTest = (int) y[0] - 1;
        if (inBounds(x[0], yTest)) {
            if (overLaps(x[0], yTest)){
                hover(x[0], y[0] + 1);
                return false;
            }    
        }
        else {
            hover(x[0], y[0] + 1);
            return false;
        } 
        return true;
    }

    public void move(int xDir) {
        x = getX();
        y = getY();
        int xTest = (int) x[0] + xDir;
        if (inBounds(xTest, y[0])) {
            if (overLaps(xTest, y[0])){
                hover(x[0] - xDir, y[0]);
            }    
        }
        else hover(x[0] - xDir, y[0]);
    }

    public void rotateSuper() {
        int tmp = getRotation();

        rotate();
        hover(x[0], y[0]);
        x = getX();
        y = getY();
        if (inBounds(x[0], y[0])) {
            if (overLaps(x[0], y[0])){
                while(getRotation() != tmp)
                    rotate();
                hover(x[0], y[0]);
            }
            return;    
        }
        while(getRotation() != tmp)
            rotate();
        hover(x[0], y[0]);
    }

    public abstract void hover(double x, double y);
    public abstract double[] getX();
    public abstract double[] getY();
    public abstract Color getC();
    public abstract int getRotation();
    public abstract void rotate();
}
