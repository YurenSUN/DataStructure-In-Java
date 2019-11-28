import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * JUnit Test for Graph Class
 * 
 * @author sun
 *
 */
class GraphTest {
  Graph graph; // used for tests

  /**
   * run before every test initialize the graph
   * 
   * @throws Exception
   */
  @BeforeEach
  public void setUp() throws Exception {
    graph = new Graph();
  }

  /**
   * run after every test set graph to null
   * 
   * @throws Exception
   */
  @AfterEach
  public void tearDown() throws Exception {
    graph = null;
  }

  /**
   * test add null vertex, should not add or throw exception, size and order should be 0
   */
  @Test
  public void test00_addVertex_null() {
    try {
      graph.addVertex(null);
      if (graph.size() != 0 || graph.order() != 0)
        fail("fail when add null vertex");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * add 3 vertices and test order
   */
  @Test
  public void test01_add_7_vertices() {
    try {
      graph.addVertex("1");
      graph.addVertex("2");
      graph.addVertex("3");
      graph.addVertex("4");
      graph.addVertex("5");
      graph.addVertex("6");
      graph.addVertex("7");
      if (graph.size() != 0 || graph.order() != 7)
        fail("fail when add 7 vertices");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * add duplicate vertex and test
   */
  @Test
  public void test02_add_vertex_duplicate() {
    try {
      graph.addVertex("1");
      graph.addVertex("2");
      graph.addVertex("2");
      graph.addVertex("1");
      if (graph.size() != 0 || graph.order() != 2)
        fail("fail when add duplicate vertex");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * remove null vertex and test no edge is removed and no exception is thrown.
   */
  @Test
  public void test03_removeVertex_null() {
    try {
      graph.addVertex("1");
      graph.addVertex("2");
      graph.addVertex("3");
      graph.addVertex("4");
      graph.removeVertex(null);
      if (graph.size() != 0 || graph.order() != 4)
        fail("fail when when remove null");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * remove vertex that non-exist
   */
  @Test
  public void test04_removeVertex_nonexist() {
    try {
      graph.addVertex("1");
      graph.addVertex("2");
      graph.addVertex("3");
      graph.addVertex("4");
      graph.removeVertex("5");
      if (graph.size() != 0 || graph.order() != 4)
        fail("fail when when remove null");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * add edge with null vertex
   */
  @Test
  public void test05_addEdges_null() {
    try {
      graph.addVertex("1");
      graph.addEdge("1", null);
      graph.addEdge(null, "1");
      graph.addEdge(null, null);
      if (graph.size() != 0 || graph.order() != 1)
        fail("fail when add null edges");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * add edge with non-exist edge
   */
  @Test
  public void test06_addEdge_nonexist() {
    try {
      graph.addVertex("1");
      graph.addEdge("1", "2");
      graph.addEdge("2", "3");
      graph.addEdge("4", "3");
      if (graph.size() != 3 || graph.order() != 4)
        fail("fail when add nonexist edges");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * addEdges with duplicate edges
   */
  @Test
  public void test07_addEdges_duplicate() {
    try {
      graph.addVertex("1");
      graph.addVertex("2");
      graph.addVertex("3");
      graph.addVertex("4");
      graph.addEdge("1", "2");
      graph.addEdge("1", "2");

      if (graph.size() != 1 || graph.order() != 4)
        fail("fail when add duplicate edges");
      if (!graph.getAdjacentVerticesOf("1").toString().equals("[2]"))
        fail("fail when add duplicate edges");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test addEdges
   */
  @Test
  public void test08_addEdges_3() {
    try {
      graph.addVertex("1");
      graph.addVertex("2");
      graph.addVertex("3");
      graph.addVertex("4");
      graph.addEdge("1", "2");
      graph.addEdge("1", "3");
      graph.addEdge("1", "4");

      if (graph.size() != 3 || graph.order() != 4)
        fail("fail when add edges");
      if (!graph.getAdjacentVerticesOf("1").toString().equals("[2, 3, 4]"))
        fail("fail when add edges");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test whether removeVertex can remove the edges with the vertex
   */
  @Test
  public void test09_removeVertex_with_edges() {
    try {
      graph.addVertex("1");
      graph.addVertex("2");
      graph.addVertex("3");
      graph.addVertex("4");
      graph.addEdge("1", "2");
      graph.addEdge("1", "3");
      graph.addEdge("1", "4");
      graph.removeVertex("1");
      if (graph.size() != 0 || graph.order() != 3)
        fail("fail when remove vertex with edges");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test removeEdge with null vertex
   */
  @Test
  public void test10_removeEdge_null() {
    try {
      graph.addVertex("1");
      graph.addVertex("2");
      graph.removeEdge(null, "1");
      graph.removeEdge("1", null);
      graph.removeEdge(null, null);
      if (graph.size() != 0 || graph.order() != 2)
        fail("fail when remove null edge");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test removeEdge with non-exist vertex
   */
  @Test
  public void test11_removeEdge_nonexist() {
    try {
      graph.addVertex("1");
      graph.addVertex("2");
      graph.removeEdge("3", "1");
      graph.removeEdge("1", "3");

      if (graph.size() != 0 || graph.order() != 2)
        fail("fail when remove nonexist edge");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test removeEdge with removing 5 edges
   */
  @Test
  public void test12_removeEdge_5() {
    try {
      // add 4 vertex and 8 edges
      graph.addVertex("1");
      graph.addVertex("2");
      graph.addVertex("3");
      graph.addVertex("4");
      graph.addEdge("1", "2");
      graph.addEdge("1", "3");
      graph.addEdge("1", "4");
      graph.addEdge("2", "3");
      graph.addEdge("2", "4");
      graph.addEdge("3", "4");
      graph.addEdge("4", "2");
      graph.addEdge("3", "2");
      if (graph.size() != 8 || graph.order() != 4)
        fail("fail when addedges");

      // remove 5 edges
      graph.removeEdge("1", "4");
      graph.removeEdge("2", "3");
      graph.removeEdge("2", "4");
      graph.removeEdge("3", "4");
      graph.removeEdge("4", "2");
      if (graph.size() != 3 || graph.order() != 4)
        fail("fail when remove edges");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getAllVertices on empty graph
   */
  @Test
  public void test13_getAllVertices_empty() {
    try {
      if (!graph.getAllVertices().toString().equals("[]"))
        fail("fail to get all vertices with empty graph");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getAllVertices with 500 vertices
   */
  @Test
  public void test14_getAllVertices_500() {
    try {
      // add 500 vertices
      for (int i = 0; i < 500; i++) {
        String add = Integer.toString(i);
        graph.addVertex(add);
      }


      // construct expected set for getAllVertices
      Set<String> expected = new HashSet<String>();
      for (int i = 0; i < 500; i++) {
        String add = Integer.toString(i);
        expected.add(add);
      }

      if (!graph.getAllVertices().equals(expected))
        fail("fail to get all vertices with empty graph");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getAdjacentVerticesOf with null vertex
   */
  @Test
  public void test15_getAdjacentVerticesOf_null() {
    try {
      if (graph.getAdjacentVerticesOf(null) != null)
        fail("fail to getAdjacentVerticesOf with null vertex");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getAdjacentVerticesOf with non-exist vertex
   */
  @Test
  public void test16_getAdjacentVerticesOf_nonexist() {
    try {
      if (graph.getAdjacentVerticesOf("1") != null)
        fail("fail to getAdjacentVerticesOf with non-exist vertex");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getAdjacentVerticesOf with no edges
   */
  @Test
  public void test17_getAdjacentVerticesOf_no_edges() {
    try {
      graph.addVertex("1");
      graph.addVertex("2");
      graph.addVertex("3");

      if (!graph.getAdjacentVerticesOf("1").toString().equals("[]"))
        fail("fail to get correct adjacent vertices of");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * test getAdjacentVerticesOf with 10 edges
   */
  @Test
  public void test18_getAdjacentVerticesOf_10() {
    try {
      // add 11 vertex and 10 edges
      graph.addVertex("1");
      graph.addVertex("2");
      graph.addVertex("3");
      graph.addVertex("4");
      graph.addVertex("5");
      graph.addVertex("6");
      graph.addVertex("7");
      graph.addVertex("8");
      graph.addVertex("9");
      graph.addVertex("10");
      graph.addVertex("11");

      graph.addEdge("1", "2");
      graph.addEdge("1", "3");
      graph.addEdge("1", "4");
      graph.addEdge("1", "5");
      graph.addEdge("1", "6");
      graph.addEdge("1", "7");
      graph.addEdge("1", "8");
      graph.addEdge("1", "9");
      graph.addEdge("1", "10");
      graph.addEdge("1", "11");

      if (!graph.getAdjacentVerticesOf("1").toString().equals("[2, 3, 4, 5, 6, 7, 8, 9, 10, 11]"))
        fail("fail to get correct adjacent vertices of");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }

  /**
   * add 7 vertex and remove 5 and test
   */
  @Test
  public void test20_removeVertex_5() {
    try {
      graph.addVertex("1");
      graph.addVertex("2");
      graph.addVertex("3");
      graph.addVertex("4");
      graph.addVertex("5");
      graph.addVertex("6");
      graph.addVertex("7");
      // remove 5 vertices
      graph.removeVertex("2");
      graph.removeVertex("3");
      graph.removeVertex("4");
      graph.removeVertex("5");
      graph.removeVertex("6");
      if (graph.size() != 0 || graph.order() != 2)
        fail("fail when remove vertices");
      if (!graph.getAllVertices().toString().equals("[1, 7]"))
        fail("fail when remove vertices");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occurs!");
    }
  }
}
