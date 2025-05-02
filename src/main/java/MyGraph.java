import java.io.*;
import java.util.*;
import java.lang.ProcessBuilder;
import org.jgrapht.*;
import org.jgrapht.Graphs;
import org.jgrapht.graph.*;
import org.jgrapht.nio.*;
import org.jgrapht.nio.dot.*;
import org.jgrapht.util.*;

/**
 * @author : Sunanada Tummala
*/

public class MyGraph {
    /**
     * Declaring MyGraph Class for graph exploration 
    */
    
    // Graph object to store and process the graph
    private Graph<String, DefaultEdge> directedGraph;
    
    /**
     * Default Constructor to initialize the directedGraph object
     */
    public MyGraph() {
        directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
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
     * importGraph method: it is used to import the dot File string into graph vertexs and edges 
     */
    public void importGraph(StringBuilder contentBuilder) {
        
        // Generate the DOTImporter object
        DOTImporter<String, DefaultEdge> dotImporter = new DOTImporter<>();
        dotImporter.setVertexFactory(label -> label);

        Map<String, Map<String, Attribute>> attrs = new HashMap<>();
        dotImporter.addVertexAttributeConsumer((p, a) -> {
            Map<String, Attribute> map = attrs.computeIfAbsent(p.getFirst(), k -> new HashMap<>());
            map.put(p.getSecond(), a);
        });

        // update the directedGraph by using the dot file string read in contentBuilder
        dotImporter.importGraph(directedGraph, new StringReader(contentBuilder.toString()));
    }


    /**
     * exportGraph method: it is used to export the graph into the dot file
     */
    public void exportGraph(String path) {
        // Initialize the DOTExporter object
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>(v -> v.toString());
        try {
            exporter.exportGraph(directedGraph, new FileWriter(path));
        }catch (IOException e){
        }
    }
    
    /**
     * containNode method: it checks if node exists in the graph 
     * and returns the status as true or false 
     */
    public boolean containNode(String label) {
        return directedGraph.containsVertex(label);
    }
    
    public String getEdgeSource(DefaultEdge e) {
        return directedGraph.getEdgeSource(e);
    }

    public String getEdgeTarget(DefaultEdge e) {
        return directedGraph.getEdgeTarget(e);
    }
   
    public DefaultEdge getEdge(String source, String target) {
        return directedGraph.getEdge(source, target);
    }

    public Set<DefaultEdge> getEdgeSet() {
        return directedGraph.edgeSet();
    }
    
    public List<String> getSuccessorNeighbors(String source) {
        return Graphs.successorListOf(directedGraph, source);
    }
}
