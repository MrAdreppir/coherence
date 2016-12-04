import java.util.*;

/**
 * Created by Daan on 30-Nov-16.
 */
public class Coherence {

    private Graph graph;

    // Constraints of edges, if not in positive then negative constraint
    private ArrayList<Edge> positive;

    // Special vertices, mapping: vertex -> weight
    private Map<Vertex, Integer> special;

    //private ArrayList<Vertex> temp; // temporary dividing of vertices into accepted (the vertices that are not in temp are in rejected at that specific moment)
    //private ArrayList<Vertex> fin; // final/best truth/false assignment

    private ArrayList<Vertex> vertices; // all the vertices of the graph
    private ArrayList<Edge> edges; // all the edges of the graph
    private ArrayList<ArrayList<Vertex>> subgroups;

    private Random random = new Random();

    private int coherence;

    public Coherence() {
        createCompleteGraph(3);

        vertices = (ArrayList<Vertex>) graph.getVertices();
        edges = (ArrayList<Edge>) graph.getEdges();
        subgroups = getSubgroups(vertices);
        setSpecialVertices(new ArrayList<Vertex>(), new ArrayList<Integer>());
        coherence = exhaust(subgroups);
        System.out.println("Coherence: " + coherence);
        // for every possible length the list of accepted vertices can have
//        for (int i = 0; i < 5; i++)
//            coherence_exhaustive(temp,0,i,0);
    }

    private void printTruthAssignment(ArrayList<Vertex> trueVertices) {
        for (Edge e : edges) {
            Vertex v1 = e.getV1();
            Vertex v2 = e.getV2();
            String out = "";
            out += v1.toString();
            if (trueVertices.contains(v1))
                out += " (1) ";
            else
                out += " (0) ";
            if (positive.contains(e))
                out += "----- ";
            else
                out += "- - - ";
            out += v2.toString();
            if (trueVertices.contains(v2))
                out += " (1) ";
            else
                out += " (0) ";
            System.out.println(out);
        }
        System.out.print("Special vertices: ");
        for (Map.Entry<Vertex, Integer> entry : special.entrySet()) {
            System.out.print(entry.getKey().toString() + ": " + entry.getValue() + ", ");
        }
        System.out.println();
    }

    private void setSpecialVertices(ArrayList<Vertex> specialVertices, ArrayList<Integer> weights) {
        special = new HashMap<Vertex, Integer>();

        // Random special vertices
        int amountOfSpecial = vertices.size() / 3;
        specialVertices.clear();
        weights.clear();
        for (int i = 0; i < amountOfSpecial; i++) {
            specialVertices.add(vertices.get(random.nextInt(vertices.size())));
            weights.add(random.nextInt(10));
        }

        if (specialVertices.size() != weights.size()) {
            System.out.println("Error: not the same amount of vertices and weights");
            System.exit(-1);
        }

        for (int i = 0; i < weights.size(); i++) {
            Vertex key = specialVertices.get(i);
            int value = weights.get(i);
            special.put(key, value);
        }
    }

    private int exhaust(ArrayList<ArrayList<Vertex>> subgroups) {
        int coherence = 0;
        ArrayList<ArrayList<Vertex>> winningGroups = new ArrayList<>();
        // Every group is a possible truth assignment
        for (ArrayList<Vertex> group : subgroups) {
            if (coherence < calcCoherence(group))
                winningGroups.add(group);
            coherence = Math.max(coherence, calcCoherence(group));
        }
        printTruthAssignment(winningGroups.get(winningGroups.size() - 1));
        return coherence;
    }

