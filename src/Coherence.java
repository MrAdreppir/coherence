import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Daan on 30-Nov-16.
 */
public class Coherence {

    private Graph graph;

    // Constraints of edges
    private ArrayList<Edge> positive;
    private ArrayList<Edge> negative;

    private Random random = new Random();

    public Coherence() {
        createCompleteGraph(3);
        System.out.println("Positive:");
        for (Edge e : positive) {
            System.out.println(e);
        }
        System.out.println("Negative:");
        for (Edge e : negative) {
            System.out.println(e);
        }
    }

    /**
     * Creates a complete graph of size n.
     * @param n number of vertices
     */
    private void createCompleteGraph(int n) {
        List<Vertex> vertices = new ArrayList<Vertex>();
        List<Edge> edges = new ArrayList<Edge>();
        positive = new ArrayList<Edge>();
        negative = new ArrayList<Edge>();

        for(int i = 0; i < n; i++) {
            vertices.add(new Vertex(i));
        }
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // The lowest number is always j
                Vertex v2 = vertices.get(i);
                Vertex v1 = vertices.get(j);
                Edge e = new Edge(v1, v2);
                edges.add(e);
                if (random.nextInt(2) > 0)
                    positive.add(e);
                else
                    negative.add(e);
            }
        }
        graph = new Graph(vertices, edges);
    }

    private int coherence_exhaustive() {
        return 0;
    }
}
