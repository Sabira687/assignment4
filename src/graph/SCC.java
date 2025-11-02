package graph;

import java.util.*;

public class SCC {
    private int time;
    private int[] disc, low;
    private boolean[] onStack;
    private Deque<Integer> stack;
    private List<List<Integer>> result;

    public List<List<Integer>> findSCCs(Graph g) {
        int n = g.size();
        disc = new int[n];
        low = new int[n];
        onStack = new boolean[n];
        stack = new ArrayDeque<>();
        result = new ArrayList<>();
        Arrays.fill(disc, -1);
        time = 0;

        for (int i = 0; i < n; i++)
            if (disc[i] == -1)
                dfs(i, g.getAdj());

        return result;
    }

    private void dfs(int u, List<List<int[]>> adj) {
        disc[u] = low[u] = ++time;
        stack.push(u);
        onStack[u] = true;

        for (int[] e : adj.get(u)) {
            int v = e[0];
            if (disc[v] == -1) {
                dfs(v, adj);
                low[u] = Math.min(low[u], low[v]);
            } else if (onStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            List<Integer> scc = new ArrayList<>();
            int v;
            do {
                v = stack.pop();
                onStack[v] = false;
                scc.add(v);
            } while (v != u);
            result.add(scc);
        }
    }
}