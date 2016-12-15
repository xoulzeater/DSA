/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author User
 */
import adt.SortedListInterface;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author User
 * @param <T>
 */
public class SortedLists<T extends Comparable<? super T>> implements SortedListInterface<T> {

    private Node firstNode;
    private int length;

    public SortedLists() {
        firstNode = null;
        length = 0;
    }

    @Override
    public boolean add(T newEntry) {
        Node newNode = new Node(newEntry);

        Node nodeBefore = null;							// For linked list traversal: to reference the node before the current node
        Node currentNode = firstNode;				// For linked list traversal: to reference the current node
        while (currentNode != null && newEntry.compareTo(currentNode.data) > 0) {
            nodeBefore = currentNode;
            currentNode = currentNode.next;
        }

        if (isEmpty() || (nodeBefore == null)) { // CASE 1: add at beginning
            newNode.next = firstNode;
            firstNode = newNode;
        } else {	// CASE 2: add in the middle or at the end, i.e. after nodeBefore
            newNode.next = currentNode;
            nodeBefore.next = newNode;
        }
        length++;
        return true;
    }

    @Override
    public boolean remove(T anEntry) {

        Node nodeBefore = null;				
        Node currentNode = firstNode;				
        while (currentNode != null && anEntry.compareTo(currentNode.data) != 0) {
            nodeBefore = currentNode;
            currentNode = currentNode.next;
        }
        if (currentNode == null) {
            return false;
        }

        if (isEmpty() || (nodeBefore == null)) { 
            firstNode = currentNode;
        } else {	
            nodeBefore.next = currentNode.next;
        }
        length--;
        return true;
    }

    @Override
    public int getPosition(T anEntry) {
        throw new UnsupportedOperationException();	// Left as Practical exercise
    }

    @Override
    public T getEntry(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= length)) {
            Node currentNode = firstNode;
            for (int i = 0; i < givenPosition - 1; ++i) {
                currentNode = currentNode.next;		// advance currentNode to next node
            }
            result = currentNode.data;	// currentNode is pointing to the node at givenPosition
        }

        return result;
    }

    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        Node currentNode = firstNode;

        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.data)) {
                found = true;
            } else {
                currentNode = currentNode.next;
            }
        }

        return found;
    }

    @Override
    public T remove(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= length)) {
            if (givenPosition == 1) {      	// CASE 1: remove first entry
                result = firstNode.data;     	// save entry to be removed 
                firstNode = firstNode.next;		// update firstNode to point to the next node
            } else {                         	// CASE 2: remove interior entry or last entry
                Node nodeBefore = firstNode;
                for (int i = 1; i < givenPosition - 1; ++i) {
                    nodeBefore = nodeBefore.next;		// advance nodeBefore to its next node
                }
                result = nodeBefore.next.data;  	// save entry to be removed	
                nodeBefore.next = nodeBefore.next.next;	// make node before point to node after the 
            } 															// one to be deleted (to disconnect node from chain)

            length--;
        }

        return result;
    }

    @Override
    public final void clear() {
        firstNode = null;
        length = 0;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return (length == 0);
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public String toString() {
        String outputStr = "";
        Node currentNode = firstNode;
        while (currentNode != null) {
            outputStr += currentNode.data + "\n";;
            currentNode = currentNode.next;
        }
        return outputStr;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class Node {

        private T data;
        private Node next;

        private Node(T dataPortion) {
            data = dataPortion;
            next = null;
        }

        private Node(T dataPortion, Node nextNode) {
            data = dataPortion;
            next = nextNode;
        }
    }

    private class ListIterator implements Iterator<T> {

        private int nextIndex;

        public ListIterator() {
            nextIndex = 1;
        }

        @Override
        public boolean hasNext() {
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

}
