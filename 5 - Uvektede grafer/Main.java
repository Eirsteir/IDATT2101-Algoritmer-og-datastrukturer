import java.io.*;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("L7g2.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        StringTokenizer stringTokenizer = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(stringTokenizer.nextToken());

        Graph graph = new Graph(N);

        int K = Integer.parseInt(stringTokenizer.nextToken());
        for (int i = 0; i < K; ++i) {
            stringTokenizer = new StringTokenizer(br.readLine());
            int fra = Integer.parseInt(stringTokenizer.nextToken());
            int til = Integer.parseInt(stringTokenizer.nextToken());
            graph.addEdge(fra, til);
        }

        graph.printSCC();
        
    }
}
