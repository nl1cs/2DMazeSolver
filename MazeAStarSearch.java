
/*
file name:      MazeAStarSearch.java
Authors:        Anh Nguyen
last modified:  4/21/2023
purpose: Find the shortest path from the starting cell to the target cell using A* algorithm.
*/
import java.util.Comparator;

public class MazeAStarSearch extends AbstractMazeSearch {

    // Initialize a priority queue to store cells
    private PriorityQueue<Cell> priorityQueue;

    public MazeAStarSearch(Maze maze) {
        super(maze);

        // Define a comparator to determine priority of cells in the priority queue
        Comparator<Cell> comparator = new Comparator<Cell>() {

            @Override
            public int compare(Cell cell1, Cell cell2) {
                // Calculate the number of steps from the starting cell to cell1 and cell2
                int numStep1 = traceback(cell1).size();
                int numStep2 = traceback(cell2).size();

                // Calculate the estimated distance (Manhattan distance) from cell1 and cell2 to
                // the target cell
                int d1 = Math.abs(target.getRow() - cell1.getRow())
                        + Math.abs(target.getCol() - cell1.getCol());
                int d2 = Math.abs(target.getRow() - cell2.getRow())
                        + Math.abs(target.getCol() - cell2.getCol());

                // Calculate the sum of the number of steps and estimated distance for cell1 and
                // cell2
                int step1 = numStep1 + d1;
                int step2 = numStep2 + d2;

                // Compare the sum of steps and estimated distance between cell1 and cell2
                if (step1 == step2) {
                    return 0; // Return 0 if cell1 and cell2 have the same priority
                } else if (step1 < step2) {
                    return -1; // Return -1 if cell1 has higher priority than cell2
                } else {
                    return 1; // Return 1 if cell2 has higher priority than cell1
                }
            }
        };

        // Create a new priority queue with the defined comparator
        // Set the queue to not allow duplicates
        this.priorityQueue = new Heap<Cell>(comparator, false);
    }

    @Override
    public LinkedList<Cell> search(Cell start, Cell target, boolean display, int delay) {
        setStart(start);
        setTarget(target);
        setCur(start);
    
        start.setCost(0);
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
                double cost = cur.getCost() + neighbor.getCost();
                if (neighbor.getPrev() == null || cost < neighbor.getCost()) {
                    neighbor.setCost(cost);
                    neighbor.setPrev(cur);
                    addCell(neighbor);
                    numVisited++; // Increment numVisited for start cell
                    if (neighbor.getRow() == target.getRow() && neighbor.getCol() == target.getCol()) {
                        target.setPrev(cur);
                        LinkedList<Cell> path = traceback(target);
                        if (path != null) {
                            double pathCost = 0.0;
                            for (Cell c : path) {
                                pathCost += c.getCost();
                            }
                            System.out.println("Total cost of path: " + pathCost);
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
    

    // Add a new cell to the priority queue
    public void addCell(Cell next) {
        priorityQueue.offer(next);
    }

    // Find the cell with the highest priority (lowest sum of steps and estimated
    // distance)
    public Cell findNextCell() {
        return priorityQueue.poll();
    }

    // Return the number of cells remaining in the priority queue
    public int numRemainingCells() {
        return priorityQueue.size();
    }

}
