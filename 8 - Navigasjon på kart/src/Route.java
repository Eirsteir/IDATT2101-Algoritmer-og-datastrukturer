import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Route {
    int numberOfVisitedNodes;
    Node startNode;
    Node goalNode;

    public Route(int numberOfVisitedNodes, Node startNode, Node goalNode) {

        this.numberOfVisitedNodes = numberOfVisitedNodes;
        this.startNode = startNode;
        this.goalNode = goalNode;
    }

    public void writeToFile(String algorithm) {
        String startName = startNode.name.replaceAll("[^a-zA-Z0-9]", "");
        String endName = goalNode.name.replaceAll("[^a-zA-Z0-9]", "");
        String fileName = startName + "-" + endName + " (" + algorithm + ").txt";

        Node node = goalNode;
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write("Route to " + goalNode.name + '\n');
            writer.write("Time to drive: " + getTimeToDrive() + '\n');
            writer.write("Number of visited nodes: " + this.numberOfVisitedNodes + '\n');

            while(node != null){
                writer.write(node.latitude + "," + node.longitude + "\n");
                node = node.predecessor;
            }
            writer.write('\n');
            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void writeNClosestToFile(List<Route> routes, String interestCode) {
        String startName = routes.get(0).startNode.name.replaceAll("[^a-zA-Z0-9]", "");
        String fileName = routes.size() + " " + interestCode + " nærmest " + startName + ".txt";

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write('\n');

            for (Route route : routes) {
                writer.write("Time to drive: " + route.getTimeToDrive() + '\n');
                writer.write("Number of visited nodes: " + route.numberOfVisitedNodes + '\n');
                Node node = route.goalNode;
                while(node != null){
                    writer.write(node.latitude + "," + node.longitude + "\n");
                    node = node.predecessor;
                }
            }
            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "Route from " + startNode.name +
                " to " + goalNode.name +
                "\n\tTime to drive: " + getTimeToDrive() +
                "\n\tPath: " + getPath();
    }

    public String getTimeToDrive() {
        int time = (int) goalNode.distance;
        int hours = time / 360000;
        int minutes = (time % 360000) / (6000);
        int seconds = (time - hours * 360000 - minutes * 6000) / 100;
        return hours + " hour" + getPluralEnding(hours)
                + minutes + " minute" + getPluralEnding(minutes)
                + seconds + " second" + getPluralEnding(seconds);
    }

    private String getPluralEnding(int size) {
        return size == 1 ? " " : "s ";
    }

    /**
     * Return the path by tracing back from goal node
     */
    private String getPath() {
        StringBuilder path = new StringBuilder();
        Node currentNode = goalNode;

        while (currentNode != null) {
            path.insert(0, String.format("%f, %f\n",
                                         currentNode.latitude,
                                         currentNode.longitude));

            currentNode = currentNode.predecessor;
        }

        return path.toString();
    }
}
