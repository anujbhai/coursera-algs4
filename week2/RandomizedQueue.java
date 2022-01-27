/* *****************************************************************************
 *  Name: Anuj Upadhyay
 *  Date: 26 January, 2022
 *  Description: a generic data type "RandomizedQueue" where the item removed is
 * chosen uniformly at random among items in the data structure
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 1;
    private int first;
    private int last;
    private Item[] q;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = 0;
        last = 0;
        q = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    public void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < n; i++) {
            copy[i] = q[(first + i) % q.length];
        }

        q = copy;
        first = 0;
        last = n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == q.length) {
            resize(2 * q.length);
        }

        q[last++] = item;

        if (last == q.length) {
            last = 0;
        }

        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = q[first];
        q[first] = null;

        n--;
        first++;

        if (first == q.length) {
            first = 0;
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = q[StdRandom.uniform(n)];

        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    public class RandomIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = q[(i + first) % q.length];
            i++;

            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queueStr = new RandomizedQueue<String>();

        StdOut.println("Test case 1: queue is empty - " + queueStr.isEmpty());
        StdOut.println("Test case 2: queue size - " + queueStr.size());

        queueStr.enqueue("Red");
        queueStr.enqueue("Blue");
        queueStr.enqueue("Orange");
        queueStr.enqueue("Gray");
        queueStr.enqueue("Gold");

        StdOut.println("Test case 3: queue is not empty - " + queueStr.isEmpty());
        StdOut.println("Test case 4: queue size - " + queueStr.size());

        StdOut.println("Test case 5: saple random item from queue - " + queueStr.sample());

        queueStr.dequeue();

        StdOut.println("Test case 6: queue size - " + queueStr.size());

        for (String i : queueStr) {
            StdOut.println(i);
        }
    }
}
