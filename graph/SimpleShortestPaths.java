package graph;

/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Florence Lau
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
        _graph = G;
        weights = new double[_graph.maxVertex() + 1];
        _predecessors = new int[_graph.maxVertex() + 1];
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    @Override
    protected abstract double getWeight(int u, int v);

    @Override
    public double getWeight(int v) {
        return weights[v];
    }

    @Override
    protected void setWeight(int v, double w) {
        weights[v] = w;
    }

    @Override
    public int getPredecessor(int v) {
        return _predecessors[v];
    }

    @Override
    protected void setPredecessor(int v, int u) {
        _predecessors[v] = u;
    }

    /** Array of vertex weights. */
    private double[] weights;

    /** Array of vertex predecessors. */
    private int[] _predecessors;

    /** The graph where shortest paths are found. */
    private Graph _graph;

}
