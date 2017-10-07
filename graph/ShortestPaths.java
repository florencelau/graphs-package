package graph;

/* See restrictions in Graph.java. */

import java.util.AbstractQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/** The shortest paths through an edge-weighted graph.
 *  By overriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Florence Lau
 */
@SuppressWarnings("unchecked")
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo.
     *  Used the algorithm from Hilfinger's Lecture 34 and
     *  Ching Fang's Google Slides on A* search. */
    public void setPaths() {
        TreeSetQueue<Integer> fringe = new TreeSetQueue<>();
        for (int v : _G.vertices()) {
            setWeight(v, Double.POSITIVE_INFINITY);
            setPredecessor(v, 0);
        }
        setWeight(getSource(), 0);
        setPredecessor(getSource(), 0);
        AStarTraversal t = new AStarTraversal(_G, fringe);
        t.traverse(getSource());
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        List<Integer> path = new ArrayList<>();
        path.add(v);
        int curr = getPredecessor(v);
        while (curr != getSource()) {
            path.add(curr);
            curr = getPredecessor(curr);
        }
        path.add(curr);
        Collections.reverse(path);
        return path;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;

    /** Custom traversal for A* search. */
    class AStarTraversal extends Traversal {
        /** An A* traversal using GRAPH and FRINGE. */
        protected AStarTraversal(Graph graph, TreeSetQueue fringe) {
            super(graph, fringe);
            g = graph;
            f = fringe;
        }

        @Override
        protected boolean visit(int v) {
            if (v == getDest()) {
                f.clear();
                return true;
            }
            for (int successor : g.successors(v)) {
                double totalWeight = getWeight(v)
                        + getWeight(v, successor);
                if (totalWeight < getWeight(successor)) {
                    setWeight(successor, totalWeight);
                    setPredecessor(successor, v);
                    f.remove((Object) successor);
                    f.add(successor);
                }
            }
            return false;
        }

        /** My graph. */
        private Graph g;

        /** My fringe. */
        private TreeSetQueue<Integer> f;
    }

    /** TreeSet that is used as a queue. */
    private class TreeSetQueue<Integer> extends AbstractQueue<Integer> {

        /** A custom kind of treeset queue. */
        TreeSetQueue() {
            myTreeSet = new TreeSet<>(new MyComparator<>());
        }

        @Override
        public boolean offer(Integer v) {
            try {
                myTreeSet.add(v);
                return true;
            } catch (ClassCastException excp) {
                return false;
            } catch (NullPointerException excp) {
                return false;
            } catch (IllegalArgumentException excp) {
                return false;
            }
        }

        @Override
        public Integer poll() {
            return myTreeSet.pollFirst();
        }

        @Override
        public Iterator<Integer> iterator() {
            return myTreeSet.iterator();
        }

        @Override
        public Integer peek() {
            if (myTreeSet.isEmpty()) {
                return null;
            } else {
                return myTreeSet.first();
            }
        }

        @Override
        public int size() {
            return myTreeSet.size();
        }

        /** My TreeSet. */
        private TreeSet<Integer> myTreeSet;
    }

    /** The type of comparator used in TreeSetQueue. */
    private class MyComparator<Integer> implements Comparator<Integer> {
        @Override
        public int compare(Object u, Object v) {
            int first = (int) u;
            int second = (int) v;
            double uWeight = getWeight(first) + estimatedDistance(first);
            double vWeight = getWeight(second) + estimatedDistance(second);
            if (uWeight < vWeight) {
                return -5;
            } else if (uWeight > vWeight) {
                return 5;
            } else {
                return 2;
            }
        }
    }
}
