package sorting.sort;


import sorting.list.IndexedList;

/**
 * The Insertion Sort algorithm, with minimizing swaps optimization.
 *
 * @param <T> Element type.
 */
public final class InsertionSort<T extends Comparable<T>>
    implements SortingAlgorithm<T> {

  /**
   * The Insertion Sort algorithm, with minimizing swaps optimization.
   *
   * @param indexedList which will be sorted
   */
  @Override
  public void sort(IndexedList<T> indexedList) {
    for (int i = 1; i < indexedList.length(); i++) {
      T target = indexedList.get(i);
      int j = i - 1;
      // Shifts the elements greater than the target one to the right to make room.
      while (j >= 0 && (indexedList.get(j).compareTo(target) > 0)) {
        indexedList.put(j + 1, indexedList.get(j));
        j--;
      }
      // Puts the target in its intended spot
      indexedList.put(j + 1, target);
    }
  }

  /**
   * Provides the name of the Insertion Sort algorithm.
   *
   * @return "Insertion Sort" as the name of the sorting type
   */
  @Override
  public String name() {
    return "Insertion Sort";
  }
}
