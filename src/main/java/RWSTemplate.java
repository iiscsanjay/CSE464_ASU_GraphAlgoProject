import java.io.*;
import java.util.*;
import java.util.Random;
import org.jgrapht.graph.*;

public class RWSTemplate extends SearchTemplate {

    /**
     * searchPath method : it search for the path from srcLabel 
     * to dstLabel by following the breadth for search algorithm 
     */
    public String getRandomElement(List<String> l) {
        Random r = new Random();
        return l.get(r.nextInt(l.size()));
    }
    
    @Override 
    public Path searchPath(MyGraph graph, String srcLabel, String dstLabel){
        
        // declaring path p as null
        Path p =  new Path();

        // declaring the visited edges 
        Set<DefaultEdge> visitedEdges = new HashSet<>();
        
        // declaring the node which is fully explored 
        Set<String> exploredVertices = new HashSet<>();

        // Initializing the queue, visited nodes and parents dictionary for path
        String currentNode = srcLabel;
        
        // adding current node into the path
        p.addNode(currentNode);
            
        // Printing the Search Type 
        System.out.println("Random Walk Search: from " + srcLabel + " => " + dstLabel);

        //Running the loop until currentNode != dstLabel
        while (!currentNode.equals(dstLabel)) {
            
            //System.out.println(currentNode);

            // Get all the neighbors of current Node
            List<String> neighbors = graph.getSuccessorNeighbors(currentNode);

        
            List<String> unvisitedVertices = new ArrayList<>();
            for (String v : neighbors) {
                DefaultEdge e = graph.getEdge(currentNode, v);
                if (!visitedEdges.contains(e)) {
                    unvisitedVertices.add(v);
                }
            }
            
            if (unvisitedVertices.size() > 0 ) {
                String nextNode = getRandomElement(unvisitedVertices);
                visitedEdges.add(graph.getEdge(currentNode, nextNode));
                if (exploredVertices.contains(nextNode)) {
                    if (!p.isEmpty()){
                        currentNode = p.getLastNode();
                        p.removeLastNode();
                    }
                }
                else {
                    currentNode = nextNode;
                    p.addNodeInLast(currentNode);
                    System.out.println("visiting " + p.getPath() );
                }
            }
            else {
                exploredVertices.add(p.getLastNode());
                if (p.getSize() == 1 ) {
                    return null;
                }
                p.removeLastNode();
                currentNode =  p.getLastNode();
                while (true) {
                    neighbors = graph.getSuccessorNeighbors(currentNode);
                    unvisitedVertices.clear();
                    for (String v : neighbors) {
                        DefaultEdge e = graph.getEdge(currentNode, v);
                        if (!visitedEdges.contains(e)) {
                            unvisitedVertices.add(v);
                        }
                    }
                    if (unvisitedVertices.size() == 0 ) {
                        exploredVertices.add(p.getLastNode());
                        if (p.getSize() == 1 ) {
                            return null;
                        }
                        p.removeLastNode();
                        currentNode = p.getLastNode();
                    }
                    else {
                        break;
                    }
                }
            }
        }
        System.out.println(p);
        return p;
    }
}
