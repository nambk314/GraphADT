package edu.union.adt.graph;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.AbstractMap.SimpleEntry;
import java.util.Stack;
/**
 * A graph that establishes connections (edges) between objects of
 * (parameterized) type V (vertices).  The edges are directed.  An
 * undirected edge between u and v can be simulated by two edges: (u,
 * v) and (v, u).
 *
 * The API is based on one from
 *     http://introcs.cs.princeton.edu/java/home/
 *
 * Some method names have been changed, and the Graph type is
 * parameterized with a vertex type V instead of assuming String
 * vertices.
 *
 * @author Aaron G. Cass, Nam Bui
 * @version 1
 */
public class GraphImplementation<V> implements Graph<V>
{
    private List<List<V>> graph;
    /**
     * Create an empty graph.
     */
    public GraphImplementation()
    {
        graph = new ArrayList<>();
    }

    /**
     * @return the number of vertices in the graph.
     */
    public int numVertices()
    {
        return graph.size();
    }

    /**
     * @return the number of edges in the graph.
     */
    public int numEdges()
    {
        int count = 0;
        if (numVertices() == 0) {
            return 0;
        } else {
            for (List<V> element : graph) {
                count += element.size()-1;
            }
            return count;
        }

    }

    /**
     * Gets the number of vertices connected by edges from a given
     * vertex.  If the given vertex is not in the graph, throws a
     * RuntimeException.
     *
     * @param vertex the vertex whose degree we want.
     * @return the degree of vertex 'vertex'
     */
    public int degree(V vertex) throws RuntimeException 
    {
         
       int x = 0;
       int size = graph.size();
       int count = 0;
       while (x < size && !(graph.get(x).get(0)).equals(vertex)) {
            x ++;

       }
       if (x >= size) {
            throw new RuntimeException("Vertex is not in the graph");
       } else {
            count = graph.get(x).size() - 1;
       }
       return count;
    }

    /**
     * Adds a directed edge between two vertices.  If there is already an edge
     * between the given vertices, does nothing.  If either (or both)
     * of the given vertices does not exist, it is added to the
     * graph before the edge is created between them.
     *
     * @param from the source vertex for the added edge
     * @param to the destination vertex for the added edge
     */
    public void addEdge(V from, V to)
    {
        if (!graph.contains(from)) {
            addVertex(from);
        }

        if (!graph.contains(to)) {
            addVertex(to);
        }

        for (List<V> vertices : graph) {
            if (vertices.get(0).equals(from)) {
                int i = 1;
                int size = vertices.size();
                while (i < size && !(vertices.get(i).equals(to))) {
                    i++;
                }
                if (i >= size) {
                    vertices.add(to);
                }
                
            }
        }
    }

    /**
     * Adds a vertex to the graph.  If the vertex already exists in
     * the graph, does nothing.  If the vertex does not exist, it is
     * added to the graph, with no edges connected to it.
     *
     * @param vertex the vertex to add
     */
    public void addVertex(V vertex)
    {
        int x = 0;
        int size = graph.size();
        while (x < size && !(graph.get(x).get(0)).equals(vertex)) {
            x++;
        }
        if (x >= size) {
            List<V> vertexList = new ArrayList<>();
            vertexList.add(vertex);
            graph.add(vertexList);
        }
    }

    /**
     * @return an iterable collection for the set of vertices of
     * the graph.
     */
    public Iterable<V> getVertices()
    {
        List<V> items = new ArrayList<>();
        for (List<V> element : graph) {
            items.add(element.get(0));
        }
        return items;
    }

    /**
     * Gets the vertices adjacent to a given vertex.  A vertex y is
     * "adjacent to" vertex x if there is an edge (x, y) in the graph.
     * Because edges are directed, if (x, y) is an edge but (y, x) is
     * not an edge, we would say that y is adjacent to x but that x is
     * NOT adjacent to y.
     *
     * @param from the source vertex
     * @return an iterable collection for the set of vertices that are
     * the destinations of edges for which 'from' is the source
     * vertex.  If 'from' is not a vertex in the graph, returns an
     * empty iterator.
     */
    public Iterable<V> adjacentTo(V from)
    {
        int x = 0;
        int size = graph.size();
        List<V> temp = new ArrayList<>();
        while (x < size && !(graph.get(x).get(0)).equals(from)) {
            x++;
        }
        if (x < size) {
            List<V> items = graph.get(x);
            for (int y = 1; y < items.size(); y++) {
                temp.add(items.get(y));
            }
        }
        return temp;
    }

