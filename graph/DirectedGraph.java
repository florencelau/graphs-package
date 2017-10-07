package graph;

/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Florence Lau
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        return getPredecessors(v).size();
    }

    @Override
    public int predecessor(int v, int k) {
        int temp = 0;
        for (int counter = 0; counter < k; k++) {
            if (predecessors(v).hasNext()) {
                temp = predecessors(v).next();
            } else {
                return 0;
            }
        }
        return temp;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        return Iteration.iteration(getPredecessors(v));
    }

}
