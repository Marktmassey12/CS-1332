import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index does not exist.");
        } else if (data == null) {
            throw new IllegalArgumentException("Entry data is null.");
        } else if (index == 0) {
            addToFront(data);
        } else if (size == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
            return;
        } else {
            if (index < size / 2) {
                int i = 0;
                DoublyLinkedListNode<T> curr = head;
                while (i != index) {
                    curr = curr.getNext();
                    i++;
                }
                DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data,
                        curr.getPrevious(), curr);
                curr.getPrevious().setNext(newNode);
                curr.setPrevious(newNode);
                size++;
            } else {
                int i = size - 1;
                DoublyLinkedListNode<T> curr = tail;
                while (i != index) {
                    curr = curr.getPrevious();
                    i--;
                }
                DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data,
                        curr.getPrevious(), curr);
                curr.getPrevious().setNext(newNode);
                curr.setPrevious(newNode);
                size++;
            }
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
        } else if (size == 0) {
            head = new DoublyLinkedListNode<T>(data);
            tail = head;
            size++;
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, null, head);
            head.setPrevious(newNode);
            head = newNode;
            size++;
        }
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
        } else if (size == 0) {
            addToFront(data);
        } else if (head == null) {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            head = new DoublyLinkedListNode<>(data);
            tail = head;
            size++;
            return;
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, tail, null);
            tail.setNext(newNode);
            tail = newNode;
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index must be in range from"
                    + "[0, " + size + ").");
        } else if (isEmpty()) {
            return null;
        } else if (index == size - 1) {
            return removeFromBack();
        } else if (index == 0) {
            return removeFromFront();
        }
        if (index > size / 2) {
            int i = size - 1;
            DoublyLinkedListNode<T> curr = tail;
            while (i != index) {
                curr = curr.getPrevious();
                i--;
            }
            curr.getPrevious().setNext(curr.getNext());
            curr.getNext().setPrevious(curr.getPrevious());
            size--;
            return curr.getData();
        } else {
            int i = 0;
            DoublyLinkedListNode<T> curr = head;
            while (i != index) {
                curr = curr.getNext();
                i++;
            }
            curr.getPrevious().setNext(curr.getNext());
            curr.getNext().setPrevious(curr.getPrevious());
            size--;
            return curr.getData();
        }
    }

    /**
     * Removes and returns the first element of the list.
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
        T removed = head.getData();
        if (size == 1) {
            clear();
            return removed;
        } else {
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return removed;
        }
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty, so nothing can be removed.");
        }
        T removed = tail.getData();
        if (size == 1) {
            clear();
            return removed;
        } else {
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return removed;
        }
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index must be "
                    + "between [0, " + size + ").");
        } else if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else if (index < size / 2) {
            int i = 0;
            DoublyLinkedListNode<T> curr = head;
            while (i != index) {
                curr = curr.getNext();
                i++;
            }
            return curr.getData();
        } else {
            int i = size - 1;
            DoublyLinkedListNode<T> curr = tail;
            while (i != index) {
                curr = curr.getPrevious();
                i--;
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
     * Must be O(1) if data is in the tail and O(n) for all other cases.
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
        DoublyLinkedListNode<T> curr = head;
        DoublyLinkedListNode<T> dataFinder = null;
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
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        T[] arr = (T[]) new Object[size];
        DoublyLinkedListNode<T> curr = head;
        int index = 0;
        while (curr.getNext() != null) {
            arr[index] = curr.getData();
            index++;
            curr = curr.getNext();
        }
        arr[size - 1] = curr.getData();
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
    public DoublyLinkedListNode<T> getHead() {
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
    public DoublyLinkedListNode<T> getTail() {
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
