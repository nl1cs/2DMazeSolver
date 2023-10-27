/*
file name:      MazeDepthFirstSearch.java
Authors:        Anh Nguyen
last modified:  4/21/2023
purpose: Find the target using Depth First Search algorithm
*/
public class MazeDepthFirstSearch extends AbstractMazeSearch {

    private Stack<Cell> stack; // A stack data structure to hold cells for DFS.

    public MazeDepthFirstSearch(Maze maze) {
        super(maze); // Calls the constructor of the parent class and sets the maze.
        this.stack = new LinkedList<>(); // Initialize the stack as an empty LinkedList.
    }

    public void addCell(Cell next) {
        stack.push(next); // Add the given cell to the top of the stack.
    }

    public Cell findNextCell() {
        return stack.pop(); // Remove and return the top cell from the stack.
    }

    public int numRemainingCells() {
        return stack.size(); // Return the number of cells currently in the stack.
    }

}
