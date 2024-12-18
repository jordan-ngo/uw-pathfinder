/*
 * Copyright (C) 2022 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */
package pathfinder;
import graph.Graph;
import pathfinder.datastructures.Point;
import pathfinder.datastructures.Path;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPathsParser;
import pathfinder.parser.CampusPath;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CampusMap implements ModelAPI {
    /**
     * AF: R(f): buildings = all buildings on campus, paths = all parsed paths from campus_paths.csv, graph = graph
     * containing all nodes and edges related to campus
     *
     * RI: R(i): buildings != null, paths != null, graph != null
     */
    private final Graph<Point, Double> graph;
    private final List<CampusPath> paths;
    private final List<CampusBuilding> buildings;


    /**
     * This is a constructor for campus maps that initializes graph, paths, and buildings.
     *
     *@spec.modifies Graph graph
     *@spec.modifies List buildings
     *@spec.modifies List paths
     *@spec.effects initialize graph, paths, buildings, and checks for any rep exposure
     */
    public CampusMap() {
        this.buildings = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        this.paths = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        this.graph = new Graph<>();
        checkRep();

    }
    @Override
    public boolean shortNameExists(String shortName) {
        Map<String, String> map = buildingNames();
        return map.containsKey(shortName);

    }

    @Override
    public String longNameForShort(String shortName) {
        Map<String, String> map = buildingNames();
        if(!map.containsKey(shortName)) {
            throw new IllegalArgumentException();

        }
        return map.get(shortName);

    }

    @Override
    public Map<String, String> buildingNames() {
        Map<String, String> map = new HashMap<>();
        for(CampusBuilding currBuilding : buildings) {
            map.put(currBuilding.getShortName(), currBuilding.getLongName());

        }
        return map;

    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        //Build graph using campus csv file
        for (CampusPath currPath : paths) {
            Point one = new Point(currPath.getX1(), currPath.getY1());
            Point two = new Point(currPath.getX2(), currPath.getY2());
            Double weight = currPath.getDistance();

            graph.addNode(one);
            graph.addNode(two);
            graph.addEdge(one, two, weight);

        }

        Point start = null;
        Point stop = null;

        //Find the start and stop points from the campus
        for (CampusBuilding currBuilding: buildings) {
            if (startShortName.equals(currBuilding.getShortName())) {
                start = new Point(currBuilding.getX(), currBuilding.getY());

            }

            if (endShortName.equals(currBuilding.getShortName())) {
                stop = new Point(currBuilding.getX(), currBuilding.getY());

            }
        }

        //use Dijkstra's algorithm
        return Dijsktra.findMinCostPath(graph, start, stop);

    }

    /**
     * Method in which the rep invariant is asserted.
     */
    public void checkRep() {
        assert (buildings != null) : "buildings cannot be null";
        assert (paths != null) : "paths cannot be null";
        assert (graph != null) : "graph cannot be null";

    }

}

