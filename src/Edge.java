/**
 * Created by Daan on 30-Nov-16.
 */
public class Edge {
    private Vertex v1, v2;
    private int weight;

    public Edge(Vertex v1, Vertex v2) {
        this(v1, v2, 1);
    }

    public Edge(Vertex v1, Vertex v2, int weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Vertex getV1 () {
        return v1;
    }

    public Vertex getV2() {
        return v2;
    }

    @Override
    public String toString() {
        String s = "e" + v1.getId() + "." + v2.getId();
        return s;
    }


}
