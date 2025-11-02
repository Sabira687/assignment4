package graph;

import java.util.*;
import java.io.*;

public class Graph {
    private final int n;
    private final List<List<int[]>> adj;

    public Graph(String path) throws Exception {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) lines.add(line.trim());
        }

        StringBuilder sb = new StringBuilder();
        for (String s : lines) sb.append(s);
        String json = sb.toString();

        int nIndex = json.indexOf("\"n\"");
        n = Integer.parseInt(json.substring(json.indexOf(":", nIndex) + 1, json.indexOf(",", nIndex)).trim());

        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        int edgesStart = json.indexOf("[", json.indexOf("\"edges\""));
        int edgesEnd = json.indexOf("]", edgesStart);
        String edgesPart = json.substring(edgesStart + 1, edgesEnd);

        String[] edges = edgesPart.split("\\},\\s*\\{");
        for (String e : edges) {
            String clean = e.replace("{", "").replace("}", "");
            int u = extractInt(clean, "\"u\"");
            int v = extractInt(clean, "\"v\"");
            int w = extractInt(clean, "\"w\"");
            addEdge(u, v, w);
        }
    }

    private int extractInt(String text, String key) {
        int idx = text.indexOf(key);
        if (idx == -1) return 0;
        int start = text.indexOf(":", idx) + 1;
        int end = text.indexOf(",", start);
        if (end == -1) end = text.length();
        return Integer.parseInt(text.substring(start, end).trim());
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new int[]{v, w});
    }

    public List<List<int[]>> getAdj() {
        return adj;
    }

    public int size() {
        return n;
    }
}