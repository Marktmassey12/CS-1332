import java.util.NoSuchElementException;
/**
 * Your implementation of a non-circular SinglyLinkedList with a tail pointer.
 *
 * @author Mark T. Massey
 * @version 1.0
 * @userid mmassey42 (e.g. gburdell3)
 * @GTID 903634890 (e.g. 900000000)
 * Collaborators: None
 */
public class SinglyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index to add the new element
     * @param data  the data to add
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index does not exist.");
        }
        if (data == null) {
            throw new IllegalArgumentException("Entry data is null.");
        }
        if (index == 0) {
            addToFront(data);
        } else {
            SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
            SinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            newNode.setNext(curr.getNext());
            curr.setNext(newNode);
            size++;
        }
    }
    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Illegal argument exception");
        }
        if (head == null) {
            SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
            head = newNode;
            tail = newNode;
        }
        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data, head);
        head = newNode;
        size++;
    }
    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null.");
        }
        if (size == 0) {
            addToFront(data);
        } else {
            SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
            tail.setNext(newNode);
            tail = newNode;
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and O(n) for all other
     * cases.
     *
     * @param index the index of the element to remove
     * @return the data that was removed
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index falls out of the range of the LinkedList");
        }
        if (index == 0) {
            return removeFromFront();
        } else {
            SinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            T removed = curr.getNext().getData();
            curr.setNext(curr.getNext().getNext());
            size--;
            return removed;
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty, so nothing can be removed.");
        }
        if (size == 1) {
            T data = head.getData();
            head = null;
            tail = null;
            size--;
            return data;
        }
        T removed = head.getData();
        head = head.getNext();
        size--;
        return removed;
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty, so nothing can be removed.");
        }
        if (size == 1) {
            tail = null;
            head = null;
        }
        SinglyLinkedListNode<T> curr = head;
        while (curr.getNext().getNext() != null) {
            curr = curr.getNext();
        }
        T removed = curr.getNext().getData();
        curr.setNext(null);
        size--;
        return removed;
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("This index does not exist.");
        }
        if (index == 0) {
            return head.getData();
        } else {
            SinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data must not be null.");
        }
        if (data.equals(tail.getData())) {
            return removeFromBack();
        }
        SinglyLinkedListNode<T> curr = head;
        SinglyLinkedListNode<T> dataFinder = null;
        for (int i = 0; i < size - 1; i++) {
            if (data.equals(curr.getNext().getData())) {
                dataFinder = curr;
            }
            curr = curr.getNext();
        }
        if (data.equals(head.getData()) && dataFinder == null) {
            return removeFromFront();
        }
        if (dataFinder == null) {
            throw new NoSuchElementException("This element was not found.");
        }
        T temp = dataFinder.getNext().getData();
        dataFinder.setNext(dataFinder.getNext().getNext());
        size--;
        return temp;
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] arr = (T[]) new Object[size];
        SinglyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            arr[i] = curr.getData();
            curr = curr.getNext();
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}