import java.io.*;
import java.util.*;

class DPQ {
    private static final int INFINITY = 1000000000;
    private int[] dist;
    private Set<Integer> settledNodes;
    private PriorityQueue<Node> pq;
    private int N;
    List<List<Node>> nodes;

    public DPQ(int N) {
        this.N = N;
        dist = new int[N];
        settledNodes = new HashSet<>();
        pq = new PriorityQueue<>(N, new Node());
    }

    public void dijkstra(List<List<Node>> nodes, int source) {
        this.nodes = nodes;
        initDist();

        // Add source node to the priority queue with a cost of 0
        pq.add(new Node(source, 0));

        // Distance to the source is 0
        dist[source] = 0;
        while (settledNodes.size() <= N) {
            if (pq.isEmpty())
                break;

            // Select the node with the lowest cost
            int u = pq.remove().node;

            // Add the node whose distance has been found
            settledNodes.add(u);

            processNeighbours(u);
        }
    }

    private void initDist() {
        // Initialize all distances to infinity
        for (int i = 0; i < N; i++)
            dist[i] = INFINITY;
    }

    private void processNeighbours(int u) {
        // All the neighbors of node v
        for (int i = 0; i < nodes.get(u).size(); i++) {
            Node v = nodes.get(u).get(i);

            // If the current neighbour node hasn't already been processed, then process it
            if (isNotSettled(v))
                processNode(dist[u], v);
        }
    }

    private boolean isNotSettled(Node v) {
        return !settledNodes.contains(v.node);
    }

    private void processNode(int i1, Node v) {
        int edgeDistance = v.cost; // distance from current node to current neighbour
        int newDistance = i1 + edgeDistance; // add to current distance

        if (newDistance < dist[v.node])
            dist[v.node] = newDistance; // new distance is less, set new distance

        // Add the current node to the priority queue with the appropriate distance
        pq.add(new Node(v.node, dist[v.node]));
    }

    private void printShortestPathFrom(int source) {
        System.out.println("Korteste vei fra node: "+ source);

        for (int i = 0; i < this.dist.length; i++) {
            int dist = this.dist[i];
            String distance = String.valueOf(dist);

            if (dist == INFINITY)
                distance = "ikke nåelig";

            System.out.println(source + " til " + i + " er "
                                       + distance);
        }
    }

    public static void main(String[] args) throws IOException {

        File file = new File("vg2");
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringTokenizer stringTokenizer = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(stringTokenizer.nextToken());
        int source = 7;

        // Adjacency list representation of the
        // connected nodes
        List<List<Node>> nodes = new ArrayList<>();

        // Initialize list
        for (int i = 0; i < N; i++)
            nodes.add( new ArrayList<>());

        int K = Integer.parseInt(stringTokenizer.nextToken());
        for (int i = 0; i < K; ++i) {
            stringTokenizer = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(stringTokenizer.nextToken());
            int to = Integer.parseInt(stringTokenizer.nextToken());
            int weight = Integer.parseInt(stringTokenizer.nextToken());

            nodes.get(from).add(new Node(to, weight));
        }

        // Calculate the shortest path to all nodes from source
        DPQ dpq = new DPQ(N);
        dpq.dijkstra(nodes, source);

        dpq.printShortestPathFrom(source);
    }
}

class Node implements Comparator<Node> {
    int node;
    int cost;

    public Node() {}

    public Node(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }

    @Override
    public int compare(Node node1, Node node2) {
        return Integer.compare(node1.cost, node2.cost);
    }
}
