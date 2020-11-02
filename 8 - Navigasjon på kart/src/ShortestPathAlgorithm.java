import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ShortestPathAlgorithm {
    private PriorityQueue<Node> priorityQueue;
    private Node startNode;
    private Node goalNode;


    public Route aStar(Node startNode, Node goalNode) {
        this.startNode = startNode;
        this.goalNode = goalNode;

        startNode.distance = 0;
        startNode.estimatedDistanceToGoalNode = distance(startNode, goalNode);
        priorityQueue = new PriorityQueue<>(new NodeFullDistanceComparator());

        return runAStarAndGetRoute();
    }

    private Route runAStarAndGetRoute() {
        Node currentNode = startNode;
        int numberOfVisitedNodes = 0;
        priorityQueue.add(currentNode);

        while (isNotFinished(currentNode)) {
            currentNode = priorityQueue.poll();
            numberOfVisitedNodes++;

            expandWithHeuristic(currentNode);
        }

        return new Route(numberOfVisitedNodes, startNode, currentNode);
    }

    boolean isNotFinished(Node currentNode) {
        return currentNode != null && !currentNode.equals(goalNode);
    }

    /**
     * Search neighbors of current node
     */
    private void expandWithHeuristic(Node node) {
        for (Edge edge : node.edges) {
            Node successor = edge.to;

            if (successor.costToGoalNodeIsNotEstimated())
                successor.estimatedDistanceToGoalNode = distance(successor, goalNode);

            // distance so far plus from current node to neighbor
            double newDistanceFromStartNode = node.distance + edge.timeToDrive;

            if (newDistanceFromStartNode < successor.distance)
                addToQueue(successor, node, newDistanceFromStartNode);
        }
    }

    /**
     * Prepare and add current node to the priority queue
     */
    private void addToQueue(Node successor, Node predecessor, double newDistance) {
        successor.distance = newDistance;
        successor.predecessor = predecessor;
        priorityQueue.add(successor);
    }

    /**
     * Calculate the estimated distance from node n1 to node n2
     * using the Haversine distance formula.
     */
    int distance(Node n1, Node n2) {
        double sin_latitude = Math.sin((((n1.latitude * Math.PI) / 180) - ((n2.latitude * Math.PI) / 180)) / 2.0);
        double sin_longitude = Math.sin((((n1.longitude * Math.PI) / 180) - ((n2.longitude * Math.PI) / 180)) / 2.0);

        return (int) (35285538.46153846153846153846 * Math.asin(Math.sqrt(
                sin_latitude * sin_latitude + n1.cosineLatitude * n2.cosineLatitude * sin_longitude * sin_longitude)));
    }

    public Route dijkstras(Node startNode, Node goalNode) {
        this.startNode = startNode;
        this.goalNode = goalNode;

        startNode.distance = 0;
        priorityQueue = new PriorityQueue<>(new NodeDistanceComparator());

        return runDijkstraAndGetRoute();
    }

    private Route runDijkstraAndGetRoute() {
        Node currentNode = startNode;
        int numberOfVisitedNodes = 0;
        priorityQueue.add(currentNode);

        while (isNotFinished(currentNode)) {
            currentNode = priorityQueue.poll();
            numberOfVisitedNodes++;

            expandWithDistance(currentNode);
        }

        return new Route(numberOfVisitedNodes, startNode, currentNode);
    }

    private void expandWithDistance(Node node) {
        for (Edge edge : node.edges) {
            Node successor = edge.to;

            double newDistance = node.distance + edge.timeToDrive;

            if (newDistance < successor.distance)
                addToQueue(successor, node, newDistance);
        }
    }

    public List<Route> findNShortestPaths(Node startNode, int n, int interestCode) {
        int numberOfFoundNodes = 0;
        int numberOfVisitedNodes = 0;

        priorityQueue = new PriorityQueue<>(new NodeDistanceComparator());
        List<Route> routes = new ArrayList<>();

        startNode.distance = 0;

        priorityQueue.add(startNode);
        Node currentNode = startNode;

        while (isNotFinished(currentNode)) {
            currentNode = priorityQueue.poll();


            if (hasCorrectInterestCode(currentNode, interestCode)) {
                routes.add(new Route(numberOfVisitedNodes, startNode, currentNode));
                numberOfFoundNodes++;
                numberOfVisitedNodes = 0;
            }

            if (numberOfFoundNodes == n)
                break;

            expandWithDistance(currentNode);
            numberOfVisitedNodes++;
        }

        return routes;
    }

    private boolean hasCorrectInterestCode(Node currentNode, int interestCode) {
        return currentNode.interestCode == interestCode || ((interestCode == 2 || interestCode == 4) && (currentNode.interestCode == 6));
    }
}