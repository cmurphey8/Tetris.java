/**********************************************************************************
 *
 *  A class to store all placed box in one object -> Nothing to do here!
 *              
 **********************************************************************************/
import java.awt.Color;

public class TetraSet {
    private int gridX;
    private int gridY;
    private Color[][] C;     // yx Color for squares

    //******************************************************************
    //  CONSTRUCTORS
    //*******************************************************************/

    public TetraSet(int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY; 
        C = new Color[gridY][gridX];
    }

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
            C[(int) yT[i]][(int) xT[i]] = cT;
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
                    C[i][j] = null;         
    } 
    
    private boolean fullRow(int i) {
        for (int j = 0; j < gridX; j++)
            if (C[i][j] == null)
                return false;
        return true;
    }    

    //******************************************************************
    //  ACCESSORS
    //*******************************************************************/

    public void draw() {
        for (int i = 0; i < gridY; i++)
            for (int j = 0; j < gridX; j++)    
                if (C[i][j] != null) drawSquare(i, j); 
    }

    public void drawSquare(int y, int x) {
        StdDraw.setPenColor(C[y][x]);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5); 

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.square(x + 0.5, y + 0.5, 0.5); 

        StdDraw.line(x, y, x+1, y+1);
        StdDraw.line(x, y+1, x+1, y);
        
        StdDraw.setPenColor(C[y][x]);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.3);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.square(x + 0.5, y + 0.5, 0.3);   
    }

    public Color[][] getC() {
        return C;
    }
}
