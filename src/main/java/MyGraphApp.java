import java.io.*;
import java.util.*;
import java.lang.ProcessBuilder;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.nio.*;
import org.jgrapht.nio.dot.*;
import org.jgrapht.util.*;

/**
 * @author : iiscsanjay
*/

public class MyGraphApp {
    
    /**
     * Declaring MyGraphApp Class for graph exploration
    */

    // Graph object to store and process the graph
    private Graph<String, DefaultEdge> directedGraph;

    // MyGraphIO object to handles read and writes into files
    private MyGraphIO mio = null;
    

    /*
     * Algorithm enum variable for selecting the algorithm
     */
    enum Algorithm{
        BFS,
        DFS,
        RWS
    }
    
    /**
     * Default Constructor to initialize the directedGraph object
     */
    public MyGraphApp() {
        directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        mio = new MyGraphIO();
    }

    
    /** 
     * addNode method: adds the node label into the graph vertex and return the status
     */
    public boolean addNode(String label) {
        boolean status = directedGraph.addVertex(label);
        return status;
    }
    
    
    /**
     * addNodes method: adds the nodes from array into the graph vertexes and return the status
     */
    public boolean addNodes(String [] label) {
        boolean result = true;
        for(String nv : label ){
            boolean status = addNode(nv);
            result = result & status;
            if (!status) {
                System.out.println("Label " + nv + " already exists in Graph");
            }
        }
        return result;
    }
    
    
    /**
     * addEdge method: adds the edge from srcLabel to dstLabel into the graph if both the labels exists in the graph, else return false 
     */
    public boolean addEdge(String srcLabel, String dstLabel) {
        boolean status = false;
        if (directedGraph.containsVertex(srcLabel) && directedGraph.containsVertex(dstLabel) ) {
            DefaultEdge e = directedGraph.addEdge(srcLabel, dstLabel);
            if (e == null)
                return false;
            else
                return true;
        }
        return status;
    }


    /**
     * removeNode method: remove the node from the directedGraph if found and  returns false
     */
    public boolean removeNode(String label) {
        boolean status = directedGraph.removeVertex(label);
        return status;
    }


    /**
     * removeNodes method: remove the nodes array from the directedGraph if found and  return the status.
     */
    public boolean removeNodes(String [] label) {
        boolean result = true;
        for(String nv : label ){
            boolean status = removeNode(nv);
            result = result & status;
            if (!status) {
                System.out.println("Label " + nv + " doesn't exist in Graph");
            }
        }
        return result;
    }


    /**
     * removeEdge method: remove the edge from the directedGraph from srcLabel to dstLabel label if exists and return the status
     */
    public boolean removeEdge(String srcLabel, String dstLabel) {
        boolean status = directedGraph.containsEdge(srcLabel, dstLabel);
        if (status) {
            directedGraph.removeEdge(srcLabel, dstLabel);
        }
        else {
                System.out.println("Edge from " + srcLabel + " -> " + dstLabel + " doesn't exist in Graph");
        }
        return status;
    }

    /**
     * toString method: returns the string of the MyGraph class object
     */
    @Override
    public String toString() {

        StringBuilder graphBuilder = new StringBuilder();

        String currentLine = "";

        // Printing the Number of Nodes
        currentLine = "Number of Nodes: " + directedGraph.vertexSet().size() + "\n";
        graphBuilder.append(currentLine);

        // Printing each Nodes Label
        for(String v : directedGraph.vertexSet() ) {
            currentLine = "\tLabel of Node : " + v + "\n";
            graphBuilder.append(currentLine);
        }

        // Printing the Number of Edges
        currentLine = "Number of Edges: " + directedGraph.edgeSet().size() + "\n";
        graphBuilder.append(currentLine);

        // Printing the Each Edge and Direction
        for(DefaultEdge e : directedGraph.edgeSet() ) {
            String srcLabel = directedGraph.getEdgeSource(e);
            String dstLabel = directedGraph.getEdgeTarget(e);
            currentLine = "\tEdge : " + srcLabel + " -> " + dstLabel + "\n";
            graphBuilder.append(currentLine);
        }
        return  graphBuilder.toString();
    }
    
    
    /**
     * parseGraph method: calls the readGraph method from 
     * MyGraphIO which reads dot file and update the graph 
     */
    public void parseGraph(String filepath) {
        mio.readGraph(directedGraph, filepath);
    }

    
    /**
     * outputGraph method: calls the writeGraph from MyGraphIO 
     * which writes the Graph details into the file given by 
     * filepath
     */
    public void outputGraph(String filepath) {
        mio.writeGraph(directedGraph, filepath);
    }

    
    /**
     * outputDOTGraph method: calls the writeDotGraph from 
     * MyGraphIO class which reads the Graph details and write 
     * the dot file given by filepath
     */
    public void outputDOTGraph(String path) throws IOException{
        mio.writeDotGraph(directedGraph, path);
    }
   

    /**
     * outputGraphics method: calls the generateGraphics method 
     * of MyGraphIO class which reads the graph and generate the 
     * dot file from it and call the dot executable to generate 
     * the graphics by given format and write into given 
     * filepath.
     */
    public void outputGraphics(String path, String format ) throws IOException {
        mio.writeImageFile(directedGraph, path, format);
    }
    
    /**
     * GraphSearch method : returns the path between the srcLabel to dstLabel via given algorithm """
     */
    public Path GraphSearch(String srcLabel, String dstLabel, Algorithm algo) {
        boolean status1 = directedGraph.containsVertex(srcLabel);
        boolean status2 = directedGraph.containsVertex(dstLabel);
        // If both source and destination node is found in graph,
        if (status1 == true & status2 == true) {
            // check the path by BFS
            if (algo == Algorithm.BFS ) {
                return bfsSearch(srcLabel, dstLabel);
            }
            // check the path by DFS
            else if (algo == Algorithm.DFS ) {
                return dfsSearch(srcLabel, dstLabel);
            }
            else {
                return null;
            }
        }
        return null;
    }
    
    /**
     * bfsSearch method : it search for the path from srcLabel 
     * to dstLabel by following the breadth for search algorithm 
     */
    public Path bfsSearch(String srcLabel, String dstLabel) {
        
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

            for(DefaultEdge e : directedGraph.edgeSet() ) {
                String source = directedGraph.getEdgeSource(e);
                String neighbor  = directedGraph.getEdgeTarget(e);
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
   
    /**
     * dfsSearch method : it search for the path from srcLabel 
     * to dstLabel by following the depth for search algorithm 
     */
    public Path dfsSearch(String srcLabel, String dstLabel) {
        
        // declaring path p as null
        Path p =  null;
        
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

            // Checks if currentNode is the destination node
            //System.out.println("Exploring Node: " + currentNode);
            if (currentNode.equals(dstLabel)) {
                p = new Path();
                while (currentNode != null) {
                    p.addNode(currentNode);
                    currentNode = parent.get(currentNode);
                }
                return p;
            }

            for(DefaultEdge e : directedGraph.edgeSet() ) {
                String source = directedGraph.getEdgeSource(e);
                String neighbor  = directedGraph.getEdgeTarget(e);
                if (source.equals(currentNode)) {
                    //System.out.println(source + "=>" + neighbor );
                    if (! visited.contains(neighbor)) {
                        stack.push(neighbor);
                        parent.put(neighbor, currentNode);
                    }
                }
            }
        }
        return p;
    }
}

