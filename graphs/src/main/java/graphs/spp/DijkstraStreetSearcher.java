package graphs.spp;

import graphs.graph.Edge;
import graphs.graph.Graph;
import graphs.graph.Vertex;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraStreetSearcher extends StreetSearcher {
  double infinity = Double.POSITIVE_INFINITY;
  private PriorityQueue<Vertex<String>> distanceQueue;
  private ArrayList<Vertex<String>> explored;
  private HashMap<Vertex<String>, Double> distance;

  /**
   * Creates a StreetSearcher object.
   *
   * @param graph an implementation of Graph ADT.
   */
  public DijkstraStreetSearcher(Graph<String, String> graph) {
    super(graph);
    distanceQueue = new PriorityQueue<>(5, new DistanceComparator());
    explored = new ArrayList<>(); // stores previously travelled intersections
    distance = new HashMap<>(); // distance between two points
  }

  @Override
  public void findShortestPath(String startName, String endName) {
    Vertex<String> start = vertices.get(startName);
    Vertex<String> end = vertices.get(endName);

    if (start == null || end == null) {
      System.out.println("Invalid endpoint: " + endName);
      return;
    }

    if (start.equals(end)) {
      System.out.println("No path found");
      return;
    }

    double totalDist = -1;  // totalDist must be updated below
    totalDist = search(start, end);

    // These method calls will create and print the path for you
    List<Edge<String>> path = getPath(end, start);
    if (VERBOSE) {
      printPath(path, totalDist);
    }
  }

  // helper method: fills priority queue
  private void setUp(Vertex<String> start) {
    for (Vertex<String> intersection: graph.vertices()) {
      if (intersection.equals(start)) { // distance between start, start = 0
        distance.put(start, 0.0);
      } else {
        distance.put(intersection, infinity); // default to high value
      }
      graph.label(intersection, null); // reset label information
      distanceQueue.add(intersection);
    }
  }

  // helper method: updates total distance
  private double search(Vertex<String> start, Vertex<String> end) {
    setUp(start);
    while (!distanceQueue.isEmpty()) {
      Vertex<String> curr = distanceQueue.poll(); // access + remove first
      explored.add(curr);
      updateDistances(curr);
    }
    return distance.get(end); // return shortest distance
  }

  // helper method: compares values within the priority queue; updates to new best path
  // i.e: changes values from infinity to real value
  private void updateDistances(Vertex<String> curr) {
    for (Edge<String> edge : graph.outgoing(curr)) {
      Vertex<String> temp = graph.to(edge);
      // check unexplored vertices
      if (!explored.contains(temp)) {
        double prevDist = distance.get(temp);
        double streetDist = (double) graph.label(edge);
        double currDist = distance.get(curr) + streetDist;
        if (prevDist > currDist) { // update with shorter path
          distance.put(temp, currDist);
          graph.label(temp, edge);
          distanceQueue.remove(temp);
          // update intersection value in queue
          distanceQueue.add(temp);
        }
      }
    }
  }

  // used for priority queue
  // compare the distances of two intersections to
  private class DistanceComparator implements Comparator<Vertex<String>> {
    @Override
    public int compare(Vertex<String> first, Vertex<String> second) {
      return distance.get(first).compareTo(distance.get(second));
    }
  }
}
