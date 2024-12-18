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

package pathfinder.scriptTestRunner;
import graph.Edge;
import graph.Graph;
import pathfinder.Dijsktra;
import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph.
 **/
public class PathfinderTestDriver {

    // ***************************
    // ***  JUnit Test Driver  ***
    // ***************************

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, Graph<String,Double>> graphs = new HashMap<String, Graph<String,Double>>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new GraphTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        Graph<String,Double> newGraph = new Graph<String,Double>();
        graphs.put(graphName, newGraph);
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Graph<String,Double> temp = graphs.get(graphName);
        temp.addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        Double edgeLabel = Double.parseDouble(arguments.get(3));

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName, Double edgeLabel) {

        Graph<String, Double> temp = graphs.get(graphName);
        temp.addEdge(parentName, childName, edgeLabel);
        output.println("added edge " + String.format("%.3f", edgeLabel) + " from " + parentName + " to " +
                childName + " in " + graphName);

    }

    private void listNodes(List<String> arguments) {

        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);

        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {

        Graph<String, Double> temp = graphs.get(graphName);
        String res = graphName + " contains:";

        TreeSet<String> sortedList =
                new TreeSet<String>(new Comparator<String>() {
                    public int compare(String one, String two) {
                        if (!one.equals(two)) {
                            return one.compareTo(two);

                        }
                        return 0;
                    }
                });

        sortedList.addAll(temp.listNodes());

        for (String node: sortedList) {
            res += " " + node;
        }
        output.println(res);
    }

    private void listChildren(List<String> arguments) {

        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);

        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {

        Graph<String, Double> g = graphs.get(graphName);
        String result = "the children of " + parentName + " in " + graphName + " are:";
        TreeSet<Edge<String,Double>> sortEdges =
                new TreeSet<Edge<String,Double>>(new Comparator<Edge<String,Double>>() {
                    public int compare(Edge<String,Double> e1, Edge<String,Double> e2) {
                        if(!(e1.getChild().equals(e2.getChild()))) {
                            return e1.getChild().compareTo(e2.getChild());

                        }
                        if (!(e1.getLabel().equals(e2.getLabel()))) {
                            return e1.getLabel().compareTo(e2.getLabel());

                        }
                        return 0;

                    }
                });

        sortEdges.addAll(g.listEdges(parentName));
        for (Edge<String,Double> e: sortEdges) {
            result += " " + e.getChild() + "(" + e.getLabel() +")";
        }
        output.println(result);
    }

    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }
        String graphName = arguments.get(0);
        String start = arguments.get(1);
        String stop = arguments.get(2);
        findPath(graphName, start, stop);

    }


    private void findPath(String graphName, String start, String stop) {

        Graph<String, Double> graph = graphs.get(graphName);
        List<Edge<String, Double>> path = Dijsktra.findMinCostPath(graphName, start, stop);
        if (!graph.containsNode(start) && !graph.containsNode(stop)) {

            output.println("unknown character " + start);
            output.println("unknown character " + stop);

        } else if (!graph.containsNode(start)) {
            output.println("unknown character " + start);

        } else if (!graph.containsNode(stop)) {
            output.println("unknown character " + stop);

        } else {
            output.println("path from " + start + " to " + stop + ":");
            String current = start;

            if (path == null) {
                output.println("no path found");
            } else {

                double totalCost = 0;
                for (Edge<String,Double> e: path) {

                    if (!e.getChild().equals(current)) {

                        output.println(current + " to " + e.getChild() + " with weight " + String.format("%.3f",e.getLabel()));
                        totalCost += e.getLabel();
                        current = e.getChild();

                    }
                }
                output.println("total cost: " + String.format("%.3f",totalCost));
            }

        }

    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}