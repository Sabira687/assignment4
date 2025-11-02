# Smart Scheduling — Assignment 4

## Goal
Integrate two main graph algorithms into one practical case:  
**Strongly Connected Components (SCC)** and **Shortest/Longest Paths in a DAG.**  
The project simulates a simplified *Smart City / Smart Campus scheduling system*,  
where some dependencies form cycles (maintenance tasks), while others are acyclic (analytic tasks).

---

## Implemented Algorithms

### 1. Strongly Connected Components (Tarjan)
- Detects all cyclic dependencies in a directed graph.
- Compresses them into single components.
- Output: list of SCCs with their sizes.

### 2. Condensation DAG + Topological Sort
- Builds a DAG from SCC results.
- Performs topological ordering using **Kahn’s algorithm**.
- Produces both component order and derived original vertex order.

### 3. Shortest & Longest Paths in DAG
- Uses **edge weights** (as defined in JSON).
- Computes:
    - Single-source shortest paths (DP over topo order).
    - Longest (critical) path using max-DP.
- Outputs path lengths and critical path length.

---

## Example Output
```
Strongly Connected Components:
[3, 2, 1] size=3
[0] size=1
[7] size=1
[6] size=1
[5] size=1
[4] size=1

Condensation DAG:
0 -> [1]
1 -> []
2 -> []
3 -> [2]
4 -> [3]
5 -> [4]

Topological Order of Components:
[5, 4, 3, 2, 0, 1]

Order of Original Tasks:
[4] [5] [6] [7] [3, 2, 1] [0]

Shortest distances: [0.0, 7.0, 3.0, 1.0, Infinity, Infinity]
Longest distances: [0.0, 7.0, 3.0, 1.0, -Infinity, -Infinity]
Critical path length = 7.0
```

---

## Dataset Summary
| Category | Nodes | Description | Variants |
|-----------|--------|-------------|-----------|
| Small | 6–10 | Simple cyclic / acyclic graphs | 3 |
| Medium | 10–20 | Mixed SCC structures | 3 |
| Large | 20–50 | For performance & timing | 3 |

All generated under `/data/` using `GraphGenerator.java`.

---

## Metrics
Each algorithm can be instrumented with operation counters and time measurement using `System.nanoTime()`.  
Key counters:
- DFS calls (SCC)
- Queue operations (TopoSort)
- Relaxations (DAG paths)

---

## Testing
Testing was performed manually through `test/Test.java`.  
The file checks:
- SCC detection on `tasks.json`
- Topological ordering correctness
- DAG shortest/longest path accuracy

Example output confirms correctness for all implemented modules.

---

## Conclusions
- **SCC** is efficient for detecting cycles and grouping tasks.
- **Condensation + TopoSort** enable clear task scheduling order.
- **DAG-SP** (Shortest/Longest) provides optimal and critical task durations.
- The combination gives a scalable and modular base for Smart Scheduling systems.
