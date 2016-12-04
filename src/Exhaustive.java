import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Daan on 03-Dec-16.
 */
public class Exhaustive implements Strategy {

    private Graph graph;
    private List<Vertex> vertices;
    private List<Edge> edges;

    private List<Edge> positive;
    private Map<Vertex, Double> special;

    private List<Vertex> solutionTruth;
    private double coherence;
    private double solutionTime;

    /**
     * Exhaustive coherence search constructor.
     * @param g the graph
     * @param positive the edges with positive constraints
     * @param special the vertices that are special
     */
    public Exhaustive(Graph g, List<Edge> positive, Map<Vertex, Double> special) {
        this.graph = g;
        this.vertices = g.getVertices();
        this.edges = g.getEdges();

        this.positive = positive;
        this.special = special;
    }

    private double calcCoherence(ArrayList<Vertex> subgroup) {
        double coherence = 0;
        for (Edge e : edges) {
            boolean v1True = subgroup.contains(e.getV1());
            boolean v2True = subgroup.contains(e.getV2());
            boolean equalTruth = v1True == v2True;
            // Positive edge: both vertices have the same truth assignment
            // Negative edge: vertices have unequal truth assignments
            if (positive.contains(e)) {
                if (equalTruth) {
                    coherence += e.getWeight();
                }
            }
            else if (!equalTruth) {
                coherence += e.getWeight();
            }
        }

        // Better complexity
        for (Vertex v : subgroup) {
            // HashMap.containsKey = O(1)
            if (special.containsKey(v)) {
                double weight = special.get(v);
                coherence += weight;
            }
        }
//        // Loop over special vertices
//        for (Map.Entry<Vertex, Integer> entry : special.entrySet()) {
//            Vertex v = entry.getKey();
//            int weight = entry.getValue();
//
//            if (subgroup.contains(v)) {
//                coherence += weight;
//            }
//        }

        return coherence;
    }

    /**
     * Creates all possible subgroups of vertices.
     * Every subgroup represents a truth assignment, i.e. the vertices in the subgroup are true
     * @param vertices the list of all the vertices
     * @return
     */
    private ArrayList<ArrayList<Vertex>> getSubgroups(List<Vertex> vertices) {
        SubsetIterator<Vertex> subsetIterator = new SubsetIterator<>(vertices);
        ArrayList<ArrayList<Vertex>> vertexSubgroups = new ArrayList<>();

        while (subsetIterator.hasNext()) {
            vertexSubgroups.add(subsetIterator.next());
        }
        return vertexSubgroups;
    }

    @Override
    public double solve() {
        long startTime = System.nanoTime();
        ArrayList<ArrayList<Vertex>> subgroups = getSubgroups(vertices);

        for (ArrayList<Vertex> group : subgroups) {
            double groupCoherence = calcCoherence(group);
            if (coherence < groupCoherence) {
                solutionTruth = group; // Save our winning configuration
                coherence = groupCoherence;
            }
        }
        long endTime = System.nanoTime();
        solutionTime = endTime - startTime;
        return coherence;
    }

    public List<Vertex> getSolutionTruth() {
        return solutionTruth;
    }

    public double getCoherence() {
        return coherence;
    }

    public double getSolutionTime() {
        return solutionTime;
    }
}
