import graph.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Graph g = new Graph("data/tasks.json");
        SCC sccFinder = new SCC();
        List<List<Integer>> sccs = sccFinder.findSCCs(g);

        int[] comp = new int[g.size()];
        for (int i = 0; i < sccs.size(); i++)
            for (int v : sccs.get(i))
                comp[v] = i;

        List<List<int[]>> dag = new ArrayList<>();
        for (int i = 0; i < sccs.size(); i++)
            dag.add(new ArrayList<>());

        List<List<int[]>> adj = g.getAdj();
        for (int u = 0; u < g.size(); u++) {
            for (int[] e : adj.get(u)) {
                int v = e[0], w = e[1];
                if (comp[u] != comp[v])
                    dag.get(comp[u]).add(new int[]{comp[v], w});
            }
        }

        List<List<Integer>> simpleDag = new ArrayList<>();
        for (List<int[]> list : dag) {
            List<Integer> to = new ArrayList<>();
            for (int[] e : list) to.add(e[0]);
            simpleDag.add(to);
        }

        List<Integer> topo = TopoSort.sort(simpleDag);

        System.out.println("Topological order: " + topo);

        int sourceComp = comp[g.getAdj().get(4).get(0)[0]]; // just pick component of vertex 4 for demo
        DAGPaths paths = new DAGPaths(dag);
        double[] shortest = paths.shortestPaths(sourceComp, topo);
        double[] longest = paths.longestPaths(sourceComp, topo);

        System.out.println("\nShortest distances from component " + sourceComp + ":");
        System.out.println(Arrays.toString(shortest));

        System.out.println("\nLongest (critical) distances from component " + sourceComp + ":");
        System.out.println(Arrays.toString(longest));

        double max = Double.NEGATIVE_INFINITY;
        int end = -1;
        for (int i = 0; i < longest.length; i++)
            if (longest[i] > max) {
                max = longest[i];
                end = i;
            }
        System.out.println("\nCritical path length = " + max + " ends at component " + end);
    }
}