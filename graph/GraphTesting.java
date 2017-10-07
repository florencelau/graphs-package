package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Florence Lau
 */
public class GraphTesting {
    @Test
    public void emptyDirectedGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void emptyUndirectedGraph() {
        UndirectedGraph g = new UndirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void testMethods() {
        DirectedGraph dg = new DirectedGraph();
        UndirectedGraph ug = new UndirectedGraph();
        for (int i = 1; i < 5; i++) {
            dg.add();
            ug.add();
        }
        dg.add(1, 2);
        dg.add(1, 2);
        ug.add(1, 2);
        ug.add(2, 1);
        assertEquals(ug.edgeId(1, 2), ug.edgeId(2, 1));
        assertTrue(ug.contains(2, 1));
        assertNotEquals(dg.edgeId(1, 2), dg.edgeId(2, 1));
        assertEquals(dg.edgeSize(), 1);
        assertEquals(ug.edgeSize(), 1);
    }
}
