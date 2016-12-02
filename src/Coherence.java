import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Daan on 30-Nov-16.
 */
public class Coherence {

    private Graph graph;

    // Constraints of edges
    private ArrayList<Edge> positive;
    private ArrayList<Edge> negative;

    private ArrayList<Map<Vertex, Integer>> special; // special vertices (d coherence)

    private ArrayList<Vertex> temp; // temporary dividing of vertices into accepted (the vertices that are not in temp are in rejected at that specific moment)
    private ArrayList<Vertex> fin; // final/best truth/false assignment

    private ArrayList<Vertex> vertices; // all the vertices of the graph
    private ArrayList<Edge> edges; // all the edges of the graph

    private Random random = new Random();

    public Coherence() {
        createCompleteGraph(4);
        System.out.println("Positive:");
        for (Edge e : positive) {
            System.out.println(e);
        }
        System.out.println("Negative:");
        for (Edge e : negative) {
            System.out.println(e);
        }
        vertices = (ArrayList<Vertex>) graph.getVertices();
        edges = (ArrayList<Edge>) graph.getEdges();

        // for every possible length the list of accepted vertices can have
        for(int i = 0; i < 5; i++)
            coherence_exhaustive(temp,0,i,0);
    }

    /**
     * Creates a complete graph of size n.
     * @param n number of vertices
     */
    private void createCompleteGraph(int n) {
        List<Vertex> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        positive = new ArrayList<>();
        negative = new ArrayList<>();
        temp = new ArrayList<>();
        fin = new ArrayList<>();


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


    /**
     * Function for the exhaustive coherence algorithm
     * @param temp = the temporary list of vertices that are accepted/assigned true
     * @param index = which vertex is being considered
     * @param length = the length of the current accepted list/assigned vertices
     * @param best = the best coherence value
     * @return
     */
    private int coherence_exhaustive (ArrayList<Vertex> temp, int index, int length, int best) {
        if(temp.size() == length) {
            // compute coherence here with accepted vertices in temp, and the rejected vertices that are not in temp
            if(compute_d_coherence(temp) > best) {
                best = compute_d_coherence(temp);
                this.fin = temp;
                // Uncomment below to see the possible ways of assigning the elements to accepted
                /*
                for(Vertex v : temp)
                    System.out.print(v + " ");
                System.out.println(); */
            }
            return 1;
        }
        else if(index >= vertices.size()) {
            return 0;
        }
        else {
            Vertex v = vertices.get(index);
            temp.add(v);
            int with = coherence_exhaustive(temp, index+1, length, best);
            temp.remove(v);
            int without = coherence_exhaustive(temp, index+1, length, best);
            return with + without;
        }
    }

    /**
     * Function that computes the coherence of a specific graph with its truth/false assignment
     * @param temp the true/false assignment of the graph
     * @return the coherence value for a given graph and dividing of accepted and rejected vertices
     */
    private int compute_d_coherence(List <Vertex> temp) {
        int coherence = 0;
        for(Edge e : edges) {
            Vertex v1 = e.getV1();
            Vertex v2 = e.getV2();

            // if the edge has a positive constraint and the vertices (of that edge) have the same truth assignment
            if(positive.contains(e) && temp.contains(v1) && temp.contains(v2))
                coherence += e.getWeight();
            else if(positive.contains(e) && !temp.contains(v1) && !temp.contains(v2))
                coherence += e.getWeight();

            // if the edge has a negative constraint and the vertices (of that edge) do not have the same truth assignment
            else if(negative.contains(e) && temp.contains(v1) && !temp.contains(v2))
                coherence += e.getWeight();
            else if(negative.contains(e) && !temp.contains(v1) && temp.contains(v2))
                coherence += e.getWeight();
        }

        // implement code here for adding weights of special vertices

        return coherence;
    }
}
