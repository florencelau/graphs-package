package graph;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/** Unit tests for the ShortestPaths class.
 *  @author Florence Lau
 */
public class ShortestPathsTests {

    class ConstructSP extends SimpleShortestPaths {
        ConstructSP(Graph g, int source) {
            super(g, source);
            _g = g;
        }

        @Override
        public double getWeight(int u, int v) {
            return getWeight(u) + getWeight(v);
        }

        /** My graph. */
        private Graph _g;
    }

    @Test
    public void testPath() {
        Graph g = new UndirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(1, 4);
        g.add(5, 3);
        g.add(2, 1);
        g.add(3, 2);
        ShortestPaths s = new ConstructSP(g, 2);
        s.setPaths();
        ArrayList<Double> weights = new ArrayList<>();
        weights.add(s.getWeight(1));
        weights.add(s.getWeight(2));
        weights.add(s.getWeight(3));
        weights.add(s.getWeight(4));
        weights.add(s.getWeight(5));
        ArrayList<Double> correct = new ArrayList<>();
        correct.add(Double.POSITIVE_INFINITY);
        correct.add(0.0);
        correct.add(Double.POSITIVE_INFINITY);
        correct.add(Double.POSITIVE_INFINITY);
        correct.add(Double.POSITIVE_INFINITY);
        assertEquals(weights, correct);
    }
}
