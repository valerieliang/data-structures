package linkedlists;

import exceptions.IndexException;
import exceptions.LengthException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of an IndexedList designed for cases where
 * only a few positions have distinct values from the initial value.
 *
 * @param <T> Element type.
 */
public class SparseIndexedList<T> implements IndexedList<T> {

  private final T defaultValue;
  private Node<T> head;
  private int length;

  /**
   * Constructs a new SparseIndexedList of length size
   * with default value of defaultValue.
   *
   * @param size         Length of list, expected: size > 0.
   * @param defaultValue Default value to store in each slot.
   * @throws LengthException if size <= 0.
   */
  public SparseIndexedList(int size, T defaultValue) throws LengthException {
    if (size <= 0) {
      throw new LengthException("Size must be greater than 0");
    }
    length = size;
    this.defaultValue = defaultValue;

    // By default, initialize an empty list
    // It is more common for people to donate the default value (thus not being stored, so this is more efficient)
    head = null;
  }

  /**
   * Returns the length of the SparseIndexedList.
   * Assumes that a valid length was initialized in the constructor
   *
   * @return The length of the SparseIndexedList.
   */
  @Override
  public int length() {
    return length;
  }

  // Checks if an index is valid
  private boolean isValid(int index) {
    return index >= 0 && index < length();
  }

  // Iterates through the existing values in the linked list.
  // Assume that if the value is not in the linked list, it is the default value.
  // Assumes valid index as checked in methods using helper
  private Node<T> find(int index) {
    Node<T> temp = head;
    while (temp != null && temp.position <= index) {
      if (temp.position == index) {
        return temp;
      }
      temp = temp.next;
    }
    return null;
  }

  /**
   * Returns the value of the given position in the SparseIndexedList.
   *
   * @return The value of the SparseIndexedList.
   * @throws IndexException if the index is out of bounds (too low or high)
   */
  @Override
  public T get(int index) throws IndexException {
    if (!isValid(index)) {
      throw new IndexException("Invalid Index");
    }
    // An empty list means all values are default
    if (head == null) {
      return defaultValue;
    }
    // Check if node already exists in sparse list
    Node<T> n = find(index);
    if (n != null) {
      return n.data;
    }
    return defaultValue;
  }

  // Used as a helper method when a non-default valued index returns to a default value
  // Assumes indexes are valid as checked in put()
  private void remove(Node<T> n) {
    if (n == head) {
      head = head.next;
    } else {
      Node<T> current = head;
      while (current.next != null && current.next != n) {
        current = current.next;
      }
      if (current.next != null) {
        current.next = current.next.next;
      }
    }
  }

  // Used as a helper method to add new nodes to Linked List
  // Assumes indexes are valid as checked in put()
  private void add(Node<T> newNode) {
    int index = newNode.position;
    // Updates to beginning, edge case
    if (head == null || head.position >= index) {
      newNode.next = head;
      head = newNode;
      return;
    }
    // If the value is not in the list, add it to its appropriate position
    // Sorting the list makes it easier to iterate over
    Node<T> current = head;
    while (current.next != null && current.next.position < index) {
      current = current.next;
    }
    newNode.next = current.next;
    current.next = newNode;
  }

  /**
   * Change the value at the given position.
   *
   * @param index representing a position in this list.
   * @param value to be written at the given index.
   *              Post: this.get(index) == value
   * @throws IndexException when index < 0 or index >= length.
   */
  @Override
  public void put(int index, T value) throws IndexException {
    if (!isValid(index)) {
      throw new IndexException("Invalid Index");
    }
    // Positions with the default value are excluded from the Sparse List
    Node<T> temp = find(index);
    if (value == defaultValue) {
      if (temp == null) {
        return;
      } else {
        remove(temp);
      }
    }
    // If necessary, add a new node to the sparse list
    Node<T> newNode = new Node<>(index, value);
    add(newNode);
  }

  private static class Node<T> {
    int position;
    T data;
    SparseIndexedList.Node<T> next;

    Node(int pos, T t) {
      position = pos;
      data = t;
      next = null;
    }
  }

  @Override
  public Iterator<T> iterator() {
    return new SparseIndexedListIterator();
  }

  private class SparseIndexedListIterator implements Iterator<T> {
    private SparseIndexedList.Node<T> current;
    private int currIndex;

    SparseIndexedListIterator() {
      current = head;
    }

    @Override
    // Checks that the iterator is in bounds of the list
    public boolean hasNext() {
      return currIndex < length;
    }

    @Override
    public T next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      T t;
      // Returns a non-default value if stored in list
      // Otherwise infers that it's default
      if (current != null && current.position == currIndex) {
        t = current.data;
        current = current.next;
      } else {
        t = defaultValue;
      }
      currIndex++;
      return t;
    }
  }
}
