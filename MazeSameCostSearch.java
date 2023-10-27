/*
file name:      MazeSameCostSearch.java
Authors:        Anh Nguyen
last modified:  4/21/2023
purpose: Find the target using Same Cost Search algorithm
*/
import java.util.Comparator;

public class MazeSameCostSearch extends AbstractMazeSearch {

    private PriorityQueue<Cell> priorityQueue;

    public MazeSameCostSearch(Maze maze){
        super(maze);
        Comparator<Cell> comparator = new Comparator<Cell>(){

            @Override
            public int compare(Cell cell1, Cell cell2) {
                int cost1 = traceback(cell1).size();
                int cost2 = traceback(cell2).size();
                if (cost1 == cost2){
                    return 0;
                } else if (cost1 < cost2){
                    return -1;
                } else {
                    return 1;
                }
            }
        };
        this.priorityQueue = new Heap<Cell>(comparator, false);
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
