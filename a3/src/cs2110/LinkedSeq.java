package cs2110;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Assignment metadata
 * Name(s) and NetID(s): Emily Liu (eyl48), Sara Tardif
 * Hours spent on assignment: 10
 */

/**
 * A list of elements of type `T` implemented as a singly linked list.  Null elements are not
 * allowed.
 */
public class LinkedSeq<T> implements Seq<T> {

    /**
     * Number of elements in the list.  Equal to the number of linked nodes reachable from `head`.
     */
    private int size;

    /**
     * First node of the linked list (null if list is empty).
     */
    private Node<T> head;

    /**
     * Last node of the linked list starting at `head` (null if list is empty).  Next node must be
     * null.
     */
    private Node<T> tail;

    /**
     * Assert that this object satisfies its class invariants.
     */
    private void assertInv() {
        assert size >= 0;
        if (size == 0) {
            assert head == null;
            assert tail == null;
        } else {
            assert head != null;
            assert tail != null;
            int count = 1;
            Node<T> hi = head;
            while(hi.next()!=null){
                count++;
                hi=hi.next();
            }
            assert size == count;
            assert hi.equals(tail);
        }
    }

    /**
     * Create an empty list.
     */
    public LinkedSeq() {
        size = 0;
        head = null;
        tail = null;

        assertInv();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void prepend(T elem) {
        assertInv();
        assert elem != null;

        head = new Node<>(elem, head);
        // If list was empty, assign tail as well
        if (tail == null) {
            tail = head;
        }
        size += 1;

        assertInv();
    }

    /**
     * Return a text representation of this list with the following format: the string starts with
     * '[' and ends with ']'.  In between are the string representations of each element, in
     * sequence order, separated by ", ".
     * <p>
     * Example: a list containing 4 7 8 in that order would be represented by "[4, 7, 8]".
     * <p>
     * Example: a list containing two empty strings would be represented by "[, ]".
     * <p>
     * The string representations of elements may contain the characters '[', ',', and ']'; these
     * are not treated specially.
     */
    @Override
    public String toString() {
        Node<T> head2 = head;
        if(head2 == null) {
            return "[]";
        }
        else {
            String str = "[" + head2.data();
            Node<T> current = head2.next();
            for(int i = 0; i < size - 1; i++) {
                str += ", " + current.data();
                current = current.next();

            }
            str += "]";
            return str;
        }
    }

    @Override
    public boolean contains(T elem) {
        if(head==null){
            return false;
        }
        Node<T> n = head;
        while(n!= null) {
            if(n.data().equals(elem)){
                return true;
            }
            n = n.next();
        }
        return false;
    }

    @Override
    public T get(int index) {
        int track = 0;
        Node<T> n = head;
        while(track != index && n.next() != null){
            track = track + 1;
            n = n.next();
        }
        assertInv();
        return n.data();

    }@Override
    public void append(T elem) {
        if(head == null) {
            prepend(elem);
        }
        else {
            tail.setNext(new Node<T>(elem, null));
            tail = tail.next();
            size++;
            assertInv();
        }
    }

    @Override
    public void insertBefore(T elem, T successor) {
        if(head.data().equals(successor)) {
            head = new Node<>(elem, head);
            size = size + 1;
            assertInv();
        }
        else {
            Node<T> current = head.next();
            Node<T> head2 = head;
            for(int i = 0; i < size; i++) {
                if(current.data().equals(successor)) {
                    Node<T> insert = new Node<>(elem, current);
                    head2.setNext(insert);
                    size = size + 1;
                    assertInv();
                    break;
                }
                head2 = head2.next();
                current = current.next();
            }
        }
    }

    @Override
    public boolean remove(T elem) {
        Node<T> current = head.next();
        Node<T> head2 = head;
        if(head.data().equals(elem)) {
            head = new Node<>(current.data(), current);
            size--;
            if(size == 1) {
                tail = head;
            }
            return true;
        }
        for(int i = 0; i < size - 1; i++) {
            if(current.data().equals(elem)) {
                head2 = new Node<>(head2.data(), current.next());
                size--;
                if(size == 1) {
                    tail = head;
                }
                return true;
            }
            head2 = head2.next();
            current = current.next();
        }
        return false;
    }

    /**
     * Return whether this and `other` are `LinkedSeq`s containing the same elements in the same
     * order.  Two elements `e1` and `e2` are "the same" if `e1.equals(e2)`.  Note that `LinkedSeq`
     * is mutable, so equivalence between two objects may change over time.  See `Object.equals()`
     * for additional guarantees.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof LinkedSeq)) {
            return false;
        }
        LinkedSeq otherSeq = (LinkedSeq) other;
        Node<T> currNodeThis = head;
        Node currNodeOther = otherSeq.head;
        int size2 = otherSeq.size;
        if(size2 != size) {
            return false;
        }
        for(int i = 0; i < size; i++) {
            if(!(currNodeThis.data().equals(currNodeOther.data()))) {
                return false;
            }
            currNodeThis = head.next();
            currNodeOther = otherSeq.head.next();
        }
        return true;
    }

    /*
     * There is no need to read the remainder of this file for the purpose of completing the
     * assignment.  We have not yet covered the implementation of these concepts in class.
     */

    /**
     * Returns a hash code value for the object.  See `Object.hashCode()` for additional
     * guarantees.
     */
    @Override
    public int hashCode() {
        // Whenever overriding `equals()`, must also override `hashCode()` to be consistent.
        // This hash recipe is recommended in _Effective Java_ (Joshua Bloch, 2008).
        int hash = 1;
        for (T e : this) {
            hash = 31 * hash + e.hashCode();
        }
        return hash;
    }

    /**
     * Return an iterator over the elements of this list (in sequence order).  By implementing
     * `Iterable`, clients can use Java's "enhanced for-loops" to iterate over the elements of the
     * list.  Requires that the list not be mutated while the iterator is in use.
     */
    @Override
    public Iterator<T> iterator() {
        assertInv();

        // Return an instance of an anonymous inner class implementing the Iterator interface.
        // For convenience, this uses Java features that have not eyt been introduced in the course.
        return new Iterator<>() {
            private Node<T> next = head;

            public T next() throws NoSuchElementException {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T result = next.data();
                next = next.next();
                return result;
            }

            public boolean hasNext() {
                return next != null;
            }
        };
    }
}
