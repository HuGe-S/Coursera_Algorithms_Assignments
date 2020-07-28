
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque <Item> implements Iterable<Item> {
    // construct an empty deque
    private Node node_first = null;
    private Node node_last = null;
    private int count = 0;
    private class Node {
        private Item it;
        private Node next;
        private Node last;
    }
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return count==0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        count++;
        Node nf = new Node();
        nf.it = item;
        nf.next = node_first;
        if (node_first != null) {
            node_first.last = nf;
        } else {
            node_last = nf;
        }
        nf.last = null;
        node_first = nf;
    }

    // add the item to the back
    public void addLast(Item item) {
        count++;
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node nl = new Node();
        nl.it = item;
        nl.last = node_last;
        if (node_last != null) {
            node_last.next = nl;
        } else {
            node_first = nl;
        }
        nl.next = null;
        node_last = nl;
    }


    // remove and return the item from the front
    public Item removeFirst() {
        count--;
        if (node_first == null) {
            throw new java.util.NoSuchElementException();
        }
        Item rf = node_first.it;
        if (node_first.next != null) {
            node_first.next.last = null;
        } else {
            node_last = null;
        }
        node_first = node_first.next;
        return rf;


    }

    // remove and return the item from the back
    public Item removeLast() {
        count--;
        if (node_last == null) {
            throw new java.util.NoSuchElementException();
        }
        Item rl = node_last.it;
        if (node_last.last != null) {
            node_last.last.next = null;
        } else {
            node_first = null;
        }
        node_last = node_last.last;
        return rl;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new FirstToLastIterator();
    }

    private class FirstToLastIterator implements Iterator<Item> {
        private Node node = node_first;

        public boolean hasNext() {
            return node != null;
        }

        public Item next() {
            try {
                Item it = node.it;
                node = node.next;
                return it; } catch (NullPointerException e) {
                throw new java.util.NoSuchElementException();

            }

        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        d.addFirst("a");
        d.addLast("b");
        d.addLast("c");
        d.addFirst("A");
        d.addLast("C");
        StdOut.println(d.size());
        StdOut.println(d.isEmpty());
        d.removeFirst();
        d.removeLast();
        for (String s:d
             ) {
            StdOut.println(s);
            
        }
    }
}
