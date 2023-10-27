/*
file name:      AbstractMazeSearchTests.java
Authors:        Anh Nguyen
last modified:  4/21/2023
purpose: Test the AbstractMazeSearch class
*/
public class AbstractMazeSearchTests {

    // Define a subclass of AbstractMazeSearch for testing
    private static class TestMazeSearch extends AbstractMazeSearch {

        public TestMazeSearch(Maze maze) {
            super(maze);
        }

        @Override
        public Cell findNextCell() {
            return null;
        }

        @Override
        public void addCell(Cell next) {}

        @Override
        public int numRemainingCells() {
            return 0;
        }
    }

    // Test numVisited() method


    // Test getMaze() method

    public void testGetMaze() {
        Maze maze = new Maze(10, 10, 0.2);
        AbstractMazeSearch search = new TestMazeSearch(maze);
        Maze expected = maze;
        Maze actual = search.getMaze();
        assert expected == actual : "getMaze test failed, expected " + expected + " but was " + actual;
    }

    // Test setTarget() and getTarget() methods

    public void testSetGetTarget() {
        Maze maze = new Maze(10, 10, 0.2);
        AbstractMazeSearch search = new TestMazeSearch(maze);
        Cell target = new Cell(1, 1, CellType.FREE);
        search.setTarget(target);
        Cell expected = target;
        Cell actual = search.getTarget();
        assert expected == actual : "setTarget/getTarget test failed, expected " + expected + " but was " + actual;
    }

    // Test setCur() and getCur() methods

    public void testSetGetCur() {
        Maze maze = new Maze(10, 10, 0.2);
        AbstractMazeSearch search = new TestMazeSearch(maze);
        Cell cur = new Cell(1, 1, CellType.FREE);
        search.setCur(cur);
        Cell expected = cur;
        Cell actual = search.getCur();
        assert expected == actual : "setCur/getCur test failed, expected " + expected + " but was " + actual;
    }

    // Test setStart() and getStart() methods

    public void testSetGetStart() {
        Maze maze = new Maze(10, 10, 0.2);
        AbstractMazeSearch search = new TestMazeSearch(maze);
        Cell start = new Cell(1, 1, CellType.FREE);
        search.setStart(start);
        Cell expected = start;
        Cell actual = search.getStart();
        assert expected == actual : "setStart/getStart test failed, expected " + expected + " but was " + actual;
    }


    // Test reset() method
    public void testReset() {
        Maze maze = new Maze(10, 10, 0.2);
        AbstractMazeSearch search = new TestMazeSearch(maze);
        Cell start = new Cell(1, 1, CellType.FREE);
        Cell target = new Cell(8, 8, CellType.FREE);
        Cell cur = new Cell(2, 2, CellType.FREE);
        search.setStart(start);
        search.setTarget(target);
        search.setCur(cur);
        search.reset();
        Cell expectedStart = null;
        Cell actualStart = search.getStart();
        assert expectedStart == actualStart : "reset test failed, expected start to be null but was " + actualStart;
        Cell expectedTarget = null;
        Cell actualTarget = search.getTarget();
        assert expectedTarget == actualTarget : "reset test failed, expected target to be null but was " + actualTarget;
        Cell expectedCur = null;
        Cell actualCur = search.getCur();
        assert expectedCur == actualCur : "reset test failed, expected cur to be null but was " + actualCur;
        }
        
        // Main method to run the tests
        
        public static void main(String[] args) {
        AbstractMazeSearchTests test = new AbstractMazeSearchTests();
        test.testGetMaze();
        test.testSetGetTarget();
        test.testSetGetCur();
        test.testSetGetStart();
        test.testReset();
        System.out.println("All tests passed!");
        }
    }