    /**
     * Tells whether or not a vertex is in the graph.
     *
     * @param vertex a vertex
     * @return true iff 'vertex' is a vertex in the graph.
     */
    public boolean contains(V vertex)
    {
        int x = 0;
        int size = graph.size();
        while (x < size && !(graph.get(x).get(0)).equals(vertex)) {
            x++;
        }

        if (x >= size) {
            return false;
        } else {
            return true;
        }

     }

    /**
     * Tells whether an edge exists in the graph.
     *
     * @param from the source vertex
     * @param to the destination vertex
     *
     * @return true iff there is an edge from the source vertex to the
     * destination vertex in the graph.  If either of the given
     * vertices are not vertices in the graph, then there is no edge
     * between them.
     */
    public boolean hasEdge(V from, V to)
    {
        boolean edge = false;
        if (!graph.contains(from) || !graph.contains(to)) {
            edge = false;
        }
        for (List<V> vertices : graph) {
            if (vertices.get(0).equals(from)) {
                for (int x = 1; x < vertices.size(); x++) {
                    if (vertices.get(x).equals(to)) {
                        edge = true;
                    }
                }
            }
        }

        return edge;
    }

    /**
     * Gives a string representation of the graph.  The representation
     * is a series of lines, one for each vertex in the graph.  On
     * each line, the vertex is shown followed by ": " and then
     * followed by a list of the vertices adjacent to that vertex.  In
     * this list of vertices, the vertices are separated by ", ".  For
     * example, for a graph with String vertices "A", "B", and "C", we
     * might have the following string representation:
     *
     * <PRE>
     * A: A, B
     * B:
     * C: A, B
     * </PRE>
     *
     * This representation would indicate that the following edges are
     * in the graph: (A, A), (A, B), (C, A), (C, B) and that B has no
     * adjacent vertices.
     *
     * Note: there are no extraneous spaces in the output.  So, if we
     * replace each space with '*', the above representation would be:
     *
     * <PRE>
     * A:*A,*B
     * B:
     * C:*A,*B
     * </PRE>
     *
     * @return the string representation of the graph
     */
    public String toString()
    {
        String representation = "";
        for (List<V> element : graph) {
            representation += element.get(0) + ":";
            for (int i = 1; i < element.size(); i++) {
                representation += " " + element.get(i);
            }
            representation += "\n";
        }
        representation.substring(0, (representation.length()));
        return representation;
    }
    @Override
    public boolean equals(Object other) {
        boolean answer = false;
            if (!(other instanceof Graph)) {
            answer = false;
        }
        GraphImplementation that = (GraphImplementation) other;

        if ((this.graph.equals(that.graph))) {
            answer = true;
        }
        return answer;
    }

        /**
     * Tells whether the graph is empty.
     *
     * @return true iff the graph is empty. A graph is empty if it has
     * no vertices and no edges.
     */
    public boolean isEmpty()
    {
        return (numVertices() == 0 && numEdges() == 0);
    }

    /**
     * Removes and vertex from the graph.  Also removes any edges
     * connecting from the edge or to the edge.
     *
     * <p>Postconditions:
     *
     * <p>If toRemove was in the graph:
     * <ul>
     * <li>numVertices = numVertices' - 1
     * <li>toRemove is no longer a vertex in the graph
     * <li>for all vertices v: toRemove is not in adjacentTo(v)
     * </ul>
     *
     * @param toRemove the vertex to remove.
     */
    public void removeVertex(V toRemove)
    {
        for (int x = 0; x <graph.size(); x++) {
            if (graph.get(x).get(0).equals(toRemove)) {
                graph.remove(x);
            }
        }
        for (List<V> element : graph) {
            int y = 1;
            while (y<element.size()){
                 if (element.get(y).equals(toRemove)){
                    element.remove(y);
                 }
                 y++;
            }
        }
    }

    /**
     * Removes an edge from the graph.
     *
     * <p>Postcondition: If from and to were in the graph and (from,
     * to) was an edge in the graph, then numEdges = numEdges' - 1
     */
    public void removeEdge(V from, V to)
    {
        for (List<V> element : graph) {
            if (element.get(0).equals(from)){
                int x = 1;
                while (x < element.size()) {
                    if (element.get(x).equals(to)){
                        element.remove(x);
                    }
                }
            }
        }
    }

