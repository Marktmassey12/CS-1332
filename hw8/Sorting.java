import java.util.*;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (comparator == null || arr == null) {
            throw new IllegalArgumentException("Array or comparator "
                    + "cannot be null!");
        }

        for (int i = 1; i < arr.length; i++) {
            T curr = arr[i];
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], curr) > 0) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = curr;
        }
    }

    /**
     * Implement cocktail sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array and comparator cannot be null.");
        }
        int beg = 0;
        int end = arr.length - 1;
        int swap = beg;
        while (beg < end) {
            swap = beg;
            for (int i = beg; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swap = i;
                }
            }
            end = swap;
            for (int i = end; i > beg; i--) {
                if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                    T temp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = temp;
                    swap = i;
                }
            }
            beg = swap;
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array and comparator cannot be null.");
        }
        if (arr.length == 0) {
            return;
        }
        if (arr.length == 1) {
            return;
        }
        int len = arr.length;
        int midInd = len / 2;
        T[] lArr = (T[]) new Object[midInd];
        T[] rArr = (T[]) new Object[len - midInd];
        for (int i = 0; i < midInd; i++) {
            lArr[i] = arr[i];
        }
        int count = 0;
        for (int i = midInd; i < len; i++) {
            rArr[count] = arr[i];
            count++;
        }
        mergeSort(lArr, comparator);
        mergeSort(rArr, comparator);
        int leftInd = 0;
        int rightInd = 0;
        int currInd = 0;
        while (leftInd < midInd && rightInd < len - midInd) {
            if (comparator.compare(lArr[leftInd], rArr[rightInd]) <= 0) {
                arr[currInd] = lArr[leftInd];
                leftInd = leftInd + 1;
            } else {
                arr[currInd] = rArr[rightInd];
                rightInd = rightInd + 1;
            }
            currInd = currInd + 1;
        }
        while (leftInd < midInd) {
            arr[currInd] = lArr[leftInd];
            currInd++;
            leftInd++;
        }
        while (rightInd < len - midInd) {
            arr[currInd] = rArr[rightInd];
            currInd++;
            rightInd++;
        }
    }

    /**
     * Implement quick sort.
     * <p>
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     * <p>
     * int pivotIndex = rand.nextInt(b - a) + a;
     * <p>
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     * <p>
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Array and comparator cannot be null.");
        }
        if (arr.length == 0) {
            return;
        }

        qHelp(arr, 0, arr.length, comparator, rand);
    }

    public static <T> void qHelp(T[] arr, int beg, int end, Comparator<T> comparator,
                                 Random rand) {
        if (beg >= end - 1) {
            return;
        }
        int pivotIndex = rand.nextInt(end - beg) + beg;
        T pivot = arr[pivotIndex];
        T first = arr[beg];
        arr[beg] = pivot;
        arr[pivotIndex] = first;
        int i = beg + 1;
        int j = end - 1;
        while (i <= j) {
            if (arr[i] == null) {
                continue;
            }
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                i++;
            }
            if (arr[j] == null) {
                continue;
            }
            while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                j--;
            }
            if (i <= j) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        T tempStart = arr[beg];
        arr[beg] = arr[j];
        arr[j] = tempStart;
        qHelp(arr, beg, j, comparator, rand);
        qHelp(arr, j + 1, end, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     * <p>
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * <p>
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot sort null array.");
        }
        if (arr.length == 0) {
            return;
        }
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        int longest = 0;
        for (int num : arr) {
            if (num > longest) {
                longest = num;
            }
        }
        int numLong = 0;
        while (longest > 0) {
            longest = longest / 10;
            numLong++;
        }
        int div = 1;
        for (int i = 0; i < numLong; i++) {
            for (int j = 0; j < arr.length; j++) {
                int numAdd = arr[j] / div;
                if (buckets[numAdd % 10 + 9] == null) {
                    buckets[numAdd % 10 + 9] = new LinkedList<>();
                }
                buckets[numAdd % 10 + 9].add(numAdd);
            }
            div *= 10;
            int index = 0;
            for (LinkedList<Integer> buck : buckets) {
                if (buck != null) {
                    for (int num : buck) {
                        arr[index++] = num;
                    }
                    buck.clear();
                }
            }
        }
    }


    /**
     * Implement heap sort.
     * <p>
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     * <p>
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     * <p>
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot sort null data.");
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(data);
        int[] sorted = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            sorted[i] = queue.remove();
        }
        return sorted;
    }
}
