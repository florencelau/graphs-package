package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the GraphObj class.
 *  @author Florence Lau
 */
public class GraphObjTests {
    @Test
    public void testInitialGraphs() {
        DirectedGraph directed = new DirectedGraph();
        UndirectedGraph undirected = new UndirectedGraph();
        assertEquals(directed.isDirected(), true);
        assertEquals(undirected.isDirected(), false);
        assertEquals(directed.vertexSize(), 0);
        assertEquals(undirected.vertexSize(), 0);
        assertEquals(directed.edgeSize(), 0);
        assertEquals(undirected.edgeSize(), 0);
    }

    @Test
    public void testAdd() {
        DirectedGraph d = new DirectedGraph();
        UndirectedGraph u = new UndirectedGraph();
        d.add();
        d.add();
        d.add(1, 2);
        Iteration<Integer> dVerticesList = d.vertices();
        int first = dVerticesList.next();
        int next = dVerticesList.next();
        assertEquals(first, 1);
        assertEquals(next, 2);
        assertEquals(d.add(2, 3), 0);
        u.add();
        u.add();
        u.add();
        assertEquals(u.contains(3), true);
        assertEquals(u.contains(5), false);
        u.add(1, 2);
        assertEquals(d.vertexSize(), 2);
        assertEquals(u.vertexSize(), 3);
        assertEquals(d.edgeSize(), 1);
        assertEquals(u.edgeSize(), 1);
        assertEquals(d.maxVertex(), 2);
        assertEquals(u.maxVertex(), 3);
    }

    @Test
    public void testRemove() {
        DirectedGraph d = new DirectedGraph();
        UndirectedGraph u = new UndirectedGraph();
        d.add();
        d.add();
        d.add();
        d.add();
        d.add();
        assertEquals(d.vertexSize(), 5);
        d.add(1, 3);
        d.add(3, 5);
        d.add(2, 4);
        d.add(4, 1);
        assertEquals(d.edgeSize(), 4);
        assertEquals(d.inDegree(1), 1);
        assertEquals(d.inDegree(2), 0);
        assertEquals(d.inDegree(3), 1);
        assertEquals(d.inDegree(4), 1);
        assertEquals(d.inDegree(5), 1);
        assertEquals(d.outDegree(1), 1);
        assertEquals(d.outDegree(2), 1);
        assertEquals(d.outDegree(3), 1);
        assertEquals(d.outDegree(4), 1);
        assertEquals(d.outDegree(5), 0);
        d.remove(4);
        d.remove(3);
        assertEquals(d.contains(4), false);
        assertEquals(d.contains(3), false);
        assertEquals(d.contains(1), true);
        assertEquals(d.vertexSize(), 3);
        assertEquals(d.edgeSize(), 0);
        d.add();
        assertEquals(d.contains(3), true);
        assertEquals(d.contains(4), false);
        d.add();
        assertEquals(d.contains(4), true);
    }
}
