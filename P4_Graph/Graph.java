import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Directed and unweighed graph implementation
 * 
 * @author Yuren sun
 *
 */
public class Graph implements GraphADT {
  private ArrayList<String> vertices; // list of vertices contain every vertex added
  private ArrayList<ArrayList<String>> edges; // ArrayList represent all the edges
  private int numEdges; // store the number of edges

  /**
   * Default no-argument constructor initialize the vertices and edges set numEdges to 0
   */
  public Graph() {
    vertices = new ArrayList<String>();
    edges = new ArrayList<ArrayList<String>>();
    numEdges = 0;
  }

  /**
   * Add new vertex to the graph.
   *
   * If vertex is null or already exists, method ends without adding a vertex
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in the graph
   * 
   * @param vertex the vertex to be added
   */
  public void addVertex(String vertex) {
    if (vertex == null || vertices.contains(vertex)) // invalid
      return;

    vertices.add(vertex);

    // add to the edges list
    ArrayList<String> newList = new ArrayList<String>();
    newList.add(vertex);
    edges.add(newList);
  }

  /**
   * Remove a vertex and all associated edges from the graph.
   * 
   * If vertex is null or does not exist, method ends without removing a vertex, edges, or throwing
   * an exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in the graph
   * 
   * @param vertex the vertex to be removed
   */
  public void removeVertex(String vertex) {
    if (vertex == null || (!vertices.contains(vertex))) // invalid
      return;

    vertices.remove(vertex);

    // remove the list for vertex in the edges list
    for (int i = 0; i < edges.size(); i++) {
      if (edges.get(i).get(0).equals(vertex)) {
        // decrease number of edges and remove list of edges for the vertex
        // i contains the current vertex, decrease by size of i minus 1
        numEdges -= edges.get(i).size() - 1;
        edges.remove(i);
      }
    }
  }

  /**
   * Add the edge from vertex1 to vertex2 to this graph. (edge is directed and unweighted) If either
   * vertex does not exist, add the non-existing vertex to the graph and then create an edge. If the
   * edge exists in the graph, no edge is added and no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. the edge is not in the graph
   */
  public void addEdge(String vertex1, String vertex2) {
    if (vertex1 == null || vertex2 == null) // invalid
      return;

    // add non-exist vertex to the graph and create edges
    if (!vertices.contains(vertex1)) {
      this.addVertex(vertex1);
      addEdge(vertex1, vertex2);
    }
    if (!vertices.contains(vertex2)) {
      this.addVertex(vertex2);
      addEdge(vertex1, vertex2);
    }

    // add edge
    for (List<String> i : edges) {
      if (i.get(0).equals(vertex1)) {
        if (i.contains(vertex2)) // edge exists
          return;
        // add edge and increase numEdges
        i.add(vertex2);
        numEdges++;
      }
    }
  }

  /**
   * Remove the edge from vertex1 to vertex2 from this graph. (edge is directed and unweighted) If
   * either vertex does not exist, or if an edge from vertex1 to vertex2 does not exist, no edge is
   * removed and no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are in the graph 3. the
   * edge from vertex1 to vertex2 is in the graph
   * 
   * @param vertex1 the first vertex
   * @param vertex2 the second vertex
   */
  public void removeEdge(String vertex1, String vertex2) {
    if (vertex1 == null || vertex2 == null) // invalid
      return;
    if ((!vertices.contains(vertex1)) || (!vertices.contains(vertex2))) // invalid
      return;

    // remove edge
    for (List<String> i : edges) {
      if (i.get(0).equals(vertex1)) {
        if (i.contains(vertex2)) { // edge exists
          // remove edge and decrease numEdges
          i.remove(vertex2);
          numEdges--;
        }
      }
    }
  }

  /**
   * Returns a Set that contains all the vertices
   * 
   * @return a Set<String> which contains all the vertices in the graph
   */
  public Set<String> getAllVertices() {
    Set<String> returnSet = new HashSet<String>(); // to be return
    for (String i : vertices)
      returnSet.add(i);
    return returnSet;
  }

  /**
   * Get all the neighbor (adjacent) vertices of a vertex
   * 
   * @param vertex the specified vertex
   * @return an List<String> of all the adjacent vertices for specified vertex
   */
  public List<String> getAdjacentVerticesOf(String vertex) {
    if (vertex == null || (!vertices.contains(vertex))) // invalid
      return null;

    // get successor of the vertex and store it in returnList
    List<String> returnList = new ArrayList<String>();
    for (List<String> i : edges) {
      if (i.get(0).equals(vertex)) {
        for (String j : i)
          returnList.add(j);
      }
    }
    returnList.remove(vertex);
    return returnList;
  }

  /**
   * Returns the number of edges in this graph.
   * 
   * @return number of edges in the graph.
   */
  public int size() {
    return numEdges;
  }

  /**
   * Returns the number of vertices in this graph.
   * 
   * @return number of vertices in graph.
   */
  public int order() {
    return vertices.size();
  }
}
