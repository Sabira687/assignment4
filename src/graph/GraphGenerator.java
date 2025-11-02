package graph;

import java.io.*;
import java.util.*;

public class GraphGenerator {
    private static final Random rnd = new Random();

    public static void generate(String path, int n, double density, boolean cyclic) throws Exception {
        List<String> edges = new ArrayList<>();
        int totalPossible = n * (n - 1);
        int targetEdges = (int) (totalPossible * density);

        Set<String> added = new HashSet<>();
        for (int i = 0; i < targetEdges; i++) {
            int u = rnd.nextInt(n);
            int v = rnd.nextInt(n);
            if (u == v) continue;
            String key = u + "-" + v;
            if (added.contains(key)) continue;
            added.add(key);
            int w = rnd.nextInt(9) + 1;
            edges.add("{\"u\": " + u + ", \"v\": " + v + ", \"w\": " + w + "}");
        }

        if (!cyclic) {
            edges.removeIf(e -> {
                int u = Integer.parseInt(e.split(":")[1].split(",")[0].trim());
                int v = Integer.parseInt(e.split(":")[2].split(",")[0].trim());
                return v < u;
            });
        }

        String json = "{\n" +
                "  \"directed\": true,\n" +
                "  \"n\": " + n + ",\n" +
                "  \"edges\": [\n    " + String.join(",\n    ", edges) + "\n  ],\n" +
                "  \"source\": 0,\n" +
                "  \"weight_model\": \"edge\"\n" +
                "}";
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(json);
        }
    }

    public static void main(String[] args) throws Exception {
        new File("data").mkdirs();

        generate("data/small1.json", 6, 0.2, true);
        generate("data/small2.json", 8, 0.3, false);
        generate("data/small3.json", 10, 0.4, true);

        generate("data/medium1.json", 12, 0.3, true);
        generate("data/medium2.json", 15, 0.4, false);
        generate("data/medium3.json", 18, 0.5, true);

        generate("data/large1.json", 25, 0.2, true);
        generate("data/large2.json", 35, 0.3, false);
        generate("data/large3.json", 45, 0.4, true);

        System.out.println("Generated 9 datasets in /data/");
    }
}