    private int calcCoherence(ArrayList<Vertex> subgroup) {
        int coherence = 0;
        for (Edge e : edges) {
            boolean v1True = subgroup.contains(e.getV1());
            boolean v2True = subgroup.contains(e.getV2());
            boolean equalTruth = v1True == v2True;
            // Positive edge: both vertices have the same truth assignment
            // Negative edge: vertices have unequal truth assignments
            if (positive.contains(e) && equalTruth) {
                coherence += e.getWeight();
            }
            else if (!equalTruth) {
                coherence += e.getWeight();
            }
        }

        // Loop over special vertices
        for (Map.Entry<Vertex, Integer> entry : special.entrySet()) {
            Vertex v = entry.getKey();
            int weight = entry.getValue();

            if (subgroup.contains(v)) {
                coherence += weight;
            }
        }
        return coherence;
    }

    private ArrayList<ArrayList<Vertex>> getSubgroups(ArrayList<Vertex> vertices) {
        SubsetIterator<Vertex> subsetIterator = new SubsetIterator<>(vertices);
        ArrayList<ArrayList<Vertex>> vertexSubgroups = new ArrayList<>();

        while (subsetIterator.hasNext()) {
            vertexSubgroups.add(subsetIterator.next());
        }
        return vertexSubgroups;
    }

    /**
     * Creates a complete graph of size n.
     * @param n number of vertices
     */
    private void createCompleteGraph(int n) {
        List<Vertex> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        positive = new ArrayList<>();
        //temp = new ArrayList<>();
        //fin = new ArrayList<>();


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
                // Randomize edge's constraint
                if (random.nextInt(2) > 0)
                    positive.add(e);
                //else
                //    negative.add(e);
            }
        }
        graph = new Graph(vertices, edges);
    }


    /**
     * Function for the exhaustive coherence algorithm
     * Jordy is een beast
     * @param temp = the temporary list of vertices that are accepted/assigned true
     * @param index = which vertex is being considered
     * @param length = the length of the current accepted list/assigned vertices
     * @param best = the best coherence value
     * @return
     */
//    private int coherence_exhaustive (ArrayList<Vertex> temp, int index, int length, int best) {
//        if (temp.size() == length) {
//            // compute coherence here with accepted vertices in temp, and the rejected vertices that are not in temp
//            if (compute_d_coherence(temp) > best) {
//                best = compute_d_coherence(temp);
//                //this.fin = temp;
//                // Uncomment below to see the possible ways of assigning the elements to accepted
//                /*
//                for (Vertex v : temp)
//                    System.out.print(v + " ");
//                System.out.println(); */
//            }
//            return 1;
//        }
//        else if (index >= vertices.size()) {
//            return 0;
//        }
//        else {
//            Vertex v = vertices.get(index);
//            temp.add(v);
//            int with = coherence_exhaustive(temp, index+1, length, best);
//            temp.remove(v);
//            int without = coherence_exhaustive(temp, index+1, length, best);
//            return with + without;
//        }
//    }


    /**
     * Function that computes the coherence of a specific graph with its truth/false assignment
     * @param temp the true/false assignment of the graph
     * @return the coherence value for a given graph and dividing of accepted and rejected vertices
     */
//    private int compute_d_coherence(List <Vertex> temp) {
//        int coherence = 0;
//        for (Edge e : edges) {
//            Vertex v1 = e.getV1();
//            Vertex v2 = e.getV2();
//
//            // if the edge has a positive constraint and the vertices (of that edge) have the same truth assignment
//            if (positive.contains(e) && temp.contains(v1) && temp.contains(v2))
//                coherence += e.getWeight();
//            else if (positive.contains(e) && !temp.contains(v1) && !temp.contains(v2))
//                coherence += e.getWeight();
//
//            // if the edge has a negative constraint and the vertices (of that edge) do not have the same truth assignment
//            else if (negative.contains(e) && temp.contains(v1) && !temp.contains(v2))
//                coherence += e.getWeight();
//            else if (negative.contains(e) && !temp.contains(v1) && temp.contains(v2))
//                coherence += e.getWeight();
//        }
//
//        // implement code here for adding weights of special vertices
//
//        return coherence;
//    }
}
