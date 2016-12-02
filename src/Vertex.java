/**
 * Created by Daan on 25-Nov-16.
 */
public class Vertex {

    private int id;
    private String name;

    public Vertex(int id) {
        this(id, "");
    }

    public Vertex(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Vertex " + id;
    }
}
