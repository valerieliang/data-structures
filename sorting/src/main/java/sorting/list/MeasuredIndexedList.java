package sorting.list;

import exceptions.IndexException;

/**
 * An ArrayIndexedList that is able to report the number of
 * accesses and mutations, as well as reset those statistics.
 *
 * @param <T> The type of the array.
 */
public class MeasuredIndexedList<T> extends ArrayIndexedList<T>
    implements Measured<T> {

  private int reads;
  private int writes;

  /**
   * Constructor for a MeasuredIndexedList.
   * Sets the number of accesses and number of mutations so far to zero.
   * It takes a size and a default value for the list.
   *
   * @param size         The size of the array.
   * @param defaultValue The initial value to set every object to in the array.
   */
  public MeasuredIndexedList(int size, T defaultValue) {
    super(size, defaultValue);
    reads = 0;
    writes = 0;
  }

  /**
   * Get the declared capacity of this list.
   *
   * @return the length
   *          Inv: length() >= 0
   */
  @Override
  public int length() {
    return super.length();
  }

  /**
   * Retrieve the value stored at the given position.
   *
   * @param index representing a position in this list.
   * @return value at the given index.
   * @throws IndexException when index < 0 or index >= length.
   */
  @Override
  public T get(int index) throws IndexException {
    T temp = super.get(index);
    // If an exception is thrown, it will not increment reads
    reads++;
    return temp;
  }

  /**
   * Change the value at the given position.
   *
   * @param index representing a position in this list.
   * @param value to be written at the given index.
   *               Post: this.get(index) == value
   * @throws IndexException when index < 0 or index >= length.
   */
  @Override
  public void put(int index, T value) throws IndexException {
    super.put(index, value);
    // If an exception is thrown, it will not increment writes
    writes++;
  }

  /**
   * Resets the number of accesses and mutations.
   */
  @Override
  public void reset() {
    reads = 0;
    writes = 0;
  }

  /**
   * Retrieve the number of accesses to the list.
   *
   * @return reads representing the number of accesses to the list.
   */
  @Override
  public int accesses() {
    return reads;
  }

  /**
   * Retrieve the number of accesses to the list.
   *
   * @return writes representing the number of modifications to the list.
   */
  @Override
  public int mutations() {
    return writes;
  }

  /**
   * Retrieve the number of a given value in the list.
   *
   * @param value to be determined how many are in the list.
   * @return counter representing the number of accesses to the list.
   */
  @Override
  public int count(T value) {
    int counter = 0;
    for (T elements: this) {
      if (elements.equals(value)) {
        // .equals() method rather than == provides extra security for more complex objects
        counter++;
      }
    }
    reads += super.length();
    // count method accessed every element in the list
    return counter;
  }
}
