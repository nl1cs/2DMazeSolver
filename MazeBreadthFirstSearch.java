/*
file name:      MazeBreadthFirstSearch.java
Authors:        Anh Nguyen
last modified:  4/21/2023
purpose: Find the target using Breadth First Search algorithm
*/
public class MazeBreadthFirstSearch extends AbstractMazeSearch {

    private Queue<Cell> queue; // declare a queue of cells

    public MazeBreadthFirstSearch(Maze maze) { // constructor that takes a Maze object as argument
        super(maze); // call constructor of superclass
        this.queue = new LinkedList<>(); // initialize queue as a new LinkedList object
    }

    public void addCell(Cell next) { // method to add a cell to the queue
        queue.offer(next); // add the cell to the end of the queue
    }

    public Cell findNextCell() { // method to get the next cell to explore
        return queue.poll(); // remove and return the first cell in the queue
    }

    public int numRemainingCells() { // method to get the number of cells left to explore
        return queue.size(); // return the size of the queue
    }

}
