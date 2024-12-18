package pathfinder;
import graph.Edge;
import graph.Graph;
import pathfinder.datastructures.Path;
import java.util.*;


/**
 * Dijsktra is a class that implements Dijkstra's algorithm. It takes in the Parameter type T which is a point
 * @param <T>
 */

public class Dijsktra<T>
{
    /**
     *
     * @param graph graph to be searched in
     * @param start starting point
     * @param stop ending point
     * @param <T> Data type of the start and stop points
     * @return the least cost path between start and stop, returns null if no path exists
     */
    public static <T> Path<T> findMinCostPath(Graph<T, Double> graph, T start, T stop) {

        PriorityQueue<Path<T>> act = new PriorityQueue<>(new Comparator<Path<T>>() {

            @Override
            public int compare(Path<T> one, Path<T> two) {
                return Double.compare(one.getCost(), two.getCost());

            }
        });


        Set<T> finished = new HashSet<>();
        Path<T> startingPath = new Path<T>(start);
        act.add(startingPath);


        while (!act.isEmpty()) {
            Path<T> minPath = act.remove();
            T minDest = minPath.getEnd();

            if (minDest.equals(stop)) {
                return minPath;

            }
            if (finished.contains((minDest))) {
                continue;

            }

            HashSet<Edge<T,Double>> children = graph.listEdges(minDest);

            for (Edge<T,Double> e : children) {
                if (!finished.contains(e)) {
                    double min = e.getLabel();
                    Path<T> newPath = minPath.extend(e.getChild(), min);
                    act.add(newPath);

                }

            }
            finished.add(minDest);

        }
        return null;

    }

}