    /**
     * Tells whether there is a path connecting two given vertices.  A
     * path exists from vertex A to vertex B iff there exists a
     * sequence x_1, x_2, ..., x_n where:
     *
     * <ul>
     * <li>x_1 = A
     * <li>x_n = B
     * <li>for all i from 1 to n-1, (x_i, x_{i+1}) is an edge in the graph.
     * </ul>
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return true iff there is a path from 'from' to 'to' in the graph.
     */
    public boolean hasPath(V from, V to) {
        boolean result = false;
        if (contains(from) && contains(to)) {
            List<V> visitedVertex = new ArrayList<V>();
            visitedVertex.add(from);
            Queue<V> nextVertex = new LinkedList<V>();
            
            nextVertex.add(from);

            while (nextVertex.size() != 0 && !nextVertex.peek().equals(to)) {
                V current = nextVertex.poll();
                ArrayList<V> source = getVertexList(current);

                for (int x = 1; x<source.size(); x ++) {
                    if (!visitedVertex.contains(source.get(x))) {
                        nextVertex.add(source.get(x));
                        visitedVertex.add(source.get(x));
                    }
                }
                
            }
            if (nextVertex.size() != 0) {
                result = true;
            } 
        }
        return result;
        
    }

    private ArrayList<V> getVertexList(V current) {
        ArrayList<V> temp = new ArrayList<V>();
        for (List<V> element : graph) {
            if (element.get(0).equals(current)) {
                temp.addAll(element);
            }
        }
        return temp;
    }

    /**
     * Gets the length of the shortest path connecting two given
     * vertices.  The length of a path is the number of edges in the
     * path.
     *
     * <ol> 
     * <li>If from = to, shortest path has length 0
     * <li>Otherwise, shortest path length is length of the shortest
     * possible path connecting from to to.  
     * </ol>
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return the length of the shortest path from 'from' to 'to' in
     * the graph.  If there is no path, returns Integer.MAX_VALUE
     */
    public int pathLengthRecursion(V from, V to, Stack<V> holder)
    {
        if (contains(from) && contains(to)) {
            if (from.equals(to)) {
                holder.push(from);
                return holder;
            } else {
                    List<V> visitedVertex = new ArrayList<V>();
                    visitedVertex.add(from);
                    Queue<SimpleEntry<V, Integer>> nextVertex = new LinkedList<SimpleEntry<V, Integer>>();
                    SimpleEntry<V, Integer> fromPair = new SimpleEntry<V, Integer> (from, shortestDistance);
                    nextVertex.add(fromPair);

                    while (nextVertex.size() != 0 && !nextVertex.peek().getKey().equals(to)) {
                        SimpleEntry<V, Integer> current = nextVertex.poll();
                        ArrayList<V> source = getVertexList(current.getKey());

                        for (int x = 1; x<source.size(); x ++) {
                            if (!visitedVertex.contains(source.get(x))) {
                                int temp = current.getValue().intValue();
                                temp++;
                                Integer distance = new Integer(temp);
                                SimpleEntry<V, Integer> nextSourcePair = new SimpleEntry<V, Integer> ((source.get(x)), distance);
                                nextVertex.add(nextSourcePair);
                                visitedVertex.add(source.get(x));
                            }
                        }
                        
                    }
                    if (nextVertex.size() != 0) {
                        V vertex = nextVertex.peek().getKey();
                        holder.push(vertex);
                        pathLengthRecursion(from, current, Stack<V> holder)

                    }
            }
 
        }
    }

    public int pathLengthRecursion(V from, V to) {
        if (hasPath(from, to)) {
                Stack<V> holder = new Stack<V>();
                pathLengthRecursion(from, to, holder);
        }

    }

    /**
     * Returns the vertices along the shortest path connecting two
     * given vertices.  The vertices should be given in the order x_1,
     * x_2, x_3, ..., x_n, where:
     *
     * <ol>
     * <li>x_1 = from
     * <li>x_n = to
     * <li>for all i from 1 to n-1: (x_i, x_{i+1}) is an edge in the graph.
     * </ol>
     * 
     * @param from the source vertex
     * @param to the destination vertex
     * @return an Iterable collection of vertices along the shortest
     * path from 'from' to 'to'.  The Iterable should include the
     * source and destination vertices.
     */
    public Iterable<V> getPath(V from, V to)
    {
        return null;
    }
}
