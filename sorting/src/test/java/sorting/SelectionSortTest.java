package sorting;

import sorting.sort.SelectionSort;
import sorting.sort.SortingAlgorithm;

public class SelectionSortTest extends SortingAlgorithmTest {
  @Override
  public SortingAlgorithm<Integer> createSortingAlgorithm() {
    return new SelectionSort<>();
  }
}
