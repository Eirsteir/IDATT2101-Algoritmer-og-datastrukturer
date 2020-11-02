import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.AbstractPainter;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.*;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

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

        showRoute(route);

//        List<Route> routes = navigation.findNShortestPaths(startNode, 10, 2);
//        showRoute(routes);
    }

    public static void showRoute(Route route) {
        JXMapViewer mapViewer = getJxMapViewer();

        List<GeoPosition> track = getTrack(route);

        // Create a track from the geo-positions
        RoutePainter routePainter = new RoutePainter(track);

        // Set the focus
        mapViewer.zoomToBestFit(new HashSet<>(track), 0.7);

        mapViewer.setOverlayPainter(routePainter);
    }

    private static JXMapViewer getJxMapViewer() {
        JXMapViewer mapViewer = new JXMapViewer();

        // Display the viewer in a JFrame
        JFrame frame = new JFrame("Øving 8");
        frame.getContentPane()
                .add(mapViewer);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);
        return mapViewer;
    }

    public static void showRoute(List<Route> routes) {
        JXMapViewer mapViewer = getJxMapViewer();

        List<List<GeoPosition>> tracks = new ArrayList<>();
        List<GeoPosition> ends = new ArrayList<>();
        for (Route route : routes) {
            tracks.add(getTrack(route));
            Node node = route.goalNode;
            ends.add(new GeoPosition(node.latitude, node.longitude));
        }

        List<RoutePainter> routePainters = new ArrayList<>();
        for (List<GeoPosition> track : tracks)
            routePainters.add(new RoutePainter(track));


        // Set the focus
        List<GeoPosition> geoPositions = getLongestTrack(routes, tracks);
        mapViewer.zoomToBestFit(new HashSet<>(geoPositions), 0.7);

        // Create waypoints from the geo-positions
        Set<Waypoint> waypoints = ends.stream()
                .map(DefaultWaypoint::new)
                .collect(Collectors.toSet());

        // Create a waypoint painter that takes all the waypoints
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
        waypointPainter.setWaypoints(waypoints);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(routePainters);
        painter.addPainter(waypointPainter);
        mapViewer.setOverlayPainter(painter);
    }

    private static List<GeoPosition> getLongestTrack(List<Route> routes, List<List<GeoPosition>> tracks) {
        int maxNumNodesVisited = 0;
        Route longestRoute = null;
        for (Route route : routes) {
            if (route.numberOfVisitedNodes > maxNumNodesVisited) {
                maxNumNodesVisited = route.numberOfVisitedNodes;
                longestRoute = route;
            }
        }
        int index = routes.indexOf(longestRoute);
        return tracks.get(index);
    }

    private static List<GeoPosition> getTrack(Route route) {
        List<GeoPosition> track = new ArrayList<>();
        Node currentNode = route.goalNode;

        while (currentNode != null) {
            GeoPosition geoPosition = new GeoPosition(currentNode.latitude, currentNode.longitude);
            track.add(geoPosition);
            currentNode = currentNode.predecessor;
        }
        return track;
    }
}
