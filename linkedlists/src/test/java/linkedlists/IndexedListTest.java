package linkedlists;

import exceptions.IndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit Tests for any class implementing the IndexedList interface.
 */
public abstract class IndexedListTest {
  protected static final int LENGTH = 10;
  protected static final int INITIAL = 7;
  private IndexedList<Integer> indexedList;

  public abstract IndexedList<Integer> createArray();

  @BeforeEach
  public void setup() {
    indexedList = createArray();
  }

  @Test
  @DisplayName("get() returns the default value after IndexedList is instantiated.")
  void testGetAfterConstruction() {
    for (int index = 0; index < indexedList.length(); index++) {
      assertEquals(INITIAL, indexedList.get(index));
    }
  }

  @Test
  @DisplayName("get() throws exception if index is below the valid range.")
  void testGetWithIndexBelowRangeThrowsException() {
    try {
      indexedList.get(-1);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      return;
    }
  }

  // TODO Add more tests!

  // Tests for constructors are in the implemented class test files
  // If create array works properly, it confirms that the constructor works

  @Test
  @DisplayName("get() throws an exception if index is above the valid range.")
  void testGetWithIndexAboveRangeThrowsException() {
    try {
      indexedList.get(LENGTH);
      fail("IndexException was not thrown for index >= list size/length");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() throws exception if index is below the valid range.")
  void testPutWithIndexBelowRangeThrowsException() {
    try {
      indexedList.put(-1, 34);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() throws exception if index is below the valid range.")
  void testPutWithIndexAboveRangeThrowsException() {
    try {
      indexedList.put(LENGTH, 34);
      fail("IndexException was not thrown for index >= length");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() works when adding non-default values")
  void testPutWithValidIndexAndNonDefaultValue() {
    indexedList.put(3, 100);
    assertEquals(100, indexedList.get(3));
  }

  @Test
  @DisplayName("put() overwrites the existing value at given index to provided value.")
  void testPutUpdatesValueAtGivenIndex() {
    indexedList.put(3, 100);
    assertEquals(100, indexedList.get(3));
    indexedList.put(3, 20);
    assertEquals(20, indexedList.get(3));
  }

  @Test
  @DisplayName("put() overwrites the existing value at given index to provided value.")
  void testPutUpdatesValueAtGivenIndexToDefault() {
    indexedList.put(3, 100);
    assertEquals(100, indexedList.get(3));
    indexedList.put(3, INITIAL);
    assertEquals(INITIAL, indexedList.get(3));
  }

  @Test
  @DisplayName("length() returns the correct size after lists are instantiated.")
  void testLengthAfterConstruction() {
    assertEquals(LENGTH, indexedList.length());
  }

  @Test
  @DisplayName("test that the iterator travels over every element correctly (for each loop).")
  void testEnhancedLoopAfterConstruction() {
    int counter = 0;
    for (int element : indexedList) {
      assertEquals(INITIAL, element);
      counter++;
    }
    assertEquals(LENGTH, counter);
  }

  // Other tests for iterator in are included in test class for the specified containers
}
