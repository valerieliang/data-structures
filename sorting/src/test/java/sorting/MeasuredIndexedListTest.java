package sorting;

import exceptions.IndexException;
import sorting.list.MeasuredIndexedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeasuredIndexedListTest {

  private static final int LENGTH = 15;
  private static final int DEFAULT_VALUE = 3;

  private MeasuredIndexedList<Integer> measuredIndexedList;

  @BeforeEach
  void setup() {
    measuredIndexedList = new MeasuredIndexedList<>(LENGTH, DEFAULT_VALUE);
  }

  @Test
  @DisplayName("MeasuredIndexedList starts with zero reads")
  void zeroReadsStart() {
    assertEquals(0, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexedList starts with zero writes")
  void zeroWritesStart() {
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("MeasuredIndexList accesses() returns the number of time the list was read")
  void accessesReturnsNumberOfGets() {
    int gotten = measuredIndexedList.get(1);
    assertEquals(1, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexList get() increases the number of reads by one each time it is called")
  void getIncrementsReadsValue()
  {
    int gotten = measuredIndexedList.get(1);
    assertEquals(1, measuredIndexedList.accesses());
    gotten = measuredIndexedList.get(3);
    assertEquals(2, measuredIndexedList.accesses());
    gotten = measuredIndexedList.get(LENGTH-1);
    assertEquals(3, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexList get() does not increase the number of reads if an exception is thrown")
  void getExceptionDoesNotIncrementsReadsValue()
  {
    int gotten = measuredIndexedList.get(1);
    gotten = measuredIndexedList.get(3);
    int numAccesses = measuredIndexedList.accesses();
    try {
      gotten = measuredIndexedList.get(LENGTH);
    } catch(IndexException ex) {
      assertEquals(numAccesses, measuredIndexedList.accesses());
      // Compares number accesses with prior number to ensure that it was not changed
      // Assume read counts works properly from above tests to isolate testing when an exception is called
    }
  }

  @Test
  @DisplayName("MeasuredIndexList reset() turns the number of reads to zero when it is called")
  void resetSetsReadsToZero()
  {
    int gotten = measuredIndexedList.get(1);
    gotten = measuredIndexedList.get(3);
    gotten = measuredIndexedList.get(LENGTH-1);
    // Assume read counts works properly from above tests to isolate testing reset
    measuredIndexedList.reset();
    assertEquals(0, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexList mutations() returns the number of time the list was written to")
  void mutationsReturnsNumberOfGets() {
    measuredIndexedList.put(1,22);
    assertEquals(1, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("MeasuredIndexList put() increases the number of writes by one each time it is called")
  void putIncrementsWritesValue(){
    measuredIndexedList.put(1, -30);
    assertEquals(1, measuredIndexedList.mutations());
    measuredIndexedList.put(2,400);
    assertEquals(2, measuredIndexedList.mutations());
    measuredIndexedList.put(LENGTH-1,34);
    assertEquals(3, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("MeasuredIndexList put() increments number of writes even if the value of the index does not change")
  void puttingDefaultValuesStillIncrement() {
    measuredIndexedList.put(1, 3);
    assertEquals(1, measuredIndexedList.mutations());
    measuredIndexedList.put(2, 3);
    assertEquals(2, measuredIndexedList.mutations());
    measuredIndexedList.put(3, 3);
    assertEquals(3, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("MeasuredIndexList put() does not increase the number of writes if an exception is thrown")
  void putExceptionDoesNotIncrementsReadsValue() {
    measuredIndexedList.put(1, 3);
    measuredIndexedList.put(2, 400);
    int numMutations = measuredIndexedList.mutations();
    try {
      measuredIndexedList.put(LENGTH,99);
    } catch(IndexException ex) {
      assertEquals(numMutations, measuredIndexedList.mutations());
      // Compares number accesses with prior number to ensure that it was not changed
      // Assume write counts works properly from above tests to isolate testing when an exception is called
    }
  }

  @Test
  @DisplayName("MeasuredIndexList reset() turns the number of writes to zero when it is called")
  void resetSetsWritesToZero()
  {
    measuredIndexedList.put(1, 3);
    measuredIndexedList.put(2,400);
    measuredIndexedList.put(LENGTH-1,34);
    // Assume write counts works properly from above tests to isolate testing reset
    measuredIndexedList.reset();
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("MeasuredIndexList get() does not affect the number of writes (only reads)")
  void getIndependentOfPut() {
    int gotten = measuredIndexedList.get(1);
    assertEquals(1, measuredIndexedList.accesses());
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("MeasuredIndexList put() does not affect the number of reads (only writes)")
  void putIndependentOfGet() {
    measuredIndexedList.put(1, 3);
    assertEquals(1, measuredIndexedList.mutations());
    assertEquals(0, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexList count() method increases number of reads by its number of elements")
  void countIncreasesReadsByLength() {
    int counted = measuredIndexedList.count(3);
    assertEquals(LENGTH, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexList count() does not affect the number of mutations (only accesses)")
  void countOnlyAffectsAccesses() {
    measuredIndexedList.put(1, 1);
    measuredIndexedList.put(2, 1);
    //Assume put works properly (see past tests) to change writes to isolate tests for count()
    int counted = measuredIndexedList.count(3);
    assertEquals(LENGTH, measuredIndexedList.accesses());
    assertEquals(2, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("MeasuredIndexList count() method returns number of elements of the same value as input")
  void countRecordsCorrectNumOfElements() {
    int counted = measuredIndexedList.count(3);
    assertEquals(LENGTH, counted);
  }

  @Test
  @DisplayName("MeasuredIndexList count() works even when value is not in the list")
  void countReturnsZeroForValuesNotInList() {
    int counted = measuredIndexedList.count(99999);
    assertEquals(0, counted);
  }

  @Test
  @DisplayName("MeasuredIndexList count() displays correct value for modified list")
  void countWorksForModifiedLists(){
    measuredIndexedList.put(1, 1);
    measuredIndexedList.put(2, 1);
    measuredIndexedList.put(3, 1);
    //Assume put/writes work properly (past tests) to test the count() when the list is modified
    int counted = measuredIndexedList.count(3);
    assertEquals(12, counted);
    counted = measuredIndexedList.count(1);
    assertEquals(3, counted);
  }

  @Test
  @DisplayName("MeasuredIndexList iterator does not affect reads and writes")
  void iteratorDoesNotAffectReadsAndWrites() {
    measuredIndexedList.put(1, 3);
    int gotten = measuredIndexedList.get(1);
    // Assume put and get work as expected from previous tests to isolate checking the iterator

    for (Integer elements : measuredIndexedList ){
      // For-each loop in Java implicitly calls the iterator
      assertEquals(1, measuredIndexedList.mutations());
      assertEquals(1, measuredIndexedList.accesses());
    }
  }
}
