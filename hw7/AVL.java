import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Mark T. Massey
 * @version 1.0
 * @userid mmassey42 (i.e. gburdell3)
 * @GTID 903634890 (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: StackOverflow, Youtube, GeeksforGeeks
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot initialize null data.");
        }
        for (T item: data) {
            if (item == null) {
                throw new IllegalArgumentException("Cannot initialize null data.");
            }
            add(item);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data.");
        }
        root = addHelp(data, root);
        size++;
    }

    /**
     * Helper method to recursively add data to tree, like in BST.
     * @param data the data to be added
     * @param cur node to keep track of location in tree
     * @return added node
     */
    private AVLNode<T> addHelp(T data, AVLNode<T> cur) {
        if (cur == null) {
            AVLNode<T> newN = new AVLNode<>(data);
            newN.setBalanceFactor(0);
            newN.setBalanceFactor(0);
            return newN;
        } else if (cur.getData().compareTo(data) < 0) {
            cur.setRight(addHelp(data, cur.getRight()));
            cur = update(cur);
            return cur;
        } else if (cur.getData().compareTo(data) > 0) {
            cur.setLeft(addHelp(data, cur.getLeft()));
            cur = update(cur);
            return cur;
        } else {
            size--;
            return cur;
        }
    }
    /**
     * Helper method to update node's balance factors and heights and rotate accordingly.
     * @param cur node to keep track of location in tree
     * @return current node with rotated children and data
     */
    private AVLNode<T> update(AVLNode<T> cur) {
        cur.setBalanceFactor(height(cur.getLeft()) - height(cur.getRight()));
        cur.setHeight(1 + Math.max(height(cur.getLeft()), height(cur.getRight())));
        if (Math.abs(cur.getBalanceFactor()) > 1) {
            if (cur.getBalanceFactor() > 0) {
                if (cur.getLeft().getBalanceFactor() < 0) {
                    cur.setLeft(leftRotate(cur.getLeft()));
                }
                cur = rightRotate(cur);
            } else if (cur.getBalanceFactor() < 0) {
                if (cur.getRight().getBalanceFactor() > 0) {
                    cur.setRight(rightRotate(cur.getRight()));
                }
                cur = leftRotate(cur);
            }
        }
        return cur;
    }
    /**
     * Helper method for right rotations.
     * @param a node to be rotated around
     * @return new node with swapped children
     */
    private AVLNode<T> rightRotate(AVLNode<T> a) {
        AVLNode<T> b = a.getLeft();
        a.setLeft(b.getRight());
        b.setRight(a);
        update(a);
        update(b);
        return b;
    }

    /**
     * Helper method for left rotations.
     * @param a node to be rotated around
     * @return new node with swapped children
     */
    private AVLNode<T> leftRotate(AVLNode<T> a) {
        AVLNode<T> b = a.getRight();
        a.setRight(b.getLeft());
        b.setLeft(a);
        update(a);
        update(b);
        return b;
    }

    /**
     * Helper method to grab the heights of the nodes.
     * @param curr node to find the height of
     * @return height of current node
     */
    private int height(AVLNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return curr.getHeight();
        }
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        }
        AVLNode<T> dum = new AVLNode<>(null);
        root = removerHelp(data, root, dum);
        return dum.getData();
    }

    /**
     * recursive helper method to remove the data
     * @param data the data to add
     * @param node the current node to keep track of location in the tree
     * @param rem the node to hold the removed data
     * @return node with the data in it
     */
    private AVLNode<T> removerHelp(T data, AVLNode<T> node, AVLNode<T> rem) {
        if (node == null) {
            throw new java.util.NoSuchElementException("There is not root, so we can't remove anything.");
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removerHelp(data, node.getRight(), rem));
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removerHelp(data, node.getLeft(), rem));
        } else {
            rem.setData(node.getData());
            size--;
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() != null && node.getRight() == null) {
                return node.getLeft();
            } else if (node.getRight() != null && node.getLeft() == null) {
                return node.getRight();
            } else {
                AVLNode<T> children = new AVLNode<T>(null);
                node.setLeft(predecessorH(node.getLeft(), children));
                node.setData(children.getData());
            }
        }
        node = update(node);
        return node;
    }

    /**
     * method to help removal with predecessor implemented
     * @param node the current node to keep track of location in the tree
     * @param child node to hold the data
     * @return node after removing the predecessor
     */
    private AVLNode<T> predecessorH(AVLNode<T> node, AVLNode<T> child) {
        if (node.getRight() == null) {
            child.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(predecessorH(node.getRight(), child));
            node = update(node);
            return node;
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot contain null data.");
        }
        AVLNode<T> tmp = new AVLNode<>(null);
        hGet(root, data, tmp);
        return tmp.getData();
    }

    /**
     * Helper method for Get.
     * @param cur node that keeps track of current node in tree
     * @param data data that is being retrieved
     * @param tmp node that holds the data being retrieved
     * @return current node
     */
    private AVLNode<T> hGet(AVLNode<T> cur, T data, AVLNode<T> tmp) {
        if (cur == null) {
            throw new NoSuchElementException("The data is not in the tree.");
        } else if (cur.getData().compareTo(data) < 0) {
            cur.setRight(hGet(cur.getRight(), data, tmp));
        } else if (cur.getData().compareTo(data) > 0) {
            cur.setLeft(hGet(cur.getLeft(), data, tmp));
        } else {
            tmp.setData(cur.getData());
        }
        return cur;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
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
     * Helper method to search AVL.
     * @param curr node to keep track of current node in tree
     * @param data data searching for
     * @return true or false if data is in the tree
     */
    private boolean searchH(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (curr.getData().compareTo(data) < 0) {
            return searchH(curr.getRight(), data);
        } else if (curr.getData().compareTo(data) > 0) {
            return searchH(curr.getLeft(), data);
        } else {
            return true;
        }
    }
    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     *
     * Should be recursive.
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        return maxHelp(root);
    }
    /**
     * recursive helper method to find the maximum deepest node
     * @param data the current node to keep track of location in the tree
     * @return the data in the deepest node
     */
    private T maxHelp(AVLNode<T> data) {
        if (data == null) {
            return null;
        } else if (data.getBalanceFactor() > 0) {
            return maxHelp(data.getLeft());
        } else if (data.getBalanceFactor() < 0) {
            return maxHelp(data.getRight());
        } else {
            if (data.getLeft() == null && data.getRight() == null) {
                return data.getData();
            } else {
                return maxHelp(data.getRight());
            }
        }
    }

    /**
     * In BSTs, you learned about the concept of the successor: the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node whose left subtree contains the data.
     *
     * The second case means the successor node will be one of the node(s) we
     * traversed left from to find data. Since the successor is the SMALLEST element
     * greater than data, the successor node is the lowest/last node
     * we traversed left from on the path to the data node.
     *
     * This should NOT be used in the remove method.
     *
     * Should be recursive.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * successor(76) should return 81
     * successor(81) should return 90
     * successor(40) should return 76
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T successor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data we are searching for isn't there.");
        }
        return hSuccessor(data, root, null);
    }

    /**
     * helper method to find successor
     * @param data data of node to find successor of
     * @param node the current node to keep track of location in the tree
     * @param suc successor node
     * @return successor node
     */
    private T hSuccessor(T data, AVLNode<T> node, T suc) {
        if (node == null) {
            throw new java.util.NoSuchElementException("the");
        } else if (data.compareTo(node.getData()) > 0) {
            return hSuccessor(data, node.getRight(), suc);
        } else if (data.compareTo(node.getData()) < 0) {
            return hSuccessor(data, node.getLeft(), node.getData());
        } else {
            if (node.getRight() != null) {
                return successDoubleHelp(node.getRight());
            } else {
                return suc;
            }
        }
    }

    /**
     * second recursive helper method to find successor. Traverse if the successor has a right child
     * @param node the current node to keep track of location in the tree
     * @return successor node
     */
    private T successDoubleHelp(AVLNode<T> node) {
        if (node.getLeft() == null) {
            return node.getData();
        } else {
            return successDoubleHelp(node.getLeft());
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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
