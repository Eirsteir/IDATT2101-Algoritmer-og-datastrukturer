import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class MapFileParser {
    private final Navigation Navigation;
    final String nodesFilename;
    final String edgesFilename;
    final String pointsOfInterestFilename;
    Map<String, Integer> pointsOfInterest;

    public MapFileParser(Navigation Navigation, String nodesFilename, String edgesFilename, String pointsOfInterestFilename) throws IOException {
        this.Navigation = Navigation;
        this.nodesFilename = nodesFilename;
        this.edgesFilename = edgesFilename;
        this.pointsOfInterest = new HashMap<>();
        this.pointsOfInterestFilename = pointsOfInterestFilename;
        readFromAllFiles();
    }

    private void readFromAllFiles() throws IOException {
        readNodesFromFile(); //
        readEdgesFromFile(); //
        readPointsOfInterestsFromFile();
    }

    void readNodesFromFile() throws IOException {
        System.out.println("Reading nodes from file");

        File file = new File(nodesFilename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringTokenizer stringTokenizer = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(stringTokenizer.nextToken());
        Navigation.setNodes(new ArrayList<>());

        for (int i = 0; i < N; i++)
            Navigation.getNodes().add(null);

        int amountOfNodeFields = 3;

        for (int i = 0; i < N; ++i) {
            String line = br.readLine();
            hsplit(line, amountOfNodeFields);

            int number = Integer.parseInt(fields[0]);
            double latitude = Double.parseDouble(fields[1]);
            double longitude = Double.parseDouble(fields[2]);

            Node node = new Node(number, latitude, longitude);
            Navigation.getNodes()
                    .set(number, node);
        }

        br.close();
    }

    void readEdgesFromFile() throws IOException {
        System.out.println("Reading edges from file");

        File file = new File(edgesFilename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringTokenizer stringTokenizer = new StringTokenizer(br.readLine());

        int K = Integer.parseInt(stringTokenizer.nextToken());
        int amountOfFields = 5;

        for (int i = 0; i < K; ++i) {
            String line = br.readLine();
            hsplit(line, amountOfFields);

            int from = Integer.parseInt(fields[0]);
            int to = Integer.parseInt(fields[1]);
            int timeToDrive = Integer.parseInt(fields[2]);

            Node fromNode = Navigation.getNodes()
                    .get(from);
            Node toNode = Navigation.getNodes()
                    .get(to);

            // Ignores speed limit and length
            Edge edge = new Edge(fromNode, toNode, timeToDrive);
            Navigation.getNodes()
                    .get(from)
                    .edges.add(edge);
        }

        br.close();
    }

    void readPointsOfInterestsFromFile() throws IOException {
        System.out.println("Reading points of interests from file");

        File file = new File(pointsOfInterestFilename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());

        while (n-- > 0) {
            st = new StringTokenizer(br.readLine());
            int nodeNumber = Integer.parseInt(st.nextToken());
            int interestCode = Integer.parseInt(st.nextToken());
            String placeName = st.nextToken();

            while (st.hasMoreTokens())
                placeName += " " + st.nextToken();

            placeName = placeName.replaceAll("\"", "");

            Navigation.getNode(nodeNumber).interestCode = interestCode;
            Navigation.getNode(nodeNumber).name = placeName;
            pointsOfInterest.put(placeName, nodeNumber);
        }

        br.close();
    }

    static String[] fields = new String[10]; //Max 10 felt i en linje

    static void hsplit(String linje, int antall) {
        int j = 0;
        int lengde = linje.length();
        for (int i = 0; i < antall; ++i) {
            //Hopp over innledende blanke, finn starten på ordet
            while (linje.charAt(j) <= ' ') ++j;
            int ordstart = j;
            //Finn slutten på ordet, hopp over ikke-blanke
            while (j < lengde && linje.charAt(j) > ' ') ++j;
            fields[i] = linje.substring(ordstart, j);
        }
    }
}