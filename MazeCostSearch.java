/*
file name:      MazeCostSearch.java
Authors:        Anh Nguyen
last modified:  4/21/2023
purpose: Find the target using Breadth Cost Search algorithm to implement extension 1
*/
import java.util.Comparator;
import java.util.PriorityQueue;

public class MazeCostSearch extends AbstractMazeSearch {

    private PriorityQueue<Cell> priorityQueue;

    public MazeCostSearch(Maze maze) {
        super(maze);
        Comparator<Cell> comparator = new Comparator<Cell>() {
            @Override
            public int compare(Cell cell1, Cell cell2) {
                double cost1 = cell1.getCost() + getHeuristicCost(cell1);
                double cost2 = cell2.getCost() + getHeuristicCost(cell2);
                return Double.compare(cost1, cost2);
            }
        };
        this.priorityQueue = new PriorityQueue<>(comparator);
    }

    @Override
    public Cell findNextCell() {
        return priorityQueue.poll();
    }

    @Override
    public void addCell(Cell next) {
        priorityQueue.add(next);
    }

    @Override
    public int numRemainingCells() {
        return priorityQueue.size();
    }

    
   @Override
public LinkedList<Cell> search(Cell start, Cell target, boolean display, int delay) {
    setStart(start);
    setTarget(target);
    setCur(start);
    start.setCost(0);
    addCell(start);
    if (display) {
        mazedisplay = new MazeSearchDisplay(this, 15);
    }

    double totalCost = 0.0; // initialize total cost to 0

    while (numRemainingCells() != 0) {

        if (display) {
            try {
                Thread.sleep(delay);
                mazedisplay.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        setCur(findNextCell());
        System.out.println();
        for (Cell neighbor : maze.getNeighbors(cur)) {
            double cost = cur.getCost() + neighbor.getCost();
            if (neighbor.getPrev() == null || cost < neighbor.getCost()) {
                neighbor.setCost(cost);
                neighbor.setPrev(cur);
                addCell(neighbor);
                if (neighbor.getRow() == target.getRow() && neighbor.getCol() == target.getCol()) {
                    target.setPrev(cur);

                    // calculate total cost of the path
                    Cell current = neighbor;
                    while (current != start) {
                        totalCost += current.getCost();
                        current = current.getPrev();
                    }
                    totalCost += start.getCost();
                    
                    // calculate path length
                    int pathLength = 1;
                    current = neighbor;
                    while (current != start) {
                        pathLength++;
                        current = current.getPrev();
                    }
                    
                    System.out.println("Total cost of path: " + totalCost);
                    System.out.println("Number of cells visited: " + numVisited());
                    System.out.println("Path length: " + pathLength);
                    return traceback(neighbor);
                }
                numVisited++;
            }
        }
    }

    return null;
}


    private double getHeuristicCost(Cell cell) {
        return Math.abs(target.getRow() - cell.getRow()) + Math.abs(target.getCol() - cell.getCol());
    }
}
