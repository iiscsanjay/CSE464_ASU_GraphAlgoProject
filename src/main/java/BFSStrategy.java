import java.io.*;
import java.util.*;
import org.jgrapht.graph.*;

public class BFSStrategy implements SearchStrategy {
    
    private String srcLabel = "";
    private String dstLabel = "";
    private MyGraph graph = null;

    public BFSStrategy(MyGraph graph, String srcLabel, String dstLabel ){
        this.srcLabel = srcLabel;
        this.dstLabel = dstLabel;
        this.graph = graph;
    }

    /**
     * searchPath method : it search for the path from srcLabel 
     * to dstLabel by following the breadth for search algorithm 
     */
    @Override 
    public Path searchPath() {
        
        // declaring path p as null
        Path p =  null;

        // declaring the visited nodes
        Set<String> visited = new HashSet<>();

        // declaring the map/dict to hold the parent -> connecting path
        HashMap<String, String> parent = new HashMap<>();

        // declaring the queue to hold unexplored nodes
        Queue<String> queue = new LinkedList<>();

        // Initializing the queue, visited nodes and parents dictionary for path
        queue.add(srcLabel);
        parent.put(srcLabel,null);
        visited.add(srcLabel);

        //Running the loop until queue becomes empty
        while (!queue.isEmpty()) {

            // removing the front element
            String currentNode = queue.remove();

            //System.out.println("Exploring Node: " + currentNode);

            // Checks if currentNode is the destination node
            if (currentNode.equals(dstLabel)) {
                p = new Path();
                while (currentNode != null) {
                    p.addNode(currentNode);
                    currentNode = parent.get(currentNode);
                }
                //System.out.println(p.getPath());
                return p;
            }

            for(DefaultEdge e : graph.getEdgeSet() ) {
                String source = graph.getEdgeSource(e);
                String neighbor  = graph.getEdgeTarget(e);
                if (source.equals(currentNode)) {
                    // System.out.println(source + "=>" + neighbor);
                    if (! visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                        parent.put(neighbor, currentNode);
                    }
                }
            }
        }
        return p;
    }

}
