/**************************************************************************************************
 *  Compilation:  javac Tetra.java
 *  Execution:    java Tetra
 *  Dependencies: StdDraw.java Shape.java Tetroid*.java
 * 
 *  Usage:  Move blocks with keys: a (left), a (right), s (down), w (rotate), space (slam down)
 * 
 *  PART 1: Complete the TetraSet Class -> complete the reduce() method
 *
 *  PART 2: Select next block at random instead of in order
 *
 *  EXTRA PRACTICE: Find a suitable end-game procedure if a column cannot be filled any higher
 * 
 **************************************************************************************************/

public class Display { 
    public int gridX;
    public int gridY;

    public final int nextBox = 5; 
    public final int buffer = 1;
    public final int scale = 20;

    public Shape[] templates;

    public Display(int gridX, int gridY, TetraSet blob) {
        this.gridX = gridX;
        this.gridY = gridY;    
        templates = new Shape[7];
        
        // initialize StdDraw elements
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(scale * (gridX + nextBox + 3 * buffer), scale * (gridY + 2 * buffer));
        StdDraw.setXscale(-buffer, gridX + nextBox + 2 * buffer);
        StdDraw.setYscale(-buffer, gridY + buffer);

        init(blob);
    }

    public void init(TetraSet blob) {
        initTemplates();
        drawBackground(blob);
        StdDraw.show();
    }

    public void initTemplates() {
        // initialize the templates array with all our tetroid block shapes
        templates[0] = new TetroidI(gridX + buffer * 1.5, gridY - buffer * 3 / 2.0);
        templates[1] = new TetroidJ(gridX + buffer * 2, gridY - 4.5 * buffer);
        templates[2] = new TetroidL(gridX + buffer * 2, gridY - 7.5 * buffer);
        templates[3] = new TetroidO(gridX + buffer * 2.5, gridY - 10.5 * buffer);
        templates[4] = new TetroidS(gridX + buffer * 2, gridY - 13.5 * buffer);
        templates[5] = new TetroidT(gridX + buffer * 2, gridY - 16.5 * buffer);
        templates[6] = new TetroidZ(gridX + buffer * 2, gridY - 18.5 * buffer);
    }

    // draw the frame, templates, and all existing tetroid blocks
    public void drawBackground(TetraSet blob) {
        drawGameBoard();
        drawNext();
        blob.draw();
    }

    public void drawGameBoard() {
        // canvas
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle((gridX + nextBox + 2.0 * buffer) / 2.0, (gridY + buffer) / 2.0, 
                                (gridX + nextBox + 2.0 * buffer), (gridY + 2.0 * buffer) / 2.0);
        
        // grid border
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.filledRectangle(gridX / 2.0, gridY / 2.0, 
                                gridX / 2.0 + buffer / 4.0, gridY / 2.0  + buffer / 4.0);

        // grid background
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(gridX / 2.0, gridY / 2.0, gridX / 2.0, gridY / 2.0);

        // grid
        StdDraw.setPenColor(StdDraw.GRAY);
        for (int i = 0; i < gridX; i++) {
            for (int j = 0; j < gridY; j++) {
                StdDraw.square(i + 0.5, j + 0.5, 0.5);
            }
        }
    }

    public void drawNext() {
        // next border
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.filledRectangle(gridX + buffer + nextBox / 2.0, gridY / 2.0, 
                                nextBox / 2.0 + buffer / 4.0, gridY / 2.0  + buffer / 4.0);

        // next background
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(gridX + buffer + nextBox / 2.0, gridY / 2.0, 
                                nextBox / 2.0, gridY / 2.0);

        // templates
        for (int i = 0; i < templates.length; i++) {
            templates[i].draw();
        }
    }
} 