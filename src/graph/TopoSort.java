package graph;

import java.util.*;

public class TopoSort {
    public static List<Integer> sort(List<List<Integer>> dag) {
        int n = dag.size();
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++)
            for (int v : dag.get(u))
                indeg[v]++;

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++)
            if (indeg[i] == 0)
                q.add(i);

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : dag.get(u))
                if (--indeg[v] == 0)
                    q.add(v);
        }
        return order;
    }
}