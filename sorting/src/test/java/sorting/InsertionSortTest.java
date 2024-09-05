package sorting;

import sorting.sort.InsertionSort;
import sorting.sort.SortingAlgorithm;

public class InsertionSortTest extends SortingAlgorithmTest {
  @Override
  public SortingAlgorithm<Integer> createSortingAlgorithm() {
    return new InsertionSort<>();
  }
}
