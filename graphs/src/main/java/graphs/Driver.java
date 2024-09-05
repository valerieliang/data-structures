package graphs;

import graphs.graph.Graph;
import graphs.spp.StreetSearcher;
import java.io.FileNotFoundException;

public class Driver {

  /**
   * Execution starts here.
   *
   * @param args command-line arguments not used here.
   */
  public static void main(String[] args) {
    StreetSearcher.VERBOSE = true;
    Graph<String, String> graph = Config.getGraph();
    StreetSearcher streetSearcher = Config.getStreetSearcher(graph);
    Config config = Config.getConfig();
    System.out.println(config);
    try {
      streetSearcher.loadNetwork(config.data);
      streetSearcher.findShortestPath(config.from, config.to);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}