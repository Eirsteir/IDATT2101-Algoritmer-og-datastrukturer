import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Node {

    private static final int INFINITY = 1000000000;
    public static final double DEFAULT_ESTIMATED_COST_TO_GOAL_NODE = -1.0;

    final int number;
    String name;
    double latitude; // bredde
    double longitude; // lengde

    double cosineLatitude;
    int interestCode;

    double distance; // g
    double estimatedDistanceToGoalNode; // h

    Node predecessor;
    List<Edge> edges;

    public Node(int number, double latitude, double longitude) {
        this.number = number;
        this.latitude = latitude;
        this.longitude = longitude;

        this.cosineLatitude = Math.cos(this.latitude * Math.PI / 180);
        this.distance = INFINITY;
        this.estimatedDistanceToGoalNode = DEFAULT_ESTIMATED_COST_TO_GOAL_NODE;

        this.predecessor = null;
        this.edges = new ArrayList<>();
    }

    // weight A*
    Double fullDistance() {
        return this.distance + this.estimatedDistanceToGoalNode;
    }

    public boolean costToGoalNodeIsNotEstimated() {
        return estimatedDistanceToGoalNode == DEFAULT_ESTIMATED_COST_TO_GOAL_NODE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return number == node.number;
    }

    @Override
    public String toString() {
        return "Node{" +
                "number=" + number +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

class NodeFullDistanceComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        return o1.fullDistance().compareTo(o2.fullDistance());
    }

}

class NodeDistanceComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        return Double.compare(o1.distance, o2.distance);
    }

}
