package graph;

import java.util.*;

public class DAGPaths {
    private final List<List<int[]>> dag;
    private final int n;

    public DAGPaths(List<List<int[]>> dag) {
        this.dag = dag;
        this.n = dag.size();
    }

    public double[] shortestPaths(int src, List<Integer> topo) {
        double[] dist = new double[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[src] = 0;

        for (int u : topo) {
            if (dist[u] != Double.POSITIVE_INFINITY) {
                for (int[] e : dag.get(u)) {
                    int v = e[0];
                    double w = e[1];
                    if (dist[v] > dist[u] + w)
                        dist[v] = dist[u] + w;
                }
            }
        }
        return dist;
    }

    public double[] longestPaths(int src, List<Integer> topo) {
        double[] dist = new double[n];
        Arrays.fill(dist, Double.NEGATIVE_INFINITY);
        dist[src] = 0;

        for (int u : topo) {
            if (dist[u] != Double.NEGATIVE_INFINITY) {
                for (int[] e : dag.get(u)) {
                    int v = e[0];
                    double w = e[1];
                    if (dist[v] < dist[u] + w)
                        dist[v] = dist[u] + w;
                }
            }
        }
        return dist;
    }
}