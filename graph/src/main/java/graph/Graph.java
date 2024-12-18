package graph;
import java.util.*;

/**
 * Graph is a implementation of a Directed Labeled Graph.
 * <p>
 * Graph is something that can be described as a list of nodes and list of edges that have both been labeled.
 * However,the graph cannot store two nodes with the exact same label
 * </p>
 *
 */
public class Graph<T,E> {
    /**
     * AF: edgeList = list containing edges, nodeList = list containing nodes, edgeList.size() = number of edges in edgeList,
     * nodeList.size() = number of nodes in nodeList()
     *
     * RI: edgeList.size() greater than or equal to zero, nodeList.size() greater than or equal to zero,
     * nodeList cannot have any nodes with the same label, edgeList cannot have any edges with the same label
     * private HashMap<T, HashSet<Edge<T,E>>> map;
     *
     **/


    //HashMap that is containing T nodes and a HashSet of Edge objects that has T nodes and E label
    private HashMap<T, HashSet<Edge<T,E>>> map;

    public Graph() {
        map = new HashMap<T, HashSet<Edge<T,E>>>();
    }



    /**
     *
     * @param n labeled node that is to be added
     * @spec.effects adds the input node to a graph
     * @spec.requires a different node with the same label cannot already exist in nodeList
     */
    public void addNode(T n) {
        if (n == null) {
            throw new IllegalArgumentException("Node cannot be null");

        }

        if (!containsNode(n)) {
            map.put(n, new HashSet<Edge<T,E>>());

        }
    }



    /**
     * Method that adds a labeled edge between two nodes on the graph
     *
     * @param parent arriving node of edge
     * @param child leaving node of edge
     * @param label label of the edge
     * @spec.effects adds an edge to graph with specified starting node and ending node
     */
    public void addEdge(T parent, T child, E label) {
        if (label == null) {
            throw new IllegalArgumentException();

        }
        if (parent == null || child == null) {
            throw new IllegalArgumentException();

        }
        addNode(parent);


        Edge<T,E> edge = new Edge<T,E>(parent, child, label);
        HashSet<Edge<T,E>> edges = map.get(parent);


        if (edges == null) {
            edges = new HashSet<Edge<T,E>>();
            edges.add(edge);

        }

        if (!edges.contains(edge)) {
            edges.add(edge);

        }

    }


    /**
     * @param n node to be searched for in the graph
     * @return true or false is dependant on whether or not the n node exists in the grpah
     */
    public boolean containsNode(T n) {
        if (n == null){
            throw new IllegalArgumentException("Node is null");

        }
        return map.containsKey(n);

    }

    /**
     * @return list of nodes in nodeList
     */
    public HashSet<T> listNodes() {
        HashSet<T> res = new HashSet<>();
        for (T n: map.keySet()) {
            res.add(n);
        }
        return res;

    }



    /**
     * @spec.requires parent node has to exist in the graph already
     * @param node list of children
     * @return list of children of parent node
     */
    public HashSet<T> listChildren(T node) {
        if (node == null) {
            throw new IllegalArgumentException();

        }
        if (!map.containsKey(node)) {
            throw new IllegalArgumentException("Node is not in the graph");

        }

        HashSet<T> result = new HashSet<T>();
        for (Edge<T,E> e: map.get(node)) {
            result.add(e.getChild());

        }
        return result;

    }



    /**
     * @spec.requires parent node has to exist in the graph already
     * @param node parent node
     * @return list of associated edges of children nodes
     */
    public HashSet<Edge<T,E>> listEdges(T node) {
        HashSet<Edge<T,E>> result = new HashSet<Edge<T,E>>();

        for (Edge<T,E> e: map.get(node)) {
            result.add(e);

        }
        return result;

    }
}
