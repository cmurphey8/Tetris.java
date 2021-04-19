import java.time.Clock;

/**************************************************************************************************
 *  Compilation:  javac Tetra.java
 *  Execution:    java Tetra
 *  Dependencies: StdDraw.java Shape.java Tetroid*.java
 * 
 *  Usage:  Pick up a copy of one of the tetroid templates by clicking on it;
 *          Rotate a picked up object with space bar;
 *          Set a picked up object by clicking in the black and grey grid;
 * 
 *  PART 1: Update all remaining Tetroid*.java files (L, T, Z) so that all tetroids
 *          initialize and rotate properly
 *
 *  PART 2: Complete the overLaps method in Shape.java to prevent blocks 
 *          from stacking on top of one another
 * 
 *  DISCUSSION: Complete the form @ 
 *              (1) What is the significance of our abstract parent class not having a constructor?
 * 
 *              (2) What is the significance of the abstract methods in our parent class?
 * 
 *              (3) What do you notice about the roles of our parent and child classes
 *                  for this problem compared to the Galaxy problem last week?
 * 
 *              (4) What are some conditions to look out for where an abstract parent class
 *                  might be preferable to a standard parent class?
 * 
 *  EXTRA PRACTICE: Remove the abstract keyword from the Shape parent class 
 *                  and Redesign the program around this update
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

            // process key entries if objct IS selected
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

            // follow the mouse while an object is selected
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

    // process mouse clicks if NO object is selected
    public static boolean unselected() {
        blob.update(tetroid);
        tetroid = null;
        return true;
    }

    // init a new block IF no block in play
    public static boolean select(int k) {
        // init a new block of type k, centered at the top of the grid
        initNew(k, gridX / 2, gridY - 2);
        StdDraw.show();
        return false;
    }

    public static int checkTemplates(double x, double y) {
        // check if a template was clicked on
        int k = -1;
        for (int i = 0; i < templates.length; i++) {
            if (templates[i].clicked(Math.floor(x), Math.floor(y))) {
                k = i;
            }
        }
        return k;
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