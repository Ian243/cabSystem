/*
Admins Cab Allocation Optimization:
Objective:
Develop an algorithm to optimize cab allocation for trips, reducing overall travel distance.
*/


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class City {
    int id;
    int[] coordinates;

    public City(int id, int[] coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }
}

class Edge {
    City start;
    City end;
    int weight;

    public Edge(City start, City end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}

class Cab {
    int id;
    List<Integer> stops;

    public Cab(int id) {
        this.id = id;
        this.stops = new ArrayList<>();
    }

    public void addStop(int cityId) {
        stops.add(cityId);
    }

    public List<Integer> getStops() {
        return stops;
    }
}

public class CabOptimization {

    private static List<City> cities;

    public static void main(String[] args) {
        cities = generateFixedCities();
        List<Edge> graph = generateConnectedGraph(cities);

        printGraphMatrix(graph);

        Cab cab1 = new Cab(1);
        Cab cab2 = new Cab(2);

        City destinationCab1 = cities.get(4);
        City destinationCab2 = cities.get(2);

        System.out.println("Initial Positions: Cab1 - " + cities.get(0).id + ", Cab2 - " + cities.get(6).id);

        List<Integer> optimalPathCab1 = findOptimalPath(cab1, destinationCab1, graph);
        List<Integer> optimalPathCab2 = findOptimalPath(cab2, destinationCab2, graph);

        System.out.println("\nCab1 Optimal Path: " + optimalPathCab1);
        System.out.println("Cab1 Total Distance: " + calculateTotalDistance(optimalPathCab1, graph));

        System.out.println("\nCab2 Optimal Path: " + optimalPathCab2);
        System.out.println("Cab2 Total Distance: " + calculateTotalDistance(optimalPathCab2, graph));

        int totalDistanceCovered = calculateTotalDistance(optimalPathCab1, graph)
                + calculateTotalDistance(optimalPathCab2, graph);
        System.out.println("\nTotal Distance Covered by Both Cabs: " + totalDistanceCovered);
    }

    private static List<Integer> findOptimalPath(Cab cab, City destination, List<Edge> graph) {
        List<Integer> optimalPath = new ArrayList<>();
        int currentCity = (cab.id == 1) ? cities.get(0).id : cities.get(6).id; // Starting city for the cabs
        optimalPath.add(currentCity);

        while (cab.getStops().size() < cities.size() - 1) {
            int nearestNeighbor = findNearestNeighbor(currentCity, cab.getStops(), graph);
            cab.addStop(nearestNeighbor);

            if (nearestNeighbor != destination.id) {
                System.out.println("Cab " + cab.id + ": Reached Stop " + nearestNeighbor);
                System.out.println("Cab " + cab.id + ": Travel to next Stop");
            } else {
                System.out.println("Cab " + cab.id + ": Reached Destination " + destination.id);
            }

            currentCity = nearestNeighbor;
        }

        return cab.getStops();
    }

    private static int findNearestNeighbor(int currentCity, List<Integer> excludedStops, List<Edge> graph) {
        int nearestNeighbor = -1;
        int minDistance = Integer.MAX_VALUE;

        for (City city : cities) {
            if (!excludedStops.contains(city.id)) {
                int distance = getWeight(currentCity, city.id, graph);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestNeighbor = city.id;
                }
            }
        }

        return nearestNeighbor;
    }

    private static void printGraphMatrix(List<Edge> graph) {
        int[][] matrix = new int[cities.size()][cities.size()];

        for (Edge edge : graph) {
            matrix[edge.start.id - 1][edge.end.id - 1] = edge.weight;
            matrix[edge.end.id - 1][edge.start.id - 1] = edge.weight;
        }

        System.out.println("Graph Matrix:");
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    private static int getWeight(int start, int end, List<Edge> graph) {
        for (Edge edge : graph) {
            if ((edge.start.id == start && edge.end.id == end) || (edge.start.id == end && edge.end.id == start)) {
                return edge.weight;
            }
        }
        return Integer.MAX_VALUE;
    }

    private static int calculateTotalDistance(List<Integer> stops, List<Edge> graph) {
        int totalDistance = 0;

        for (int i = 0; i < stops.size() - 1; i++) {
            int startCityId = stops.get(i);
            int endCityId = stops.get(i + 1);
            totalDistance += getWeight(startCityId, endCityId, graph);
        }

        return totalDistance;
    }

    private static List<Edge> generateConnectedGraph(List<City> cities) {
        List<Edge> graph = new ArrayList<>();

        // Generate a fully connected graph (for demonstration purposes)
        for (int i = 0; i < cities.size(); i++) {
            for (int j = i + 1; j < cities.size(); j++) {
                int weight = new Random().nextInt(10) + 1; // Random weight between 1 and 10
                graph.add(new Edge(cities.get(i), cities.get(j), weight));
            }
        }

        // Remove some edges to make it not fully connected
        graph.remove(2); // Example: Remove edge at index 2

        return graph;
    }

    private static List<City> generateFixedCities() {
        List<City> cities = new ArrayList<>();
        cities.add(new City(1, new int[]{0, 0}));
        cities.add(new City(2, new int[]{10, 5}));
        cities.add(new City(3, new int[]{15, 12}));
        cities.add(new City(4, new int[]{5, 8}));
        cities.add(new City(5, new int[]{7, 15}));
        cities.add(new City(6, new int[]{12, 10}));
        cities.add(new City(7, new int[]{18, 5}));
        return cities;
    }
}
