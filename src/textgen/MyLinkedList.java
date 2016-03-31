package textgen;

import java.util.AbstractList;


/**
 * A class that implements a doubly linked list
 *
 * @param <E> The type of the elements stored in the list
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MyLinkedList<E> extends AbstractList<E> {
    LLNode<E> head;
    LLNode<E> tail;
    int size;

    /**
     * Create a new empty LinkedList
     */
    public MyLinkedList() {
        size = 0;
        head = new LLNode<>(null);
        tail = new LLNode<>(null);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Appends an element to the end of the list
     *
     * @param element The element to add
     */
    public boolean add(E element) {
        LLNode<E> toAdd = new LLNode<E>(element, tail.prev, tail);
        tail.prev.next = toAdd;
        tail.prev = toAdd;
        size++;
        return true;
    }

    /**
     * Get the element at position index
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).data;
    }

    /**
     * Add an element to the list at the specified index
     * <p>
     * The index where the element should be added
     *
     * @param element The element to add
     */
    public void add(int index, E element) {
        if (index >= size || index < 0) throw new IndexOutOfBoundsException();

        if (element == null) throw new NullPointerException();

        LLNode<E> toMove = getNode(index);

        LLNode<E> toInsert = new LLNode<>(element, toMove.prev, toMove);

        toMove.prev.next = toInsert;
        toMove.prev = toInsert;

        size++;
    }


    /**
     * Return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Remove a node at the specified index and return its data element.
     *
     * @param index The index of the element to remove
     * @return The data element removed
     * @throws IndexOutOfBoundsException If index is outside the bounds of the list
     */
    public E remove(int index) {
        if (index >= size || index < 0) throw new IndexOutOfBoundsException();

        LLNode<E> toRemove = getNode(index);

        toRemove.next.prev = toRemove.prev;
        toRemove.prev.next = toRemove.next;
        size--;
        return toRemove.data;
    }

    /**
     * Set an index position in the list to a new element
     *
     * @param index   The index of the element to change
     * @param element The new element
     * @return The element that was replaced
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E set(int index, E element) {
        if (index >= size || index < 0) throw new IndexOutOfBoundsException();

        if (element == null) throw new NullPointerException();

        LLNode<E> toSet = getNode(index);

        LLNode<E> in = new LLNode<>(element, toSet.prev, toSet.next);

        toSet.prev.next = in;
        toSet.next.prev = in;

        return toSet.data;
    }

    private LLNode<E> getNode(int index) {
        LLNode<E> ans = head.next;
        for (int i = 0; i < index; i++) {
            ans = ans.next;
        }
        return ans;
    }
}

class LLNode<E> {
    LLNode<E> prev;
    LLNode<E> next;
    E data;

    // E.g. you might want to add another constructor
    public LLNode(E e, LLNode prev, LLNode next) {
        this.data = e;
        this.prev = prev;
        this.next = next;
    }

    public LLNode(E e) {
        this.data = e;
        this.prev = null;
        this.next = null;
    }

}
