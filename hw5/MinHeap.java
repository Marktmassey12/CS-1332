import java.util.ArrayList;
import java.util.NoSuchElementException;
/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * <p>
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     * <p>
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     * <p>
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Sorry! This data is null.");
        }
        backingArray = (T[]) new Comparable[(2 * data.size()) + 1];
        for (int i = 1; i < data.size() + 1; i++) {
            if (data.get(i - 1) == null) {
                throw new IllegalArgumentException("Sorry! This data is null.");
            }
            backingArray[i] = data.get(i - 1);
            size++;
        }
        for (int i = size / 2; i > 0; i--) {
            helpHeap(backingArray, i);
        }
    }

    /**
     * helper method for assistance in building the heap
     * @param i helps trace through the current value in the heap
     * @param backingArray is the parent index used for comparison to children nodes
     */
    private void helpHeap(T[] backingArray, int i) {
        int right = (2 * i) + 1;
        int left = (2 * i);
        T tmp = backingArray[i];
        if (right <= size) {
            if (tmp.compareTo(backingArray[left]) > 0
                    || tmp.compareTo(backingArray[right]) > 0) {
                if (backingArray[right].compareTo(backingArray[left]) < 0) {
                    tmp = backingArray[right];
                    backingArray[right] = tmp;
                    helpHeap(backingArray, 2 * i + 1);
                } else {
                    backingArray[i] = backingArray[left];
                    backingArray[right] = tmp;
                    helpHeap(backingArray, 2 * i);
                }
            }
        } else if (2 * 1 <= size) {
            if (backingArray[i].compareTo(backingArray[left]) > 0) {
                backingArray[i] = backingArray[left];
                backingArray[left] = tmp;
                helpHeap(backingArray, 2 * i);
            }
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data"
                    + "to the heap.");
        }
        int index = size + 1;
        int parIndex = index / 2;
        if (index < backingArray.length) {
            backingArray[index] = data;
        } else {
            T[] temp = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
            backingArray[index] = data;
        }
        while (parIndex >= 1
                && backingArray[parIndex].compareTo(backingArray[index])
                > 0) {
            T tempData = backingArray[index];
            backingArray[index] = backingArray[parIndex];
            backingArray[parIndex] = tempData;
            index = parIndex;
            parIndex = parIndex / 2;
        }
        size++;
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("There is nothing"
                    + "currently stored in the heap");
        }
        T data = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        int index = 1;
        int left = 2 * index;
        int right = (2 * index) + 1;
        boolean sort = false;
        while ((left < size || right < size) && !sort) {
            if (right >= size) { //compare to left and swap if necessary
                if (backingArray[index].compareTo(backingArray[left]) > 0) {
                    T temp = backingArray[index];
                    backingArray[index] = backingArray[left];
                    backingArray[left] = temp;
                    index = left;
                    left = index * 2;
                    right = (index * 2) + 1;
                } else {
                    sort = true;
                }
            } else {
                if (backingArray[left].compareTo(backingArray[right]) < 0) {
                    if (backingArray[index].compareTo(backingArray[left]) > 0) {
                        T temp = backingArray[index];
                        backingArray[index] = backingArray[left];
                        backingArray[left] = temp;
                        index = left;
                        left = index * 2;
                        right = (index * 2) + 1;
                    } else {
                        sort = true;
                    }
                } else {
                    if (backingArray[index].compareTo(backingArray[right])
                            > 0) {
                        T temp = backingArray[index];
                        backingArray[index] = backingArray[right];
                        backingArray[right] = temp;
                        index = right;
                        left = index * 2;
                        right = (index * 2) + 1;
                    } else {
                        sort = true;
                    }
                }
            }
        }
        size--;
        return data;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
