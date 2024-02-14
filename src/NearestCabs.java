/*
Employee's Cab Search Optimization:
Objective: Enhance the user experience for employees searching for cabs by suggesting nearby cabs
that are currently in use.
*/

import java.util.Arrays;

public class NearestCabs {

    public static double minkowskiDistance(int[] point1, int[] point2, int p) {
        double sum = 0;
        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(Math.abs(point1[i] - point2[i]), p);
        }
        return Math.pow(sum, 1.0 / p);
    }

    public static int[][] findNearestCabs(int[] customerLocation, int[][] cabsDataset, int k, int p) {
        double[] distances = new double[cabsDataset.length];

        for (int i = 0; i < cabsDataset.length; i++) {
            distances[i] = minkowskiDistance(customerLocation, cabsDataset[i], p);
        }

        // Create an array of indices to sort the distances array
        Integer[] indices = new Integer[cabsDataset.length];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }

        // Sort the indices based on distances
        Arrays.sort(indices, (a, b) -> Double.compare(distances[a], distances[b]));

        // Print positions of all cabs
        System.out.println("Positions of all cabs:");
        for (int i = 0; i < cabsDataset.length; i++) {
            System.out.println("Cab " + (i + 1) + ": [" + cabsDataset[i][0] + ", " + cabsDataset[i][1] + "]");
        }

        // Retrieve the k nearest cabs
        int[][] nearestCabs = new int[k][2];
        for (int i = 0; i < k; i++) {
            nearestCabs[i] = cabsDataset[indices[i]];
        }

        return nearestCabs;
    }

    public static void main(String[] args) {
        // Generate a random customer location
        int[] customerLocation = { (int) (Math.random() * 10), (int) (Math.random() * 10) };

        // Generate a random cabs dataset for demonstration purposes
        int[][] cabsDataset = new int[10][2];
        for (int i = 0; i < 10; i++) {
            cabsDataset[i][0] = (int) (Math.random() * 10);
            cabsDataset[i][1] = (int) (Math.random() * 10);
        }

        int k = 5;
        int p = 3; // Set the Minkowski distance order, you can change it as needed
        int[][] nearestCabs = findNearestCabs(customerLocation, cabsDataset, k, p);

        System.out.println("\nRandom Customer Location: [" + customerLocation[0] + ", " + customerLocation[1] + "]");
        System.out.println("Nearest Available Cabs: " + Arrays.deepToString(nearestCabs));
    }
}
