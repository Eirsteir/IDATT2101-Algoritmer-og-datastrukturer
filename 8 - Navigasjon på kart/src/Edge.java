public class Edge {

    Node to;
    Node from;
    int timeToDrive; // weight
    int speedLimit;

    public Edge(Node from, Node to, int timeToDrive) {
        this.from = from;
        this.to = to;
        this.timeToDrive = timeToDrive;
    }

    public Edge(Node from, Node to, int timeToDrive, int speedLimit) {
        this.from = from;
        this.to = to;
        this.timeToDrive = timeToDrive;
        this.speedLimit = speedLimit;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from +
                ", to=" + to +
                ", timeToDrive=" + timeToDrive +
                '}';
    }
}
