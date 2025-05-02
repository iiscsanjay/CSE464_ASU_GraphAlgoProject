import java.io.*;
import java.util.*;
import org.jgrapht.graph.*;

/**
 * @author: iiscsanjay 
*/

/**
 * Declaring MyGraphApp Class for graph exploration 
*/
public class MyGraphApp {
    /*
     * Algorithm enum variable for selecting the algorithm
     */
    enum Algorithm{
        BFS,
        DFS,
        RWS
    }
    
    // MyGraph object to store and process the graph
    private MyGraph graph = null;

    // MyGraphIO object to read and write the graph to and from to file 
    private MyGraphIO graphio = null;
    
    /**
     * Default Constructor to initialize the graph and graphio object of the MyGraph and MyGraphIO class 
     */
    public MyGraphApp() {
        graph = new MyGraph(); 
        graphio = new MyGraphIO();
    }

    /** 
     * addNode method: calls the addNode method from graph Class 
     * which returns the status
     */
    public boolean addNode(String label) {
        return graph.addNode(label);
    }
    
    
    /**
     * addNodes method: calls the addNodes method from graph 
     * Class which returns the status
     */
    public boolean addNodes(String [] label) {
        return graph.addNodes(label);
    }
    
    
    /**
     * addEdge method: calls the addEdge method from MyGraph which 
     * adds the edge from srcLabel to dstLabel into the graph if 
     * both the labels exists in the graph, else return false 
     */
    public boolean addEdge(String srcLabel, String dstLabel) {
        return graph.addEdge(srcLabel, dstLabel);
    }


    /**
     * removeNode method: calls the removeNode method from MyGraph 
     * class which remove the node from graph if found and  
     * returns false
     */
    public boolean removeNode(String label) {
        return graph.removeNode(label);
    }


    /**
     * removeNodes method: calls the removeNodes method from 
     * MyGraph Class which remove the nodes array from the 
     * directedGraph if found and  return the status.
     */
    public boolean removeNodes(String [] label) {
        return graph.removeNodes(label);
    }


    /**
     * removeEdge method: calls the removeEdge method from the 
     * MyGraph class which remove the edge from the directedGraph 
     * from srcLabel to dstLabel label if exists and return the 
     * status
     */
    public boolean removeEdge(String srcLabel, String dstLabel) {
        return graph.removeEdge(srcLabel,dstLabel);
    }
   
    /**
     * toString method: calls the toString method from MyGraph Class
     */
    @Override
    public String toString(){
        return graph.toString();
    }

    /**
     * parseGraph method: calls the readGraph method from 
     * MyGraphIO which reads dot file and update the graph 
     */
    public void parseGraph(String filepath) {
        graphio.readGraph(graph, filepath);
    }

    
    /**
     * outputGraph method: calls the writeGraph from MyGraphIO 
     * which writes the Graph details into the file given by 
     * filepath
     */
    public void outputGraph(String filepath) {
        graphio.writeGraph(graph, filepath);
    }

    
    /**
     * outputDOTGraph method: calls the writeDotGraph from 
     * MyGraphIO class which reads the Graph details and write 
     * the dot file given by filepath
     */
    public void outputDOTGraph(String path) throws IOException{
        graphio.writeDotGraph(graph, path);
    }
   

    /**
     * outputGraphics method: calls the generateGraphics method 
     * of MyGraphIO class which reads the graph and generate the 
     * dot file from it and call the dot executable to generate 
     * the graphics by given format and write into given 
     * filepath.
     */
    public void outputGraphics(String path, String format ) throws IOException {
        graphio.writeImageFile(graph, path, format);
    }
    
    
    /**
     * GraphSearch method : returns the path between the srcLabel to dstLabel via given algorithm """
     */
    public Path GraphSearch(String srcLabel, String dstLabel, Algorithm algo) {
        
        boolean status1 = graph.containNode(srcLabel);
        boolean status2 = graph.containNode(dstLabel);
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
        return p;
    }
   
}
