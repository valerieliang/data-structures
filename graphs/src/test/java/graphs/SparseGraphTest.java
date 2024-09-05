package graphs;

import graphs.graph.Graph;
import graphs.graph.SparseGraph;

public class SparseGraphTest extends GraphTest {

  @Override
  protected Graph<String, String> createGraph() {
    return new SparseGraph<>();
  }
}
