import java.time.Clock;

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
 *  EXTRA PRACTICE: provide a next block preview via background highlighting of the block that's up next
 * 
 **************************************************************************************************/

public class Tetra { 
    public static int gridX = 10;
    public static int gridY = 20;
    public static int nextBox = 5; 
    public static int buffer = 1;
    public static int scale = 20;

    public static int DELAY = 10;

    // public static Shape[] tetroids;
    public static Shape tetroid;
    public static Shape[] templates;
    public static TetraSet blob = new TetraSet(gridX, gridY);

    public static void main(String[] args) {    
        templates = new Shape[7];
        
        // initialize StdDraw elements
        StdDraw.enableDoubleBuffering();
        // StdDraw.setCanvasSize(500, 600);
        StdDraw.setCanvasSize(scale * (gridX + nextBox + 3 * buffer), scale * (gridY + 2 * buffer));
        StdDraw.setXscale(-buffer, gridX + nextBox + 2 * buffer);
        StdDraw.setYscale(-buffer, gridY + buffer);

        initTemplates();
        drawBackground();
        StdDraw.show();

        boolean released = true;
        int k = 0;
        long time0 = System.currentTimeMillis();
        while (true) {
            long timeDelta = System.currentTimeMillis() - time0;
            // process mouse clicks if NO object is selected
            if (released) {
                released = select(k);
                k = (k + 1) % 7;
            }

            // process key entries if object IS selected
            if (StdDraw.hasNextKeyTyped() && !released) {
                switch (StdDraw.nextKeyTyped()) {
                    case ' ':    // space bar >> slam down
                        tetroid.slam(blob, gridX, gridY);
                        break;
                    case 'a':    // a >> move left
                        tetroid.move(-1, blob, gridX, gridY);
                        break;
                    case 'w':    // w >> rotate
                        tetroid.rotate(gridX, gridY);
                        break;
                    case 'd':    // d >> move right
                        tetroid.move(1, blob, gridX, gridY);
                        break;
                    case 's':    // s >> speed down
                        tetroid.drop(blob, gridX, gridY);
                        break;
                }
            }

            // if block IN play: drop on-time and release if floored
            if (!released) {
                drawBackground(); 
                tetroid.draw();
                if (timeDelta > 500) {
                    if (!tetroid.drop(blob, gridX, gridY)) {
                        released = unselected();
                    }
                    time0 = System.currentTimeMillis();
                }
                StdDraw.show(); 
                StdDraw.pause(DELAY);
            }
        } // end of while
    } 

    // reset block if floored
    public static boolean unselected() {
        blob.update(tetroid);
        tetroid = null;
        return true;
    }

    // reinit block IF no block in play
    public static boolean select(int k) {
        // init a new block of type k, centered at the top of the grid
        initNew(k, gridX / 2, gridY - 2);
        StdDraw.show();
        return false;
    }

    public static void initNew(int k, double x, double y) {
        // add a new block to the tetroids array by the template type identified
        switch (k) {
            case 0: 
                tetroid = new TetroidI(x, y);
                break;
            case 1: 
                tetroid = new TetroidJ(x, y);
                break;
            case 2: 
                tetroid = new TetroidL(x, y);
                break;
            case 3: 
                tetroid = new TetroidO(x, y);
                break;
            case 4: 
                tetroid = new TetroidS(x, y);
                break;
            case 5: 
                tetroid = new TetroidT(x, y);
                break;
            case 6:
                tetroid = new TetroidZ(x, y);
                break;
        }
    }

    public static void initTemplates() {
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
    public static void drawBackground() {
        drawGameBoard();
        drawNext();
        blob.draw();
    }

    public static void drawGameBoard() {
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

    public static void drawNext() {
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
