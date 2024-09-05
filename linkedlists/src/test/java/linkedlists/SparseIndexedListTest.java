package linkedlists;

import exceptions.LengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SparseIndexedListTest extends IndexedListTest {

  @Override
  public IndexedList<Integer> createArray() {
    return new SparseIndexedList<>(LENGTH, INITIAL);
  }

  @Test
  @DisplayName("constructor throws exception if length is below the valid range")
  void testConstructorWithLengthBelowRangeThrowsException() {
    try {
      ArrayIndexedList<Integer> list = new ArrayIndexedList<>(-1,6);
      fail("LengthException was not thrown for size < 1");
    } catch (LengthException ex) {
      return;
    }
  }

  @Test
  @DisplayName("Iterator test after with explicit call to object.")
  void testIteratorAfterIndexedListIsInstantiated() {
    SparseIndexedList<Character> sparse = new SparseIndexedList<>(3,'z');
    sparse.put(0, 'a');
    sparse.put(1, 'b');
    sparse.put(2, 'c');

    Iterator<Character> iterator = sparse.iterator();

    assertEquals(true,iterator.hasNext());
    assertEquals('a', iterator.next());
    assertEquals(true,iterator.hasNext());
    assertEquals('b', iterator.next());
    assertEquals(true,iterator.hasNext());
    assertEquals('c', iterator.next());
    assertEquals(false,iterator.hasNext());
  }

  @Test
  @DisplayName("throws NoSuchElementException if iterator calls next() when there is no node.")
  void testIteratorThrowsExceptionForInvalidNext() {
    SparseIndexedList<Character> sparse = new SparseIndexedList<>(3,'z');
    sparse.put(0, 'a');
    sparse.put(1, 'b');

    Iterator<Character> iterator = sparse.iterator();

    assertEquals(true,iterator.hasNext());
    assertEquals('a', iterator.next());
    assertEquals(true,iterator.hasNext());
    assertEquals('b', iterator.next());
    assertEquals(true,iterator.hasNext());
    assertEquals('z', iterator.next());
    assertEquals(false,iterator.hasNext());
    try {
      iterator.next();
      fail("NoSuchElementException was not thrown for going next()");
    } catch (NoSuchElementException ex) {
      return;
    }
  }


}