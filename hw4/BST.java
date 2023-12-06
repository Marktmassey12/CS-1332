import java.util.*;

/**
 * Your implementation of a BST.
 *
 * @author Mark T. Massey
 * @version 1.0
 * @userid mmassey42 (i.e. gburdell3)
 * @GTID 903634890 (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot initialize null data");
        }
        for (T obj: data) {
            if (obj == null) {
                throw new IllegalArgumentException("Cannot initialize null data");
            }
            add(obj);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added.");
        }
        root = hAdd(data, root);
        size++;
    }
    /**
     * Created helper method that recursively adds the data
     * @param data data added
     * @param cur current location of node in tree
     * @return returns node with data
     */
    private BSTNode<T> hAdd(T data, BSTNode<T> cur) {
        if (cur == null) {
            return new BSTNode<>(data);
        } else if (cur.getData().compareTo(data) < 0) {
            cur.setRight(hAdd(data, cur.getRight()));
            return cur;
        } else if (cur.getData().compareTo(data) > 0) {
            cur.setLeft(hAdd(data, cur.getLeft()));
            return cur;
        } else {
            size--;
            return cur;
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = rRemove(root, data, dummy);
        return dummy.getData();
    }
    /**
     * helper method that will faciliate recursive removal of data
     * @param cur node that will keep current location in tree
     * @param data data
     * @param temp holds removed data
     * @return return node with data
     */
    private BSTNode<T> rRemove(BSTNode<T> cur, T data, BSTNode<T> temp) {
        if (cur == null) {
            throw new NoSuchElementException("data not found within tree");
        } else if (cur.getData().compareTo(data) > 0) {
            cur.setLeft(rRemove(cur.getLeft(), data, temp));
        } else if (cur.getData().compareTo(data) < 0) {
            cur.setRight(rRemove(cur.getRight(), data, temp));
        } else {
            temp.setData(cur.getData());
            size--;
            if (cur.getLeft() == null && cur.getRight() == null) {
                return null;
            } else if (cur.getLeft() != null && cur.getRight() == null) {
                return cur.getLeft();
            } else if (cur.getRight() != null && cur.getLeft() == null) {
                return cur.getRight();
            } else {
                BSTNode<T> temp2 = new BSTNode<>(null);
                cur.setRight(removeSuccessor(cur.getRight(), temp2));
                cur.setData(temp2.getData());
            }
        }
        return cur;
    }
    /**
     * Method to help removal with the successor implementation.
     * @param cur node to keep track of current location in tree
     * @param temp node to hold data in
     * @return node after removing successor
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> cur, BSTNode<T> temp) {
        if (cur.getLeft() == null) {
            temp.setData(cur.getData());
            return cur.getRight();
        } else {
            cur.setLeft(removeSuccessor(cur.getLeft(), temp));
            return cur;
        }
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot contain null data.");
        }
        BSTNode<T> temp = new BSTNode<>(null);
        getH(root, data, temp);
        return temp.getData();
    }
    /**
     * Helper method for Get.
     * @param cur node tracks current node
     * @param data data retrieved
     * @param temp node holds retrieved data
     * @return returns current node
     */
    private BSTNode getH(BSTNode<T> cur, T data, BSTNode<T> temp) {
        if (cur == null) {
            throw new NoSuchElementException("Data not located within the tree.");
        } else if (cur.getData().compareTo(data) < 0) {
            cur.setRight(getH(cur.getRight(), data, temp));
        } else if (cur.getData().compareTo(data) > 0) {
            cur.setLeft(getH(cur.getLeft(), data, temp));
        } else {
            temp.setData(cur.getData());
        }
        return cur;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot contain null data.");
        }
        return searchH(root, data);
    }
    /**
     * Helper method to search BST.
     * @param cur keeps track of current node
     * @param data data searching for
     * @return true or false if data is in the tree
     */
    private boolean searchH(BSTNode<T> cur, T data) {
        if (cur == null) {
            return false;
        } else if (cur.getData().compareTo(data) < 0) {
            return searchH(cur.getRight(), data);
        } else if (cur.getData().compareTo(data) > 0) {
            return searchH(cur.getLeft(), data);
        } else {
            return true;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> gList = new LinkedList<T>();
        if (root == null) {
            return gList;
        } else {
            return hPreorder(root, gList);
        }
    }
    /**
     * Helper method for preorder.
     * @param cur node keeps track of current spot in tree
     * @param lList linked list that data is added to
     * @return linked list of ordered data
     */
    private List<T> hPreorder(BSTNode<T> cur, List<T> lList) {
        if (cur != null) {
            lList.add(cur.getData());
            hPreorder(cur.getLeft(), lList);
            hPreorder(cur.getRight(), lList);
        }
        return lList;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> iList = new LinkedList<T>();
        if (root == null) {
            return iList;
        } else {
            return hInorder(root, iList);
        }
    }
    /**
     * Helper method for inorder.
     * @param cur node keeps track of current spot in tree
     * @param lList linked list that data is added to
     * @return linked list of ordered data
     */
    private List<T> hInorder(BSTNode<T> cur, List<T> lList) {
        if (cur != null) {
            hInorder(cur.getLeft(), lList);
            lList.add(cur.getData());
            hInorder(cur.getRight(), lList);
        }
        return lList;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> iList = new LinkedList<T>();
        if (root == null) {
            return iList;
        } else {
            return hPostorder(root, iList);
        }
    }
    /**
     * Helper method for postorder.
     * @param cur node keeps track of current spot in tree
     * @param lList linked list that data is added to
     * @return linked list of ordered data
     */
    private List<T> hPostorder(BSTNode<T> cur, List<T> lList) {
        if (cur != null) {
            hPostorder(cur.getLeft(), lList);
            hPostorder(cur.getRight(), lList);
            lList.add(cur.getData());
        }
        return lList;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> lList = new LinkedList<>();
        Queue<BSTNode<T>> qu = new LinkedList<BSTNode<T>>();
        if (root == null) {
            return lList;
        }
        qu.add(root);
        while (!qu.isEmpty()) {
            BSTNode<T> cur = qu.remove();
            lList.add(cur.getData());
            if (cur.getLeft() != null) {
                qu.add(cur.getLeft());
            }
            if (cur.getRight() != null) {
                qu.add(cur.getRight());
            }
        }
        return lList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        int height = -1;
        return height + hHeight(root);
    }
    /**
     * Helper method for height.
     * @param cur node to keep track of location in tree
     * @return integer of the height
     */
    private int hHeight(BSTNode<T> cur) {
        if (cur == null) {
            return 0;
        } else {
            return 1 + Math.max(hHeight(cur.getLeft()), hHeight(cur.getRight()));
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     *
     * This must be done recursively.
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   11   15   40
     *  /
     * 10
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        List<T> output = new ArrayList<>();
        if (root == null) {
            return output;
        }
        return helperMethod(root, output, 0);
    }
    private List<T> helperMethod(BSTNode<T> cur, List<T> output, int level) {
        if (cur != null) {
            if (output.size() == level) {
                output.add(cur.getData());
            }
            if (cur.getRight() != null) {
                helperMethod(cur.getRight(), output, level + 1);
            }
            if (cur.getLeft() != null) {
                helperMethod(cur.getLeft(), output, level + 1);
            }
        }
        return output;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
