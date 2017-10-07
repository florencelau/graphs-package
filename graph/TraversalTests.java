package graph;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/** Unit tests for the Traversals class.
 *  @author Florence Lau
 */
public class TraversalTests {
    @Test
    public void testBreadth() {
        Graph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(1, 4);
        g.add(5, 3);
        g.add(2, 1);
        g.add(3, 2);
        Collection<Integer> fringe = new ArrayList<>();
        fringe.add(1);
        fringe.add(2);
        fringe.add(3);
        fringe.add(4);
        fringe.add(5);
        BreadthFirstTraversal b = new BreadthFirstTraversal(g);
        b.traverse(fringe);
        assertEquals(b.testingMarked(), fringe);
    }

    @Test
    public void testDepth() {
        Graph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(1, 4);
        g.add(5, 3);
        g.add(2, 1);
        g.add(3, 2);
        DepthFirstTraversal d = new DepthFirstTraversal(g);
        ArrayList<Integer> order = new ArrayList<>();
        order.add(3);
        order.add(2);
        order.add(1);
        order.add(4);
        d.traverse(3);
        assertEquals(d.testingMarked(), order);
    }
}
