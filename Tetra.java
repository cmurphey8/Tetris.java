/**************************************************************************************************
 *  Compilation:  javac Tetra.java
 *  Execution:    java Tetra
 *  Dependencies: StdDraw.java Shape.java Tetroid*.java
 * 
 *  Usage:  Move blocks with keys: a (left), a (right), s (down), w (rotate), space (slam down)
 * 
 *  PART 1: Complete the TetraSet Class -> complete the reduce() method
 *
 *  PART 2: Select next block at random instead of in order, per the NES randomizer @ https://simon.lc/the-history-of-tetris-randomizers
 *
 *  EXTRA PRACTICE: Find a suitable end-game procedure if a column cannot be filled any higher
 * 
 **************************************************************************************************/

public class Tetra { 
    // final global constants
    public final static int DELAY = 10;
    public final static int gridX = 10;
    public final static int gridY = 20;

    // global objects
    public static Shape tetroid;
    public static Shape[] templates;
    public static TetraSet blob = new TetraSet(gridX, gridY);
    public static Display background = new Display(gridX, gridY, blob);

    // global tracker
    public static int nextBlock;

    public static void main(String[] args) {    
        boolean inPlay = false;
        nextBlock = -1;
        long time0 = System.currentTimeMillis();
        while (true) {
            long timeDelta = System.currentTimeMillis() - time0;
            // select a new block if last block no longer in play
            if (!inPlay) {
                inPlay = select(nextBlock());
            }

            // process key entries if object IS selected
            if (StdDraw.hasNextKeyTyped() && inPlay) {
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
            if (inPlay) {
                background.drawBackground(blob); 
                tetroid.draw();
                if (timeDelta > 500) {
                    if (!tetroid.drop(blob, gridX, gridY)) {
                        inPlay = unselect();
                    }
                    time0 = System.currentTimeMillis();
                }
                StdDraw.show(); 
                StdDraw.pause(DELAY);
            }
        } // end of while
    } 

    // reset block if floored
    public static boolean unselect() {
        blob.update(tetroid);
        tetroid = null;
        return false;
    }

    // reinit block IF no block in play
    public static boolean select(int k) {
        // init a new block of type k, centered at the top of the grid
        initNew(k, gridX / 2, gridY - 2);
        return true;
    }

    // reinit block IF no block in play
    public static int nextBlock() {
        nextBlock = (nextBlock + 1) % 7;
        return nextBlock;
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
} 
