import java.io.*;
import java.util.*;

public class Navigation {

    private final MapFileParser mapFileParser;
    private final ShortestPathAlgorithm algorithm = new ShortestPathAlgorithm();

    private List<Node> nodes;

    public static void main(String[] args) throws IOException {
        Navigation navigation = new Navigation("noder.txt",
                                               "kanter.txt",
                                               "interessepkt.txt");

        String startPlace = "Trondheim";
        String goalPlace = "Helsinki";

        Node startNode = navigation.getPointOfInterest(startPlace);
        Node goalNode = navigation.getPointOfInterest(goalPlace);

        System.out.println(String.format("\nInitiating search: %s to %s", startPlace, goalPlace));

        Route route = navigation.runAStarTimed(startNode, goalNode);
//        Route route = navigation.runDijkstraTimed(startNode, goalNode);

        System.out.println(String.format("Search finished. Visited %d nodes", route.numberOfVisitedNodes));
        System.out.println();
        route.writeToFile("A Star");


//        List<Route> routes = navigation.findNShortestPaths(startNode, 10, 2);
//        Route.writeNClosestToFile(routes, "bensinstasjoner");
    }

    public Navigation(String nodesFilename, String edgesFilename, String pointsOfInterestFilename) throws IOException {
        this.nodes = new ArrayList<>();
        this.mapFileParser = new MapFileParser(this, nodesFilename, edgesFilename, pointsOfInterestFilename);
    }

    List<Route> findNShortestPaths(Node startNode, int n, int interestCode) {
        return algorithm.findNShortestPaths(startNode, n, interestCode);
    }

    public Route runDijkstraTimed(Node startNode, Node goalNode) {
        Date startTime = new Date();
        double actualTime;
        Date finishTime;

        Route route = runDijkstra(startNode, goalNode);

        finishTime = new Date();
        actualTime = (double) finishTime.getTime() - startTime.getTime();
        System.out.println(String.format("A* finished in %f milliseconds", actualTime));

        return route;
    }

    public Route runDijkstra(Node startNode, Node goalNode) {
        return algorithm.dijkstras(startNode, goalNode);
    }

    public Route runAStarTimed(Node startNode, Node goalNode) {
        Date startTime = new Date();
        double actualTime;
        Date finishTime;

        Route route = runAStar(startNode, goalNode);

        finishTime = new Date();
        actualTime = (double) finishTime.getTime() - startTime.getTime();
        System.out.println(String.format("A* finished in %f milliseconds", actualTime));

        return route;
    }

    public Route runAStar(Node startNode, Node goalNode) {
        return algorithm.aStar(startNode, goalNode);
    }

    Node getPointOfInterest(String pointOfInterest) {
        int nodeNumber = mapFileParser.pointsOfInterest.get(pointOfInterest);
        return nodes.get(nodeNumber);
    }

    public Node getNode(int nodeNumber) {
        return nodes.get(nodeNumber);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
