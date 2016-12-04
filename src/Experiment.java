import java.util.*;

/**
 * Created by Daan on 04-Dec-16.
 */
public class Experiment {

    private Random random;

    public Experiment() {
        this.random = new Random();
        experimentOne();
    }

    private void experimentOne() {
        Graph graph = createCompleteGraph(3);
        List<Edge> positive = setRandomConstraints(graph);
        Map<Vertex, Double> special = setRandomSpecialVertices(graph);
        Exhaustive exh = new Exhaustive(graph, positive, special);
        System.out.println(exh.solve());
        printTruthAssignment(graph, exh.getSolutionTruth(), special);
    }

    private Map<Vertex, Double> setRandomSpecialVertices(Graph g) {
        Map<Vertex, Double> special = new HashMap<>();
        List<Vertex> specialVertices = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        int amountOfSpecial = g.getNrOfVertices() / 3;

        for (int i = 0; i < amountOfSpecial; i++) {
            Vertex randomVertex = g.getVertices().get(random.nextInt(g.getNrOfVertices()));
            if (!specialVertices.contains(randomVertex)) {
                specialVertices.add(randomVertex);
                weights.add((double) random.nextInt(5) + 1); // Weight of special vertices between 1 and 5 (integers)
            } else {
                i--; // If we've already assigned that vertex to special, add another one
            }
        }

        for (int i = 0; i < amountOfSpecial; i++) {
            Vertex key = specialVertices.get(i);
            double value = weights.get(i);
            special.put(key, value);
        }

        return special;
    }

    private Graph createCompleteGraph(int n) {
        List<Vertex> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            vertices.add(new Vertex(i));
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // The lowest number is always j
                Vertex v2 = vertices.get(i);
                Vertex v1 = vertices.get(j);
                Edge e = new Edge(v1, v2);
                edges.add(e);
            }
        }
        return new Graph(vertices, edges);
    }

    private List<Edge> setRandomConstraints(Graph g) {
        List<Edge> positive = new ArrayList<>();
        List<Edge> edges = g.getEdges();
        for (Edge e : edges) {
            if (random.nextInt(2) > 0) {
                positive.add(e);
            }
        }
        return positive;
    }

    private void printTruthAssignment(Graph g, List<Vertex> positive, Map<Vertex,Double> special) {
        for (Edge e : g.getEdges()) {
            Vertex v1 = e.getV1();
            Vertex v2 = e.getV2();
            String out = "";
            out += v1.toString();
            if (positive.contains(v1))
                out += " (1) ";
            else
                out += " (0) ";
            if (positive.contains(e))
                out += "----- ";
            else
                out += "- - - ";
            out += v2.toString();
            if (positive.contains(v2))
                out += " (1) ";
            else
                out += " (0) ";
            System.out.println(out);
        }
        System.out.print("Special vertices: ");
        StringJoiner joiner = new StringJoiner(",");
        for (Map.Entry<Vertex, Double> entry : special.entrySet()) {
            joiner.add(entry.getKey().toString() + ": " + entry.getValue().toString());
        }
        System.out.println(joiner.toString());
//        for (Map.Entry<Vertex, Double> entry : special.entrySet()) {
//            System.out.print(entry.getKey().toString() + ": " + entry.getValue() + ", ");
//        }
        System.out.println();
    }
}
