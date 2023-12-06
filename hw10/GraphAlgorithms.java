import java.util.*;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Mark T. Massey
 * @version 1.0
 * @userid mmassey42 (i.e. gburdell3)
 * @GTID 903634890 (i.e. 900000000)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Cannot pass null start or graph.");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Graph does not contain this start vertex.");
        }
        Queue<Vertex<T>> pq = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        List<Vertex<T>> ans = new LinkedList<>();

        visited.add(start);
        pq.add(start);

        while (pq.size() > 0) {
            Vertex<T> v = pq.remove();
            ans.add(v);
            for (VertexDistance<T> w : graph.getAdjList().get(v)) { // how to get adjacent list of one vertex
                if (!visited.contains(w.getVertex())) {
                    visited.add(w.getVertex());
                    pq.add(w.getVertex());
                }
            }
        }
        return ans;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Cannot pass null start or graph.");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Graph does not contain this start vertex.");
        }

        Set<Vertex<T>> visited = new HashSet<>();
        List<Vertex<T>> ans = new LinkedList<>();

        dfs(start, graph, visited, ans);
        return ans;
    }

    /**
     * Helper method for depth first search.
     *
     * @param vertex  starting vertex of that pass of the search
     * @param graph   graph that the vertices are in
     * @param visited set of visited vertices
     * @param ans     set with the answer of vertices in order
     * @param <T>     all parameters must be of type T
     */
    private static <T> void dfs(Vertex<T> vertex, Graph<T> graph, Set<Vertex<T>> visited, List<Vertex<T>> ans) {
        visited.add(vertex);
        ans.add(vertex);
        for (VertexDistance<T> w : graph.getAdjList().get(vertex)) { // how to get adjacent list of one vertex
            if (!visited.contains(w.getVertex())) {
                dfs(w.getVertex(), graph, visited, ans);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Cannot pass null start or graph.");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Graph does not contain this start vertex.");
        }

        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();

        for (Vertex<T> vertex : graph.getVertices()) {
            distanceMap.put(vertex, (int) Double.POSITIVE_INFINITY);
        }
        pq.add(new VertexDistance<>(start, 0));
        while (pq.size() > 0 && visitedSet.size() != graph.getVertices().size()) {
            VertexDistance<T> u = pq.remove();
            if (!visitedSet.contains(u.getVertex())) {
                visitedSet.add(u.getVertex());
                distanceMap.put(u.getVertex(), u.getDistance());
                for (VertexDistance<T> w : graph.getAdjList().get(u.getVertex())) {
                    if (!visitedSet.contains(w.getVertex())) {
                        int num = u.getDistance() + w.getDistance();
                        pq.add(new VertexDistance<T>(w.getVertex(), num));
                    }
                }
            }
        }
        return distanceMap;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }

        DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>();
        Set<Edge<T>> edgeSet = new HashSet<>();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>(graph.getEdges());

        while (!pq.isEmpty() && edgeSet.size() < (graph.getVertices().size() - 1) * 2) {
            Edge<T> tmp = pq.remove();
            Vertex<T> u = tmp.getU();
            Vertex<T> v = tmp.getV();
            if (!disjointSet.find(u).equals(disjointSet.find(v))) {
                disjointSet.union(u, v);
                edgeSet.add(tmp);
                edgeSet.add(new Edge<>(v, u, tmp.getWeight()));
            }
        }
        if (edgeSet.size() == (graph.getVertices().size() - 1) * 2) {
            return edgeSet;
        }
        return null;
    }
}
