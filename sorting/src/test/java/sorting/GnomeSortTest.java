package sorting;

import sorting.sort.GnomeSort;
import sorting.sort.SortingAlgorithm;

public class GnomeSortTest extends SortingAlgorithmTest {
  @Override
  public SortingAlgorithm<Integer> createSortingAlgorithm() {
    return new GnomeSort<>();
  }
}
