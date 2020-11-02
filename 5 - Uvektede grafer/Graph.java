import java.util.*;
import java.util.LinkedList;

/**
 * This graph uses Kosaraju's algorithm to find strongly connected components
 */
class Graph {
    private int V;
    private LinkedList<Integer>[] adjacencyList;

    // Create a graph
    Graph(int size) {
        V = size;
        adjacencyList = new LinkedList[size];
        for (int i = 0; i < size; ++i)
            adjacencyList[i] = new LinkedList<>();
    }

    // Add edge
    void addEdge(int from, int to) {
        adjacencyList[from].add(to);
    }

    // DFS search
    void DFSUtil(int s, boolean[] visitedVertices) {
        visitedVertices[s] = true;
        System.out.print(s + " ");

        for (Integer i : adjacencyList[s]) {
            if (!visitedVertices[i])
                DFSUtil(i, visitedVertices);
        }
    }

    // Reverse the graph - G^T
    Graph Reverse() {
        Graph g = new Graph(V);

        for (int s = 0; s < V; s++)
            for (Integer integer : adjacencyList[s])
                g.adjacencyList[integer].add(s);

        return g;
    }

    // Sort the graph in descending fill-time
    void fillOrder(int s, boolean[] visitedNodes, Stack<Integer> stack) {
        visitedNodes[s] = true;

        for (int n : adjacencyList[s])
            if (!visitedNodes[n])
                fillOrder(n, visitedNodes, stack);

        stack.push(s);
    }

    // Print the strongly connected components
    void printSCC() {
        Stack<Integer> stack = new Stack<>();

        boolean[] visitedVertices = new boolean[V];
        for (int i = 0; i < V; i++)
            visitedVertices[i] = false;

        for (int i = 0; i < V; i++)
            if (!visitedVertices[i])
                fillOrder(i, visitedVertices, stack);

        Graph gr = Reverse();

        for (int i = 0; i < V; i++)
            visitedVertices[i] = false;

        int component = 1;
        System.out.println("Grafen har følgende sterkt sammenhengende komponenter:");

        while (!stack.empty()) {
            int s = stack.pop();

            if (!visitedVertices[s]) {
                System.out.print(component + "\t\t");
                gr.DFSUtil(s, visitedVertices);
                System.out.println();
                component++;
            }

        }
    }

}



