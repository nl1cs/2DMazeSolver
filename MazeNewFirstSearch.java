/*
file name:      MazeNewFirstSearch.java
Authors:        Anh Nguyen
last modified:  4/21/2023
purpose: Find the target using a different First Search algorithm
*/
import java.util.*;

public class MazeNewFirstSearch extends AbstractMazeSearch {

    private PriorityQueue<Cell> priorityQueue;

    public MazeNewFirstSearch(Maze maze) {
        super(maze);
        Comparator<Cell> comparator = new Comparator<Cell>(){
            @Override
            public int compare(Cell c1, Cell c2) {
                // cell 1
                int d1 = Math.abs(target.getRow() - c1.getRow()) + Math.abs(target.getCol() - c1.getCol());
                // cell 2
                int d2 = Math.abs(target.getRow() - c2.getRow()) + Math.abs(target.getCol() - c2.getCol());
                if (d1 == d2){
                    return 0;
                } else if (d1 < d2){
                    return -1;
                } else {
                    return 1;
                }
            }
        };
        this.priorityQueue = new Heap<Cell>(comparator, false);;
    }

    public void addCell(Cell next){
        priorityQueue.offer(next);
    }

    public Cell findNextCell(){
        return priorityQueue.poll();
    }

    public int numRemainingCells(){
        return priorityQueue.size();
    }
}