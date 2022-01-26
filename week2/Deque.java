/* *****************************************************************************
 *  Name: Anuj Upadhyay
 *  Date: 26 January, 2022
 *  Description: A double-ended queue or deque (pronounced deck) is a
 * generalization of a stack and a queue that supports adding and removing items
 * from either the front or the back of the data structure.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null || last == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> oldfirst = new Node<Item>();
        oldfirst.item = item;

        if (first == null) {
            first = oldfirst;
            last = oldfirst;
        }
        else {
            oldfirst.next = first;
            first.prev = oldfirst;
            first = oldfirst;
        }

        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> oldlast = new Node<Item>();
        oldlast.item = item;

        if (first == null) {
            first = oldlast;
            last = oldlast;
        }
        else {
            last.next = oldlast;
            oldlast.prev = last;
            last = oldlast;
        }

        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Dequeue underflow");
        }

        Item item = first.item;
        first = first.next;

        n--;

        if (isEmpty()) {
            last = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Dequeue underflow");
        }

        Item item = last.item;
        last.item = null;

        if (n > 1) {
            last = last.prev;
            last.next = null;
        }
        else {
            first = null;
            last = null;
        }

        n--;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;

            return item;
        }
    }

    // unit testing (required
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();

        deque.addFirst("item1");
        deque.addFirst("item2");
        deque.addFirst("item3");

        deque.addLast("item101");
        deque.addLast("item102");
        deque.addLast("item103");

        deque.removeFirst();
        deque.removeFirst();
        StdOut.println(deque.removeFirst());

        deque.removeLast();
        deque.removeLast();

        for (String i : deque) {
            StdOut.println(i);
        }
    }
}
