/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import adt.ListInterface;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author User
 */
public class DoublyList<T> implements ListInterface<T> {

    private Node firstNode;
    private Node lastNode;

    public DoublyList() {
        firstNode = null;
    }

    @Override
    public T remove(int i) {
        Node node = null;
        int index = 0;
        node = firstNode;

        while (index < i) {
            if (node.next == null) {
                return null;
            }
            node = node.next;
            index++;
        }
        if (node.previous == null) {
            firstNode = node.next;
        }
        if (node.next == null) {
            lastNode = node.previous;
        }

        if (node.next.previous != null) {
            node.next.previous = node.previous;
        }
        if (node.next.previous != null) {
            node.previous.next = node.next;
        }
        return node.data;
    }

    @Override
    public T remove(T newEntry) {
        Node node = null;
        int index = 0;
        node = firstNode;

        while (!node.data.equals(newEntry)) {
            if (node.next == null) {
                return null;
            }
            node = node.next;
            index++;
        }
        if (node.previous == null) {
            firstNode = node.next;
        }
        if (node.next == null) {
            lastNode = node.previous;
        }

        if (node.next.previous != null) {
            node.next.previous = node.previous;
        }
        if (node.next.previous != null) {
            node.previous.next = node.next;
        }
        return node.data;
    }

    @Override
    public boolean add(T newEntry) {
        if (firstNode == null) {
            Node node = new Node(newEntry);
            firstNode = node;
            lastNode = node;
        } else {
            Node node = new Node(newEntry, null, lastNode);
            lastNode.next = node;
            lastNode = node;
        }
        return true;
    }

    @Override
    public boolean add(int newPosition, T newEntry) {
        int index = 0;
        Node node = firstNode;
        while (index < newPosition) {
            if (node.next == null) {
                return false;
            }
            node = node.next;
            index++;
        }
        Node newNode = new Node(newEntry, node, node.previous);
        node.previous.next = newNode;
        node.previous = newNode;
        lastNode = node;
        return true;
    }

    @Override
    public void clear() {
        lastNode = null;
        firstNode = null;
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        int index = 0;
        Node node = firstNode;

        while (index < givenPosition) {
            if (node.next == null) {
                return false;
            }
            node = node.next;
            index++;
        }
        node = new Node(newEntry, node.next, node.previous);
        return true;

    }

    @Override
    public T getEntry(int givenPosition) {
        int index = 0;
        Node node = firstNode;
        if (isEmpty()) {
            return null;
        }
        while (index < givenPosition) {
            if (node.next == null) {
                return null;
            }
            node = node.next;
            index++;
        }
        return node.data;

    }

    @Override
    public boolean contains(T anEntry) {
        Node node = firstNode;

        while (node != null) {
            if (node.data.equals(anEntry)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int getNumberOfEntries() {
        int index = 0;
        Node node = firstNode;
        while (node != null) {
            node = node.next;
            index++;
        }
        return index;
    }

    @Override
    public boolean isEmpty() {
        return firstNode == null && lastNode == null;
    }

    @Override
    public boolean isFull() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class Node implements Serializable {

        private Node previous;
        private T data;
        private Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.previous = null;
        }

        public Node(T data, Node next, Node previous) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }

    }

    private class MyIterator implements Iterator<T> {

        private int nextIndex;

        public MyIterator() {
            nextIndex = 0;
        }

        @Override
        public boolean hasNext() {
            Object ss = getEntry(nextIndex);
            return getEntry(nextIndex) != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                return getEntry(nextIndex++);
            } else {
                throw new NoSuchElementException("Next Element is not found!");
            }
        }

    }

    public static void main(String[] args) {
        ListInterface<Integer> list = new DoublyList<>();
//        list.add(0);
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//
//        list.add(2, 50);
        Iterator<Integer> tsss = list.iterator();

        while (tsss.hasNext()) {
            System.out.println(tsss.next());
        }

        String s = "";

    }

}
