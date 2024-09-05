package sorting;

import sorting.sort.BubbleSort;
import sorting.sort.SortingAlgorithm;

public class BubbleSortTest extends SortingAlgorithmTest {
  @Override
  public SortingAlgorithm<Integer> createSortingAlgorithm() {
    return new BubbleSort<>();
  }
}
