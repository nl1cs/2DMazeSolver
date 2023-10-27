/*
file name:      Heap.java
Authors:        Anh Nguyen
last modified:  4/21/2023
purpose: Set up the Heap data structure
*/
import java.util.Comparator;

public class Heap<T> implements PriorityQueue<T> {

    private static class Node<T> {
        Node<T> left, right, parent;
        T data;

        public Node(T data, Node<T> left, Node<T> right, Node<T> parent) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public String toString() {
            return data + "";
        }

    }

    private int size;
    private Node<T> root, last;
    private Comparator<T> comparator;

    /**
     * Initializes a default min Heap.
     */
    public Heap() {
        this(null, false); // no comparator, not a MaxHeap
    }

    /**
     * Initializes a default Heap that is either min or max depending on the
     * specified boolean.
     * 
     * @param maxHeap the specified parameter, true if max, otherwise false
     */
    public Heap(boolean maxHeap) {
        this(null, maxHeap);
    }

    /**
     * Initializes a min Heap based on the given comparator.
     * 
     * @param comparator the specified comparator
     */
    public Heap(Comparator<T> comparator) {
        this(comparator, false);
    }

    /**
     * Initializes a Heap based on the given comparator and the given boolean
     * indicating max or min.
     * 
     * @param comparator the specified comparator
     * @param maxHeap    the specified boolean, true if max, otherwise false
     */
    @SuppressWarnings("unchecked")
    public Heap(Comparator<T> comparator, boolean maxHeap) {
        if (comparator != null) {
            this.comparator = comparator;
        } else {
            this.comparator = new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return ((Comparable<T>) o1).compareTo(o2);
                }
            };
        }
        if (maxHeap) {
            Comparator<T> normalComparator = this.comparator;
            Comparator<T> reverseComparator = new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return normalComparator.compare(o2, o1);
                }
            };
            this.comparator = reverseComparator;
        }
        this.size = 0;
        this.root = null;
        this.last = null;
    }

    /**
     * Adds the specified item to the Heap
     */
    public void offer(T item) {
        if (this.size == 0) {
            this.root = new Node<T>(item, null, null, null);
            size++;
            this.last = this.root;
        } else if (this.size % 2 == 0) {
            this.last.parent.right = new Node<T>(item, null, null, last.parent);
            this.last = this.last.parent.right;
            size++;
            bubbleUp(this.last);
        } else {
            Node<T> curNode = this.last;
            while (curNode != this.root && curNode == curNode.parent.right) {
                curNode = curNode.parent;
            }
            if (curNode != root) {
                curNode = curNode.parent.right;
            }
            while (curNode.left != null) {
                curNode = curNode.left;
            }
            curNode.left = new Node<T>(item, null, null, curNode);
            this.last = curNode.left;
            size++;
            bubbleUp(this.last);
        }
    }

    /**
     * A private helper method that swaps the data of the two nodes specified
     * 
     * @param node1 the first specified node
     * @param node2 the second specified node
     */
    private void swap(Node<T> node1, Node<T> node2) {
        T temp = node1.data;
        node1.data = node2.data;
        node2.data = temp;
    }

    /**
     * A private recursive helper method that we use after offering. This is to make
     * sure that the Heap
     * property is still true. The specified node gets sifted up until it satisfies
     * the Heap property.
     * 
     * @param curNode the specified node
     */
    private void bubbleUp(Node<T> curNode) {
        if (curNode == this.root) {
            return;
        }
        T myself = curNode.data;
        T myParent = curNode.parent.data;
        if (comparator.compare(myself, myParent) < 0) {
            swap(curNode, curNode.parent);
            bubbleUp(curNode.parent);
        }
    }

    /**
     * A private recursive helper method that we use after polling. This is to make
     * sure that the Heap
     * property is still true. The specified node gets sifted down until it
     * satisfies the Heap property.
     * 
     * @param curNode the specified node
     */
    private void bubbleDown(Node<T> curNode) {
        if (curNode.left == null) {
            return;
        } else if (curNode.right == null) {
            T myself = curNode.data;
            T myLeft = curNode.left.data;
            if (comparator.compare(myLeft, myself) < 0) {
                swap(curNode, curNode.left);
                bubbleDown(curNode.left);
            }
        } else {
            T myself = curNode.data;
            T myLeft = curNode.left.data;
            T myRight = curNode.right.data;
            if (comparator.compare(myLeft, myRight) < 0) {
                if (comparator.compare(myLeft, myself) < 0) {
                    swap(curNode, curNode.left);
                    bubbleDown(curNode.left);
                }
            } else {
                if (comparator.compare(myRight, myself) < 0) {
                    swap(curNode, curNode.right);
                    bubbleDown(curNode.right);
                }
            }
        }
    }

    /**
     * Returns the number of items in the Heap
     */
    public int size() {
        return this.size;
    }

    /**
     * 
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the item of greatest priority in the Heap
     */
    public T peek() {
        return this.root.data;
    }

    /**
     * Returns and removes the item of the greatest priorty in the Heap
     */
    public T poll() {
        // In order to do this, you should take whatever item is in the last Node and
        // replace the root's data with it.
        // Like the offer method, the main difficulty here is updating the last Node.
        if (this.size == 0) {
            return null;
        }
        T removed = this.root.data;
        if (this.size == 1) {
            this.root = null;
            this.last = null;
            size--;
        } else if (this.size % 2 == 1) {
            this.root.data = this.last.data;
            this.last = this.last.parent.left;
            this.last.parent.right = null;
            size--;
            bubbleDown(this.root);
        } else {
            Node<T> curNode = this.last;
            while (curNode != this.root && curNode == curNode.parent.left) {
                curNode = curNode.parent;
            }
            if (curNode != this.root) {
                curNode = curNode.parent.left;
            }
            while (curNode.right != null) {
                curNode = curNode.right;
            }
            this.root.data = this.last.data;
            this.last.parent.left = null;
            this.last = curNode;
            size--;
            bubbleDown(this.root);
        }
        return removed;
    }

    /**
     * Updates the priority of the given item - that is, ensures that it is 'behind'
     * items with higher priority and
     * 'ahead' of items with lower priority
     */
    public void updatePriority(T item) {
        Node<T> walker = this.root;
        while (walker.left != null) {
            walker = walker.left;
        }
        while (walker.data != null && walker.data != item) {
            walker = nextNode(walker);
        }
        if (walker.data == null) {
            System.out.println("Item not found in Heap!");
            return;
        } else {
            T myself = walker.data;
            T myParent = walker.parent.data;
            if (comparator.compare(myself, myParent) < 0) {
                bubbleUp(walker);
            } else {
                bubbleDown(walker);
            }
        }
    }

    /**
     * A private helper method that finds the next node in the Heap
     * 
     * @param curNode the current node
     * @return the next node in the Heap
     */
    private Node<T> nextNode(Node<T> curNode) {
        if (curNode.right != null) {
            curNode = curNode.right;
            while (curNode.left != null) {
                curNode = curNode.left;
            }
            return curNode;
        }
        while (curNode.parent != null && curNode == curNode.parent.right) {
            curNode = curNode.parent;
        }
        return curNode.parent;
    }

    /**
     * Re1turns a String that represents the Heap accurately depicting the levels
     */
    public String toString() {
        if (this.root == null) {
            return "";
        }
        String bst = "root: " + this.root;
        if (this.size() == 1) {
            return bst;
        }
        String left = this.toStringLeft(this.root.left, 1);
        String right = this.toStringRight(this.root.right, 1);
        return right + "\n" + bst + left;
    }

    /**
     * A private recursive helper function for toString()
     * 
     * @param cur     the current Node
     * @param counter the current level on the BSTMap
     * @return an updated String representation of the left side of the BSTMap
     */
    private String toStringLeft(Node<T> cur, int counter) {
        String left = "";
        if (cur != null) {
            String tabs = "";
            int i = counter;
            while (i > 0) {
                tabs += "  ";
                i--;
            }
            left = toStringRight(cur.right, counter + 1) + "\n" + tabs + "left: " + cur
                    + toStringLeft(cur.left, counter + 1);
        }
        return left;
    }

    /**
     * A private recursive helper function for toString()
     * 
     * @param cur     the current Node
     * @param counter the current level on the BSTMap
     * @return an updated String representation of the right side of the BSTMap
     */
    private String toStringRight(Node<T> cur, int counter) {
        String right = "";
        if (cur != null) {
            String tabs = "";
            int i = counter;
            while (i > 0) {
                tabs += "  ";
                i--;
            }
            right = toStringRight(cur.right, counter + 1) + "\n" + tabs + "right: " + cur
                    + toStringLeft(cur.left, counter + 1);
        }
        return right;
    }

}