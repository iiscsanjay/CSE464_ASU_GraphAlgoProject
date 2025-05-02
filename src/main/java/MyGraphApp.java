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
  
    // Calling search according to strategy
    public Path search(SearchStrategy searchMethod) {
        return searchMethod.searchPath();
    }

    
    /**
     * GraphSearch method : returns the path between the srcLabel to dstLabel via given algorithm """
     */
    public Path GraphSearch(String srcLabel, String dstLabel, Algorithm algo) {
        
        boolean status1 = graph.containNode(srcLabel);
        boolean status2 = graph.containNode(dstLabel);
        
        // initializing the template typei with bfs
        //SearchTemplate stype = new BFSTemplate();

        // If both source and destination node is found in graph,
        if (status1 == true & status2 == true) {

            // check the path by BFS
            if (algo == Algorithm.BFS ) {
                // Template Pattern
                //return stype.searchPath(graph, srcLabel, dstLabel);
                
                // Strategy Pattern
                return search(new BFSStrategy(graph, srcLabel, dstLabel));
            }
            // check the path by DFS
            else if (algo == Algorithm.DFS ) {
                // Template Pattern
                //stype = new DFSTemplate(); 
                //return stype.searchPath(graph, srcLabel, dstLabel);
                
                // Strategy Pattern
                return search(new DFSStrategy(graph, srcLabel, dstLabel));
            }
            // check the path by RWS 
            else if (algo == Algorithm.RWS ) {
                // Template Pattern
                //stype = new RWSTemplate(); 
                //return stype.searchPath(graph, srcLabel, dstLabel);
                
                // Strategy Pattern
                return search(new RWSStrategy(graph, srcLabel, dstLabel));
            }
            else {
                return null;
            }
        }
        return null;
    }
}
    
