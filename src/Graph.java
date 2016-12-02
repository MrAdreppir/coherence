import java.util.List;

/**
 * Created by Daan on 30-Nov-16.
 */
public class Graph {

    private int nrOfVertices;
    private int nrOfEdges;

    private List<Vertex> vertices;
    private List<Edge> edges;

    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
        this.nrOfVertices = vertices.size();
        this.nrOfEdges = edges.size();
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getNrOfVertices() {
        return nrOfVertices;
    }

    public int getNrOfEdges() {
        return nrOfEdges;
    }
}
