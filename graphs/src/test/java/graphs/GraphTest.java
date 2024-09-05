package graphs;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import graphs.graph.Edge;
import graphs.graph.Graph;
import graphs.graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public abstract class GraphTest {

  protected Graph<String, String> graph;

  @BeforeEach
  public void setupGraph() {
    this.graph = createGraph();
  }

  protected abstract Graph<String, String> createGraph();

  // Vertex<V> insert(V v)
  @Test
  @DisplayName("insert(v) returns a vertex with given data")
  public void canGetVertexAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    assertEquals(v1.get(), "v1");
  }
  @Test
  @DisplayName("insert(v) throws InsertionException if v is null or already in this Graph")
  public void insertVertexThrowsExceptionForNull() {
    try {
      Vertex<String> v1 = graph.insert(null);
      fail("The expected exception was not thrown");
    } catch (InsertionException e) {
      return;
    }
  }
  @Test
  @DisplayName("insert(v) throws InsertionException for duplicate vertices")
  public void insertVertexThrowsExceptionForDuplicateVertices() {
    try {
      Vertex<String> v1 = graph.insert("v");
      Vertex<String> v2 = graph.insert("v");
      fail("The expected exception was not thrown");
    } catch (InsertionException e) {
      return;
    }
  }

  // Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
  @Test
  @DisplayName("insert(U, V, e) returns an edge with given data")
  public void canGetEdgeAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals(e1.get(), "v1-v2");
  }
  @Test
  @DisplayName("insert(v, w, e) goes to and from one vertex to another")
  public void insertEdgeConnectsTwoVertices() {
    Vertex<String> v = graph.insert("v");
    Vertex<String> w = graph.insert("w");
    Edge<String> e = graph.insert(v, w, "e");
    //assert statements
    assertEquals(v, graph.from(e));
    assertEquals(w, graph.to(e));
  }
  @Test
  @DisplayName("insert(null, V, e) throws exception when first vertex is null")
  public void insertEdgeThrowsPositionExceptionWhenfirstVertexIsNull() {
    try {
      Vertex<String> v = graph.insert("v");
      Edge<String> e = graph.insert(null, v, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }
  @Test
  @DisplayName("insert(V, null, e) throws exception when second vertex is null")
  public void insertEdgeThrowsPositionExceptionWhenSecondVertexIsNull() {
    try {
      Vertex<String> v = graph.insert("v");
      Edge<String> e = graph.insert(v, null, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }
  @Test
  @DisplayName("insert(v, v, e) throws exception when creating self loop")
  public void insertEdgeThrowsPositionExceptionWhenCreatingLoop() {
    try {
      Vertex<String> v = graph.insert("v");
      Edge<String> e = graph.insert(v, v, "e");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }
  @Test
  @DisplayName("insert(v, w, e) throws exception when creating duplicate edge")
  public void insertEdgeThrowsExceptionWhenCreatingDuplicateEdge() {
    try {
      Vertex<String> v = graph.insert("v");
      Vertex<String> w = graph.insert("w");
      Edge<String> e1 = graph.insert(v, w, "e1");
      Edge<String> e2 = graph.insert(v, w, "e2");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }
  @Test
  @DisplayName("insert() does not throw exception when creating edges between the same pair of vertices in different directions")
  public void insertEdgeDoesNotThrowsPositionExceptionWhenOppositeDirectionEdge() {
    try {
      Vertex<String> v = graph.insert("v");
      Vertex<String> w = graph.insert("w");
      Edge<String> e1 = graph.insert(v, w, "e1");
      Edge<String> e2 = graph.insert(w, v, "e2");
      //assert statements
      assertEquals("e1", e1.get());
      assertEquals(v, graph.from(e1));
      assertEquals(w, graph.to(e1));
      assertEquals("e2", e2.get());
      assertEquals(w, graph.from(e2));
      assertEquals(v, graph.to(e2));
    } catch (PositionException ex) {
      fail("An unexpected exception was thrown");
    }
  }

  // V remove(Vertex<V> v)
  @Test
  @DisplayName("remove(v) returns the data from the removed vertex")
  public void removeVertexReturnsData() {
    Vertex<String> v = graph.insert("v");
    String data = graph.remove(v);
    assert(data.equals("v"));
  }
  @Test
  @DisplayName("remove(v) throws PositionException for invalid position")
  public void removeVertexThrowsExceptionForInvalidPosition() {
    try {
      Vertex<String> v = graph.insert("v");
      String data = graph.remove((Vertex<String>) null);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }
  @Test
  @DisplayName("remove(v) throws RemovalException If vertex still has incident edges.")
  public void removeVertexThrowsExceptionForVerticesWithEdges() {
    try {
      Vertex<String> v = graph.insert("v");
      Vertex<String> w = graph.insert("w");
      Edge<String> e1 = graph.insert(v, w, "e1"); // incident edge
      String data = graph.remove(v);
      fail("The expected exception was not thrown");
    } catch (RemovalException ex) {
      return;
    }
  }

  // E remove(Edge<E> e)
  @Test
  @DisplayName("remove(e) returns the data from the removed edge")
  public void removeEdgeReturnsData() {
    Vertex<String> v = graph.insert("v");
    Vertex<String> w = graph.insert("w");
    Edge<String> e1 = graph.insert(v, w, "e1"); // incident edge
    String data = graph.remove(e1);
    assert(data.equals("e1"));
  }
  @Test
  @DisplayName("remove(e) throws PositionException for invalid position")
  public void removeEdgeThrowsExceptionForInvalidPosition() {
    try {
      Vertex<String> v = graph.insert("v");
      Vertex<String> w = graph.insert("w");
      Edge<String> e1 = graph.insert(v, w, "e1"); // incident edge
      String data = graph.remove((Edge<String>) null);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  // Iterable<Vertex<V>> vertices();
  @Test
  @DisplayName("vertices() method iterates over all vertices of the graph")
  public void iterateOverAllVertices() {
    // set up
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    // test iterator
    Iterable<Vertex<String>> verticesIterable = graph.vertices();
    ArrayList<Vertex<String>> verticesList = new ArrayList<>();
    for (Vertex<String> vertex : verticesIterable) { // implicit iterator
      verticesList.add(vertex);
    }
    // assert statements
    assert(verticesList.contains(v1));
    assert(verticesList.contains(v2));
    assert(verticesList.contains(v3));
    assertEquals(3, verticesList.size()); // three total vertices in graph
  }
  @Test
  @DisplayName("test iterator with no vertices")
  public void iterateNoVertices() {
    int count = 0;
    Iterable<Vertex<String>> verticesIterable = graph.vertices();
    for (Vertex<String> vertex : verticesIterable) {
      count++;
    }
    assertEquals(0, count);
  }

  // Iterable<Edge<E>> edges();
  @Test
  @DisplayName("edges() iterates over all edges of the graph")
  public void iterateOverAllEdges() {
    // set up
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    Edge<String> e2 = graph.insert(v2, v3, "e2");
    Edge<String> e3 = graph.insert(v3, v1, "e3");
    // test iterator
    Iterable<Edge<String>> edgesIterable = graph.edges();
    ArrayList<Edge<String>> edgesList = new ArrayList<>();
    for (Edge<String> edge : edgesIterable) { // implicit iterator
      edgesList.add(edge);
    }
    // assert statements
    assert(edgesList.contains(e1));
    assert(edgesList.contains(e2));
    assert(edgesList.contains(e3));
    assertEquals(3, edgesList.size());
  }
  @Test
  @DisplayName("test iterator with no edges")
  public void iterateNoEdges() {
    int count = 0;
    Iterable<Edge<String>> edgesIterable = graph.edges();
    for (Edge<String> edge : edgesIterable) { // implicit iterator
      count++;
    }
    assertEquals(0, count);
  }

  // Iterable<Edge<E>> outgoing(Vertex<V> v)
  @Test
  @DisplayName("outgoing(v) returns all outgoing edges from the specified vertex")
  public void iterateOverOutgoing() {
    // setup
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    Edge<String> e2 = graph.insert(v1, v3, "e2");
    Edge<String> e3 = graph.insert(v2, v3, "e3");
    // test iterator
    Iterable<Edge<String>> outgoingEdgesIterable = graph.outgoing(v1);
    ArrayList<Edge<String>> outgoingEdgesList = new ArrayList<>();
    for (Edge<String> edge : outgoingEdgesIterable) { // implicit iterator
      outgoingEdgesList.add(edge);
    }
    // assert statements
    assert(outgoingEdgesList.contains(e1));
    assert(outgoingEdgesList.contains(e2));
    assert(!outgoingEdgesList.contains(e3));
    assertEquals(2,outgoingEdgesList.size());
  }
  @Test
  @DisplayName("test outgoing iterator with no outgoing edges")
  public void iterateNoOutgoingEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    int count = 0;
    Iterable<Edge<String>> outgoingEdgesIterable = graph.outgoing(v2);
    for (Edge<String> edge : outgoingEdgesIterable) { // implicit iterator
      count++;
    }
    assertEquals(0, count);
  }
  @Test
  @DisplayName("outgoing(v) throws PositionException If vertex position is invalid")
  public void outgoingThrowsPositionException() {
    try {
      // setup
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Vertex<String> v3 = graph.insert("v3");
      Edge<String> e1 = graph.insert(v1, v2, "e1");
      Edge<String> e2 = graph.insert(v1, v3, "e2");
      Edge<String> e3 = graph.insert(v2, v3, "e3");
      // test iterator
      Iterable<Edge<String>> outgoingEdgesIterable = graph.outgoing(null);
      fail("The expected exception was not thrown.");
    } catch (PositionException e) {
      return;
    }
  }

  // Iterable<Edge<E>> incoming(Vertex<V> v)
  @Test
  @DisplayName("incoming(v) returns all incoming edges from the specified vertex")
  public void iterateOverIncoming() {
    // setup
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    Edge<String> e2 = graph.insert(v1, v3, "e2");
    Edge<String> e3 = graph.insert(v2, v3, "e3");
    // test iterator
    Iterable<Edge<String>> incomingEdgesIterable = graph.incoming(v3);
    ArrayList<Edge<String>> incomingEdgesList = new ArrayList<>();
    for (Edge<String> edge : incomingEdgesIterable) { // implicit iterator
      incomingEdgesList.add(edge);
    }
    // assert statements
    assert(!incomingEdgesList.contains(e1));
    assert(incomingEdgesList.contains(e2));
    assert(incomingEdgesList.contains(e3));
    assertEquals(2,incomingEdgesList.size());
  }
  @Test
  @DisplayName("test outgoing iterator with no incoming edges")
  public void iterateNoIncomingEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    int count = 0;
    Iterable<Edge<String>> incomingEdgesIterable = graph.incoming(v1);
    for (Edge<String> edge : incomingEdgesIterable) { // implicit iterator
      count++;
    }
    assertEquals(0, count);
  }
  @Test
  @DisplayName("incoming(v) throws PositionException if vertex position is invalid")
  public void incomingThrowsPositionException() {
    try {
      // setup
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Vertex<String> v3 = graph.insert("v3");
      Edge<String> e1 = graph.insert(v1, v2, "e1");
      Edge<String> e2 = graph.insert(v1, v3, "e2");
      Edge<String> e3 = graph.insert(v2, v3, "e3");
      // test iterator
      Iterable<Edge<String>> incomingEdgesIterable = graph.incoming(null);
      fail("The expected exception was not thrown.");
    } catch (PositionException e) {
      return;
    }
  }

  // Vertex<V> from(Edge<E> e)
  @Test
  @DisplayName("from(e) returns the start vertex of the specified edge")
  public void fromMethodReturnsStart() {
    // setup
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    // test from()
    Vertex<String> start = graph.from(e1);
    // assert statements
    assertEquals(start, v1);
  }
  @Test
  @DisplayName("from(e) throws PositionException if edge position is invalid")
  public void fromThrowsPositionException() {
    try {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Edge<String> e1 = graph.insert(v1, v2, "e1");
      graph.from(null);
      fail("The expected exception was not thrown.");
    } catch (PositionException e) {
      return;
    }
  }

  // Vertex<V> to(Edge<E> e)
  @Test
  @DisplayName("to(e) returns the start vertex of the specified edge")
  public void toMethodReturnsEnd() {
    // setup
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    // test from()
    Vertex<String> end = graph.to(e1);
    // assert statements
    assertEquals(end, v2);
  }
  @Test
  @DisplayName("to(e) throws PositionException if edge position is invalid")
  public void toThrowsPositionException() {
    try {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Edge<String> e1 = graph.insert(v1, v2, "e1");
      graph.to(null);
      fail("The expected exception was not thrown.");
    } catch (PositionException e) {
      return;
    }
  }

  // label(Vertex<V> v, Object l)
  @Test
  @DisplayName("label(v, l) labels the specified vertex with the given object")
  public void testLabelVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Object label = new Object();
    graph.label(v1, label);
    assertEquals(label, graph.label(v1));
  }

  @Test
  @DisplayName("label(v, l) throws PositionException if vertex position is invalid")
  public void testLabelVertexThrowsPositionException(){
    try {
      Vertex<String> v1 = graph.insert("v1");
      Object label = new Object();
      graph.label((Vertex<String>) null, label);
      fail("The expected exception was not thrown.");
    } catch (PositionException e) {
      return;
    }
  }

  // label(Edge<E> e, Object l)
  @Test
  @DisplayName("label(e, l) labels the specified edge with the given object")
  public void testLabelEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    Object label = new Object();
    graph.label(e1, label);
    assertEquals(label, graph.label(e1));
  }

  @Test
  @DisplayName("label(e, l) throws PositionException if edge position is invalid")
  public void testLabelEdgeThrowsPositionException(){
    try {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Edge<String> e1 = graph.insert(v1, v2, "e1");
      Object label = new Object();
      graph.label((Edge<String>) null, label);
      fail("The expected exception was not thrown.");
    } catch (PositionException e) {
      return;
    }
  }

  // label(v)
  @Test
  @DisplayName("label(v) returns vertex label")
  public void testGetLabelVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Object label = new Object();
    graph.label(v1, label);
    assertEquals(graph.label(v1), label);
  }
  @Test
  @DisplayName("label(v) returns null for vertex with no label")
  public void testGetLabelVertexNull() {
    Vertex<String> v1 = graph.insert("v1");
    assertNull(graph.label(v1));
  }
  @Test
  @DisplayName("label(v) throws PositionException if vertex position is invalid")
  public void testGetLabelVertexThrowsPositionException(){
    try {
      Vertex<String> v1 = graph.insert("v1");
      Object label = new Object();
      graph.label((Vertex<String>) null);
      fail("The expected exception was not thrown.");
    } catch (PositionException e) {
      return;
    }
  }

  // label(e)
  @Test
  @DisplayName("label(e) returns edge label")
  public void testGetLabelEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    Object label = new Object();
    graph.label(e1, label);
    assertEquals(graph.label(e1), label);
  }
  @Test
  @DisplayName("label(e) returns null for edge with no label")
  public void testGetLabelEdgeNull() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    assertNull(graph.label(e1));
  }
  @Test
  @DisplayName("label(e) throws PositionException if edge position is invalid")
  public void testGetLabelEdgeThrowsPositionException(){
    try {
      Vertex<String> v1 = graph.insert("v1");
      Vertex<String> v2 = graph.insert("v2");
      Edge<String> e1 = graph.insert(v1, v2, "e1");
      Object label = new Object();
      graph.label((Edge<String>) null);
      fail("The expected exception was not thrown.");
    } catch (PositionException e) {
      return;
    }
  }

  // clearLabels()
  @Test
  @DisplayName("clearLabels() sets all vertex and edge labels to null")
  public void testClearLabelsMethod() {
    // setup
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Object vertexLabel = new Object();
    graph.label(v1, vertexLabel);
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    Object edgeLabel = new Object();
    graph.label(e1, edgeLabel);
    // test clearLabels()
    graph.clearLabels();
    // assertion statements
    assertNull(graph.label(v1));
    assertNull(graph.label(v2));
    assertNull(graph.label(e1));
  }
}
