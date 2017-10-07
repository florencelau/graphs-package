package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;
import java.util.Collections;

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 * Note: Used Cantor's pairing function for edgeID's. Idea from
 * Piazza.
 *  @author Florence Lau
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        vertices = new ArrayList<>();
        outgoingEdges = new ArrayList<>();
        incomingEdges = new ArrayList<>();
        orderedEdges = new ArrayList<>();
        deletedVertices = new ArrayList<>();
        numVertices = 0;
        _maxVertex = 0;
        _prevMaxVertex = 0;
        numEdges = 0;
    }

    @Override
    public int vertexSize() {
        return numVertices;
    }

    @Override
    public int maxVertex() {
        if (vertices.isEmpty()) {
            return 0;
        }
        return _maxVertex;
    }

    @Override
    public int edgeSize() {
        return numEdges;
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (vertices.contains(v)) {
            return outgoingEdges.get(v).size();
        }
        return 0;
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return vertices.contains(u);
    }

    @Override
    public boolean contains(int u, int v) {
        if (vertices.contains(u) && vertices.contains(v)) {
            if (!isDirected()) {
                if (outgoingEdges.get(u).contains(v)
                    || outgoingEdges.get(v).contains(u)) {
                    return true;
                }
                return false;
            } else {
                if (outgoingEdges.get(u).contains(v)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public int add() {
        int newVertex;
        if (!deletedVertices.isEmpty()) {
            newVertex = Collections.min(deletedVertices);
            deletedVertices.remove(Collections.min(deletedVertices));
        } else {
            newVertex = vertexSize() + 1;
        }
        vertices.add(newVertex);
        while (newVertex > outgoingEdges.size() - 1) {
            outgoingEdges.add(new ArrayList<>());
        }
        outgoingEdges.add(newVertex, new ArrayList<>());
        while (newVertex > incomingEdges.size() - 1) {
            incomingEdges.add(new ArrayList<>());
        }
        incomingEdges.add(newVertex, new ArrayList<>());
        if (newVertex > _maxVertex) {
            _prevMaxVertex = _maxVertex;
            _maxVertex = newVertex;
        }
        numVertices += 1;
        return newVertex;
    }

    @Override
    public int add(int u, int v) {
        if (vertices.contains(u) && vertices.contains(v)) {
            if (!isDirected()) {
                int[] d = new int[2];
                d[0] = Math.min(u, v);
                d[1] = Math.max(u, v);
                if (!orderedEdges.contains(d)) {
                    orderedEdges.add(d);
                }
            } else {
                int[] a = new int[2];
                a[0] = u;
                a[1] = v;
                if (!orderedEdges.contains(a)) {
                    orderedEdges.add(a);
                }
            }
            if (!outgoingEdges.get(u).contains(v)) {
                outgoingEdges.get(u).add(v);
                numEdges += 1;
                if (isDirected()) {
                    if (!incomingEdges.get(v).contains(u)) {
                        incomingEdges.get(v).add(u);
                    }
                } else {
                    if (!outgoingEdges.get(v).contains(u)) {
                        outgoingEdges.get(v).add(u);
                    }
                }
            }
            return edgeId(u, v);
        }
        return 0;
    }

    @Override
    public void remove(int v) {
        if (vertices.contains(v)) {
            ArrayList<Integer> indices = new ArrayList<>();
            for (int o : outgoingEdges.get(v)) {
                indices.add(o);
            }
            for (int i : indices) {
                remove(v, i);
            }
            indices.clear();
            for (int i : incomingEdges.get(v)) {
                indices.add(i);
            }
            for (int j : indices) {
                remove(j, v);
            }
            ArrayList<int[]> toRemove = new ArrayList<>();
            for (int[] a : orderedEdges) {
                if (a[0] == v || a[1] == v) {
                    toRemove.add(a);
                }
            }
            orderedEdges.removeAll(toRemove);
            vertices.remove((Object) v);
            if (v == _maxVertex) {
                _maxVertex = _prevMaxVertex;
            }
            deletedVertices.add(v);
            numVertices -= 1;
        }
    }

    @Override
    public void remove(int u, int v) {
        if (vertices.contains(u) && vertices.contains(v)) {
            if (outgoingEdges.get(u).contains(v)) {
                outgoingEdges.get(u).remove((Object) v);
                numEdges -= 1;
                if (isDirected()) {
                    incomingEdges.get(v).remove((Object) u);
                } else {
                    outgoingEdges.get(v).remove((Object) u);
                }
            }
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        return Iteration.iteration(vertices);
    }

    @Override
    public int successor(int v, int k) {
        int temp = 0;
        for (int counter = 0; counter < k; k++) {
            if (successors(v).hasNext()) {
                temp = successors(v).next();
            } else {
                return 0;
            }
        }
        return temp;
    }

    @Override
    public abstract int predecessor(int v, int k);

    /** Returns predecessors of vertex V. */
    protected ArrayList<Integer> getPredecessors(int v) {
        return incomingEdges.get(v);
    }

    @Override
    public Iteration<Integer> successors(int v) {
        return Iteration.iteration(outgoingEdges.get(v));
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        return Iteration.iteration(orderedEdges);
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!vertices.contains(v)) {
            throw new IllegalArgumentException("vertex not from Graph");
        }
    }

    @Override
    protected int edgeId(int u, int v) {
        int first = Math.min(u, v);
        int second = Math.max(u, v);
        if (!isDirected()) {
            if (!outgoingEdges.get(first).contains(second)) {
                return 0;
            } else {
                return (((first + second)
                        * (first + second + 1)) / 2) + second;
            }
        } else {
            if (!outgoingEdges.get(u).contains(v)) {
                return 0;
            }
        }
        return (((u + v) * (u + v + 1)) / 2) + v;
    }

    /** List of vertices in this graph. */
    private ArrayList<Integer> vertices;

    /** List containing outgoing edges. */
    private ArrayList<ArrayList<Integer>> outgoingEdges;

    /** List containing incoming edges. */
    private ArrayList<ArrayList<Integer>> incomingEdges;

    /** List containing all edges. */
    private ArrayList<int[]> orderedEdges;

    /** List containing deleted vertices. */
    private ArrayList<Integer> deletedVertices;

    /** Highest vertex number. */
    private int _maxVertex;

    /** Previous highest vertex number. */
    private int _prevMaxVertex;

    /** Number of vertices in this graph. */
    private int numVertices;

    /** Number of edges in this graph. */
    private int numEdges;

}
