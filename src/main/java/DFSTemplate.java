import java.io.*;
import java.util.*;
import org.jgrapht.graph.*;

public class DFSTemplate extends SearchTemplate {

    /**
     * searchPath method : it search for the path from srcLabel 
     * to dstLabel by following the depth for search algorithm 
     */
    @Override 
    public Path searchPath(MyGraph graph, String srcLabel, String dstLabel){
        
        // Printing the Search Type 
        System.out.println("Depth First Search: from " + srcLabel + " => " + dstLabel);
        
        // declaring path p as null
        Path p =  new Path();
        
        // declaring the visited nodes
        Set<String> visited = new HashSet<>();

        // declaring the map/dict to hold the parent -> connecting path
        HashMap<String, String> parent = new HashMap<>();
        
        // declaring the stack to hold unexplored nodes
        Stack<String> stack = new Stack<>();

        // Initializing the stack, visited nodes and parents dictionary for path
        stack.push(srcLabel);
        parent.put(srcLabel,null);

        //Running the loop until stack becomes empty
        while (!stack.isEmpty()) {

            // removing the top element
            String currentNode = stack.pop();

            visited.add(currentNode);
            p.addNodeInLast(currentNode);
            //System.out.println("Exploring Node: " + currentNode);
            System.out.println("visiting " + p.getPath() );
            
            // Checks if currentNode is the destination node
            if (currentNode.equals(dstLabel)) {
                p = new Path();
                while (currentNode != null) {
                    p.addNode(currentNode);
                    currentNode = parent.get(currentNode);
                }
                System.out.println(p);
                return p;
            }

            for(DefaultEdge e : graph.getEdgeSet() ) {
                String source = graph.getEdgeSource(e);
                String neighbor  = graph.getEdgeTarget(e);
                if (source.equals(currentNode)) {
                    //System.out.println(source + "=>" + neighbor );
                    if (! visited.contains(neighbor)) {
                        stack.push(neighbor);
                        parent.put(neighbor, currentNode);
                    }
                }
            }
        }
        return null;
    }
}
