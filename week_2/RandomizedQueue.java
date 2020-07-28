import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] it;
    private int N = 0;
    private int count = 0;
    // construct an empty randomized queue
    public RandomizedQueue() {
        it = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (N == it.length-1) {
            resize(2*it.length);
        }
        it[N++] = item;
        count++;
    }

    private void resize(int l) {
        Item []newit = (Item[]) new Object[l];
        int length = (newit.length > it.length)?it.length:newit.length;
        for (int i = 0; i < length; i++) {
            newit[i] = it[i];
        }
        it = newit;
    }

    // remove and return a random item
    public Item dequeue() {
        if (N == 0) {
            throw new java.util.NoSuchElementException();
        }
        int randN = StdRandom.uniform(N);
        Item rit = it[randN];
        N--;
        it[randN] = it[N];
        it[N] = null;
        if (N > 0 && N == it.length/4) resize((it.length/2));
        count--;
        return rit;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (N == 0) {
            throw new java.util.NoSuchElementException();
        }
        int randN = StdRandom.uniform(N);
        return it[randN];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private int n = N;
        public boolean hasNext() {
            return n != 0;
        }

        public Item next() {
            if (n == 0) {
                throw new java.util.NoSuchElementException();
            }
            int randInt = StdRandom.uniform(n);
            Item temp = it[randInt];
            Item rit = it[randInt];
            it[randInt] = it[--n];
            it[n] = temp;
            return rit;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(553);
        rq.enqueue(45);
        rq.isEmpty();
        rq.isEmpty();
        rq.dequeue();

    }

}