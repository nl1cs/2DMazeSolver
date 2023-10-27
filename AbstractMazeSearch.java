
/*
file name:      AbstractMazeSearch.java
Authors:        Anh Nguyen
last modified:  4/21/2023
purpose: Set up the AbstractMazeSearch class to search for a target in a randomly generated maze.
*/
import java.awt.Color;
import java.awt.Graphics;

public abstract class AbstractMazeSearch {

    public Maze maze;
    protected Cell start;
    protected Cell target;
    protected Cell cur;
    public MazeSearchDisplay mazedisplay;
    public static int numVisited;

    public AbstractMazeSearch(Maze maze) {
        this.maze = maze;
        this.cur = null;
        this.target = null;
        this.start = null;
        numVisited = 0;
    }

    public int numVisited() {
        return numVisited;
    }

    /**
     * Returns the next cell to explore
     */
    public abstract Cell findNextCell();

    /**
     * this method adds the given Cell to whatever structure is storing the future
     * Cells to explore.
     */
    public abstract void addCell(Cell next);

    /**
     * this method returns the number of future Cells to explore
     * (so just the size of whatever structure is storing the future Cells).
     * 
     * @return number of future cells to explore
     */
    public abstract int numRemainingCells();

    public Maze getMaze() {
        return maze;
    }

    // set the target cell
    public void setTarget(Cell target) {
        this.target = target;
    }

    // get the target cell
    public Cell getTarget() {
        return target;
    }

    // set the current cell
    public void setCur(Cell cell) {
        this.cur = cell;
    }

    // get the current cell
    public Cell getCur() {
        return cur;
    }

    // set the starting cell
    public void setStart(Cell start) {
        this.start = start;
        start.setPrev(start);
    }

    // get the starting cell
    public Cell getStart() {
        return start;
    }

    // reset the cells
    public void reset() {
        this.cur = null;
        this.start = null;
        this.target = null;
    }

    // trace back method to help with drawing the path
    public LinkedList<Cell> traceback(Cell cell) {
        Cell curCell = cell;
        LinkedList<Cell> path = new LinkedList<>();

        while (curCell != null) {

            path.addFirst(curCell);
            if (curCell.getRow() == start.getRow() && curCell.getCol() == start.getCol()) {

                return path;
            }
            curCell = curCell.getPrev();
        }
        return null;
    }

    // search method
    public LinkedList<Cell> search(Cell start, Cell target, boolean display, int delay) {

        setStart(start);
        setTarget(target);
        setCur(start);

        addCell(start);

        if (display == true) {
            mazedisplay = new MazeSearchDisplay(this, 15);
        }

        while (numRemainingCells() != 0) {

            if (display == true) {
                try {
                    Thread.sleep(delay);
                    mazedisplay.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            setCur(findNextCell());

            for (Cell neighbor : maze.getNeighbors(cur)) {
                if (neighbor.getPrev() == null) {
                    neighbor.setPrev(cur);
                    addCell(neighbor);
                    numVisited++; // Increment numVisited for start cell
                    if (neighbor.getRow() == target.getRow() && neighbor.getCol() == target.getCol()) {
                        target.setPrev(cur);
                        LinkedList<Cell> path = traceback(target);
                        if (path != null) {
                            System.out.println("Path length: " + path.size());
                        }
                        System.out.println("Number of cells visited: " + numVisited());
                        System.out.println("Ratio: " + ((double)numVisited() / (double)path.size()));
                        return traceback(neighbor);
                    }
                }
            }
        }

        return null;
    }

    public void draw(Graphics g, int scale) {
        // Draws the base version of the maze
        getMaze().draw(g, scale);
        // Draws the paths taken by the searcher
        getStart().drawAllPrevs(getMaze(), g, scale, Color.RED);
        // Draws the start cell
        getStart().draw(g, scale, Color.LIGHT_GRAY);
        // Draws the target cell
        getTarget().draw(g, scale, Color.RED);
        // Draws the current cell
        getCur().draw(g, scale, Color.MAGENTA);

        // If the target has been found, draws the path taken by the searcher to reach
        // the target sans backtracking.
        // System.out.println(getTarget() + "painting");
        if (getTarget().getPrev() != null) {
            Cell traceBackCur = getTarget().getPrev();
            while (!traceBackCur.equals(getStart())) {
                // System.out.println("Coloring path green!");
                traceBackCur.draw(g, scale, Color.GREEN);
                traceBackCur = traceBackCur.getPrev();
            }
            getTarget().drawPrevPath(g, scale, Color.BLUE);
        }
    }

    public static void main(String[] argz) throws InterruptedException {
        Cell x = new Cell(1, 1, CellType.FREE);
        Cell y = new Cell(38, 38, CellType.FREE);
        Maze ls = new Maze(40, 40, .2);
        AbstractMazeSearch maze = new MazeCostSearch(ls);
        maze.search(x, y, true, 0);
    }

}
