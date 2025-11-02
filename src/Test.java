import graph.*;
import java.util.*;

public class Test {
    public static void main(String[] args) throws Exception {
        System.out.println("==== SCC TEST ====");
        Graph g = new Graph("data/tasks.json");
        SCC scc = new SCC();
        List<List<Integer>> sccs = scc.findSCCs(g);
        System.out.println("SCC count = " + sccs.size());
        for (List<Integer> comp : sccs)
            System.out.println(comp);

        System.out.println("\n==== TOPO SORT TEST ====");
        List<List<Integer>> dag = new ArrayList<>();
        dag.add(Arrays.asList(1));
        dag.add(Arrays.asList(2));
        dag.add(new ArrayList<>());
        System.out.println("TopoSort: " + TopoSort.sort(dag));

        System.out.println("\n==== DAG PATHS TEST ====");
        List<List<int[]>> dagW = new ArrayList<>();
        dagW.add(Arrays.asList(new int[]{1, 2}, new int[]{2, 4}));
        dagW.add(Arrays.asList(new int[]{2, 1}));
        dagW.add(new ArrayList<>());

        List<List<Integer>> dagSimple = new ArrayList<>();
        dagSimple.add(Arrays.asList(1, 2));
        dagSimple.add(Arrays.asList(2));
        dagSimple.add(new ArrayList<>());
        List<Integer> topo = TopoSort.sort(dagSimple);

        DAGPaths dp = new DAGPaths(dagW);
        double[] shortest = dp.shortestPaths(0, topo);
        double[] longest = dp.longestPaths(0, topo);

        System.out.println("Shortest distances: " + Arrays.toString(shortest));
        System.out.println("Longest distances: " + Arrays.toString(longest));
    }
}