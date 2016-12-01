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
    
    private ArrayList<Vertex> temp = new ArrayList<>(); // temporary dividing 
    private ArrayList<Vertex> fin = new ArrayList<>(); // final/best dividing 
    
    private ArrayList<Vertex> vertices; // all the vertices of the graph
    
    private Random random = new Random();

    public Coherence() {
        createCompleteGraph(4);
        //System.out.println("Positive:");
        for (Edge e : positive) {
            //System.out.println(e);
        }
        //System.out.println("Negative:");
        for (Edge e : negative) {
            //System.out.println(e);
        }
        vertices = (ArrayList<Vertex>) graph.getVertices();
        
        for(int i = 0; i < 5; i++)
            coherence_exhaustive(temp,0,i,0,fin);
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
    
    
    /**
     * Function for the exhaustive coherence algorithm
     * @param temp = the temporary list of vertices that are accepted
     * @param index = which vertex is being considered
     * @param length = the length of the length of the current accepted list
     * @param best = the best coherence value
     * @param fin = final dividing of accepted and rejected vertices
     * @return 
     */
    private int coherence_exhaustive (List<Vertex> temp, int index, int length, int best, List<Vertex> fin) {
        if(temp.size() == length) {
            // compute coherence here with accepted vertices in temp, and the rejected vertices that are not in temp 
            if(compute_coherence(temp) > best) {
                best = compute_coherence(temp);
                fin = temp;
            }
            return 1;
        }
        else if(index >= vertices.size()) {
            return 0;
        }
        else {
            Vertex v = vertices.get(index);
            temp.add(v);
            int with = coherence_exhaustive(temp, index+1, length, best, fin);
            temp.remove(v);
            int without = coherence_exhaustive(temp, index+1, length, best, fin);
            return with + without;
        }
    }
    /**
     * Function that computes the coherence of a specific graph
     * @return the coherence value for a given graph and dividing of accepted and rejected vertices
     */
    private int compute_coherence(List <Vertex> temp) {
        return 1;
    }
}
