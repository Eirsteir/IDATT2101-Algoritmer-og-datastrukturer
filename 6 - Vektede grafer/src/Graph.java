import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph{
      private int[] dist;
      private boolean[] settled;
      private PriorityQueue<Node> pq;
      private int V; // Number of vertices
      private List<List<Node>> adj;

      public Graph(int V) {
            this.V = V;
            dist = new int[V];
            settled = new boolean[V];
            pq = new PriorityQueue<>(V, new Node());
            adj = new ArrayList<>(V);
      }

      public void dijkstra(List<List<Node>> adj, int src){
            this.adj = adj;

            //Set distance to source node zero and all other nodes to infinity
            for (int i = 0; i < V; i++) {
                  dist[i] = Integer.MAX_VALUE;
                  settled[i] = false;
            }

            dist[src] = 0;
            int newNode = src;
            while (true){
                  if (!settled[newNode]) {
                        //The selected new node's shortest path is settled
                        settled[newNode] = true;
                        //Finds all edges with origin from this node
                        for (int i = 0; i < adj.get(newNode).size(); i++) {
                              Node node = adj.get(newNode).get(i);
                              //If the target node isn't settled
                              if (!settled[node.node]) {
                                    //If the new path to the node has a lower cost
                                    if (node.cost + dist[newNode] < dist[node.node]) {
                                          //Updates priority
                                          dist[node.node] = node.cost + dist[newNode];
                                          //Adds the node to the priority queue
                                          pq.add(new Node(node.node, dist[node.node]));
                                    }
                              }
                        }
                  }
                  if (pq.isEmpty())
                        break;
                  //Selects node with lowest cost aka. highest priority
                  newNode = pq.remove().node;
            }
      }

      static class Node implements Comparator<Node>{
            private int node;
            private int cost;

            public Node(){
            }

            public Node(int node, int cost){
                  this.node = node;
                  this.cost = cost;
            }

            @Override
            public int compare(Node p1, Node p2){
                  return Integer.compare(p1.cost, p2.cost);
            }
      }

      public static void main(String[] args) throws IOException{


            //Read number of nodes and edges
            File file = new File("src/vg2.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String[] firstLine = br.readLine().trim().split("\\s+");
            int V = Integer.parseInt(firstLine[0]);
            int edges = Integer.parseInt(firstLine[1]);


            //Choose the source node
            int source = 7;

            Graph dpq = new Graph(V);
            List<List<Node>> adj = new ArrayList<>();

            for (int i = 0; i < V; i++){
                  List<Node> item = new ArrayList<>();
                  adj.add(item);
            }


            //Read each edge from file
            String[] line;
            for (int i = 0; i < edges; i++){
                  line = br.readLine().trim().split("\\s+");
                  adj.get(Integer.parseInt(line[0])).add(new Node(Integer.parseInt(line[1]), Integer.parseInt(line[2])));
            }


            //Add all edges and the source node
            dpq.dijkstra(adj, source);


            System.out.println("Djikstra's algorithm on " + file.getName() + "\nThe shorted path from node :");
            for (int i = 0; i < dpq.dist.length; i++){
                  System.out.print(source + " to " + i);
                  if (dpq.dist[i] < Integer.MAX_VALUE) //Integer cannot be infinite in java. I therefore choose to use Integer-max instead
                        System.out.print(" is " + dpq.dist[i]);
                  else System.out.print(" cannot be reached"); //If distance is 'infinite'
                  System.out.println();
            }
      }
}
