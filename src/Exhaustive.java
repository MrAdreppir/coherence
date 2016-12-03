import java.util.List;

/**
 * Created by Daan on 03-Dec-16.
 */
public class Exhaustive implements Strategy {

    private Graph graph;
    private List<Vertex> vertices;
    private List<Edge> edges;


    public Exhaustive(Graph g) {
        this.graph = g;
    }

    @Override
    public double solve() {
        return 0;
    }
}
