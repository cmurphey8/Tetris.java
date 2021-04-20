/**********************************************************************************
 *
 *  A class to store all placed box in one object -> Nothing to do here!
 *              
 **********************************************************************************/
import java.awt.Color;

public class TetraSet {
    protected static int gridX;
    protected static int gridY;
    protected static Color[][] superC;     // yx Color for squares

    //******************************************************************
    //  CONSTRUCTORS
    //*******************************************************************/

    public TetraSet(int x, int y) {
        gridX = x;
        gridY = y; 
        superC = new Color[gridY][gridX];
    }

    public TetraSet() {}

    //******************************************************************
    //  MUTATORS
    //*******************************************************************/

    public void update(Shape Tetroid) {
        // gather Tetroid data
        double[] xT = Tetroid.getX();
        double[] yT = Tetroid.getY();
        Color cT = Tetroid.getC();

        // update background with this set piece
        for (int i = 0; i < 4; i++) {
            superC[(int) yT[i]][(int) xT[i]] = cT;
        }

        // reduce if blocks span the grid
        reduce();

        // draw all remaining blocks on the grid
        draw();
    }

    private void reduce() {
        // eliminate all blocks in a row if the row is full
        for (int i = 0; i < gridY; i++)
            if (fullRow(i))
                for (int j = 0; j < gridX; j++)
                    superC[i][j] = null;         
    } 
    
    private boolean fullRow(int i) {
        for (int j = 0; j < gridX; j++)
            if (superC[i][j] == null)
                return false;
        return true;
    }    

    //******************************************************************
    //  ACCESSORS
    //*******************************************************************/

    public void draw() {
        for (int i = 0; i < gridY; i++)
            for (int j = 0; j < gridX; j++)    
                if (superC[i][j] != null) drawSquare(i, j); 
    }

    public void drawSquare(int y, int x) {
        StdDraw.setPenColor(superC[y][x]);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5); 

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.square(x + 0.5, y + 0.5, 0.5); 

        StdDraw.line(x, y, x+1, y+1);
        StdDraw.line(x, y+1, x+1, y);
        
        StdDraw.setPenColor(superC[y][x]);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.3);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.square(x + 0.5, y + 0.5, 0.3);   
    }
}
