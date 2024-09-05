package graphs.graph;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;



/**
 * An implementation of Graph ADT using incidence lists
 * for sparse graphs where most nodes aren't connected.
 *
 * @param <V> Vertex element type.
 * @param <E> Edge element type.
 */
public class SparseGraph<V, E> implements Graph<V, E> {

  HashMap<Vertex<V>, ArrayList<Edge<E>>> incidenceList;

  /**
   * Constructor for SparseGraph.
   */
  public SparseGraph() {
    incidenceList = new HashMap<>();
  }

  // Converts the vertex back to a VertexNode to use internally
  private VertexNode<V> convert(Vertex<V> v) throws PositionException {
    try {
      VertexNode<V> gv = (VertexNode<V>) v;
      if (gv.owner != this) {
        throw new PositionException();
      }
      return gv;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  // Converts and edge back to a EdgeNode to use internally
  private EdgeNode<E> convert(Edge<E> e) throws PositionException {
    try {
      EdgeNode<E> ge = (EdgeNode<E>) e;
      if (ge.owner != this) {
        throw new PositionException();
      }
      return ge;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  /**
   * Insert a new vertex.
   *
   * @param v Element to insert.
   * @return Vertex position created to hold element.
   * @throws InsertionException If v is null or already in this Graph.
   */
  @Override
  public Vertex<V> insert(V v) throws InsertionException {
    if (v == null) { // null vertices
      throw new InsertionException();
    } else {
      for (Vertex<V> vertex : incidenceList.keySet()) {
        if (vertex.get().equals(v)) {
          throw new InsertionException(); // Duplicate vertex found
        }
      }
      VertexNode<V> vertex = new VertexNode<>(v, this);
      incidenceList.put(vertex, new ArrayList<>());
      return vertex;
    }
  }

  /**
   * Insert a new edge.
   *
   * @param from Vertex position where edge starts.
   * @param to   Vertex position where edge ends.
   * @param e    Element to insert (can be null).
   * @return Edge position created to hold element.
   * @throws PositionException  If either vertex position is invalid.
   * @throws InsertionException If insertion would create a self-loop or
   *                            duplicate edge.
   */
  @Override
  public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
          throws PositionException, InsertionException {
    if (from == null || to == null) { // invalid vertices
      throw new PositionException();
    } else if (from == to) { // self-loop
      throw new InsertionException();
    } else if (!incidenceList.containsKey(from) || !incidenceList.containsKey(to)) {
      throw new PositionException();
    } else {
      EdgeNode<E> temp = new EdgeNode<>(convert(from), convert(to), e, this);
      // implicitly checks owner; vertices must both belong to this
      ArrayList<Edge<E>> edges = incidenceList.get(from);
      for (Edge<E> edge : edges) {
        if (convert(to).equals(convert(edge).to)) {
          throw new InsertionException(); // duplicate edge
        }
      }
      edges.add(temp); // implicit down casting
      return temp;
    }
  }

  // vertex remove helper: for incident edges
  private V checkIncidentEdges(Vertex<V> v) {
    Iterable<Edge<E>> edges = this.incoming(v);
    Collection<Edge<E>> edgeCollection = new ArrayList<>();
    for (Edge<E> edge : edges) {
      edgeCollection.add(edge);
    }
    if (!edgeCollection.isEmpty()) {
      throw new RemovalException(); // incident edges
    }
    V temp = convert(v).data;
    incidenceList.remove(v);
    return temp;
  }

  /**
   * Remove a vertex.
   *
   * @param v Vertex position to remove.
   * @return Element from removed vertex position.
   * @throws PositionException If vertex position is invalid.
   * @throws RemovalException  If vertex still has incident edges.
   */
  @Override
  public V remove(Vertex<V> v) throws PositionException, RemovalException {
    if (v == null) {
      throw new PositionException();
    } else if (convert(v).owner != this) {
      throw new PositionException();
    } else if (!incidenceList.containsKey(v)) {
      throw new PositionException();
    } else {
      // vertex exists in graph
      if (!incidenceList.get(v).isEmpty()) {
        throw new RemovalException();
        // has incident edges
      } else {
        return checkIncidentEdges(v);
      }
    }
  }

  /**
   * Remove an edge.
   *
   * @param e Edge position to remove.
   * @return Element from removed edge position.
   * @throws PositionException If edge position is invalid.
   */
  @Override
  public E remove(Edge<E> e) throws PositionException {
    if (e == null) { // value is null
      throw new PositionException();
    } else if (!incidenceList.containsKey(convert(e).from)) {
      // doesn't have vertex -> doesn't have edge
      throw new PositionException();
    } else { // convert has implicit check for owner
      if (!incidenceList.containsKey(convert(e).to)) {
        throw new PositionException();
      } // has start but not end
      ArrayList<Edge<E>> edges = incidenceList.get(convert(e).from);
      if (!edges.contains(e)) { // edge not in graph
        throw new PositionException();
      } else {
        int temp = edges.indexOf(e);
        Edge<E> edge = edges.remove(temp);
        return convert(edge).data;
      }
    }
  }

  /**
   * Vertices of graph.
   *
   * @return Iterable over all vertices of the graph (in no specific order).
   */
  @Override
  public Iterable<Vertex<V>> vertices() {
    return Collections.unmodifiableCollection(incidenceList.keySet());
  }

  /**
   * Edges of graph.
   *
   * @return Iterable over all edges of the graph (in no specific order).
   */
  @Override
  public Iterable<Edge<E>> edges() {
    Collection<ArrayList<Edge<E>>> edgeLists = incidenceList.values();
    ArrayList<Edge<E>> allEdges = new ArrayList<>();
    for (ArrayList<Edge<E>> edgeList : edgeLists) {
      allEdges.addAll(edgeList);
    }
    return Collections.unmodifiableCollection(allEdges);
  }

  /**
   * Outgoing edges of vertex.
   *
   * @param v Vertex position to explore.
   * @return Iterable over all outgoing edges of the given vertex
   *         (in no specific order).
   * @throws PositionException If vertex position is invalid.
   */
  @Override
  public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
    if (v == null) {
      throw new PositionException();
    } else if (convert(v).owner != this) {
      throw new PositionException(); // wrong graph
    }
    ArrayList<Edge<E>> outgoingEdges = incidenceList.get(v);
    return Collections.unmodifiableCollection(outgoingEdges);
  }

  /**
   * Incoming edges of vertex.
   *
   * @param v Vertex position to explore.
   * @return Iterable over all incoming edges of the given vertex
   *         (in no specific order).
   * @throws PositionException If vertex position is invalid.
   */
  @Override
  public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
    if (v == null) {
      throw new PositionException();
    } else if (convert(v).owner != this) {
      throw new PositionException(); // wrong graph
    }
    ArrayList<Edge<E>> incomingEdges = new ArrayList<>();
    for (ArrayList<Edge<E>> edgeList : incidenceList.values()) {
      for (Edge<E> edge : edgeList) {
        if (convert(edge).to == v) {
          incomingEdges.add(edge);
        }
      }
    }
    return Collections.unmodifiableCollection(incomingEdges);
  }


  /**
   * Start vertex of edge.
   *
   * @param e Edge position to explore.
   * @return Vertex position edge starts from.
   * @throws PositionException If edge position is invalid.
   */
  @Override
  public Vertex<V> from(Edge<E> e) throws PositionException {
    if (e == null) {
      throw new PositionException();
    } else if (convert(e).owner != this) { // wrong graph
      throw new PositionException();
    }
    return convert(e).from;
  }

  /**
   * End vertex of edge.
   *
   * @param e Edge position to explore.
   * @return Vertex position edge leads to.
   * @throws PositionException If edge position is invalid.
   */
  @Override
  public Vertex<V> to(Edge<E> e) throws PositionException {
    if (e == null) {
      throw new PositionException();
    }
    return convert(e).to;
  }

  /**
   * Label vertex with object.
   *
   * @param v Vertex position to label.
   * @param l Label object.
   * @throws PositionException If vertex position is invalid.
   */
  @Override
  public void label(Vertex<V> v, Object l) throws PositionException {
    if (v == null) {
      throw new PositionException();
    }
    convert(v).label = l;
  }

  /**
   * Label edge with object.
   *
   * @param e Edge position to label.
   * @param l Label object.
   * @throws PositionException If edge position is invalid.
   */
  @Override
  public void label(Edge<E> e, Object l) throws PositionException {
    if (e == null) {
      throw new PositionException();
    }
    convert(e).label = l;
  }

  /**
   * Vertex label.
   *
   * @param v Vertex position to query.
   * @return Label object (or null if none).
   * @throws PositionException If vertex position is invalid.
   */
  @Override
  public Object label(Vertex<V> v) throws PositionException {
    if (v == null) {
      throw new PositionException();
    }
    return convert(v).label;
  }

  /**
   * Edge label.
   *
   * @param e Edge position to query.
   * @return Label object (or null if none).
   * @throws PositionException If edge position is invalid.
   */
  @Override
  public Object label(Edge<E> e) throws PositionException {
    if (e == null) {
      throw new PositionException();
    }
    return convert(e).label;
  }

  /**
   * Clear all labels.
   * All labels are null after this.
   */
  @Override
  public void clearLabels() {
    Iterable<Vertex<V>> vertexIterable = this.vertices();
    for (Vertex<V> vertex : vertexIterable) {
      convert(vertex).label = null;
    }
    Iterable<Edge<E>> edgesIterable = this.edges();
    for (Edge<E> edge : edgesIterable) {
      convert(edge).label = null;
    }
  }

  @Override
  public String toString() {
    GraphPrinter<V, E> gp = new GraphPrinter<>(this);
    return gp.toString();
  }


  // Class for a vertex of type V
  private final class VertexNode<V> implements Vertex<V> {
    V data;
    Graph<V, E> owner;
    Object label;
    // TODO You may need to add fields/methods here!

    VertexNode(V v) {
      this.data = v;
      this.label = null;
    }

    VertexNode(V v, Graph<V, E> g) {
      this.data = v;
      this.label = null;
      this.owner = g;
    }

    @Override
    public V get() {
      return this.data;
    }

  }


  //Class for an edge of type E
  private final class EdgeNode<E> implements Edge<E> {
    E data;
    Graph<V, E> owner;
    VertexNode<V> from;
    VertexNode<V> to;
    Object label;
    // TODO You may need to add fields/methods here!

    // Constructor for a new edge
    EdgeNode(VertexNode<V> f, VertexNode<V> t, E e) {
      this.from = f;
      this.to = t;
      this.data = e;
      this.label = null;
    }

    EdgeNode(VertexNode<V> f, VertexNode<V> t, E e, Graph<V,E> g) {
      this.from = f;
      this.to = t;
      this.data = e;
      this.label = null;
      this.owner = g;
    }

    @Override
    public E get() {
      return this.data;
    }

  }
}

