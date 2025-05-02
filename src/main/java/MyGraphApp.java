import java.io.*;
import java.util.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.nio.*;
import org.jgrapht.nio.dot.*;
import org.jgrapht.util.*;

public class MyGraphApp {
    private Graph<String, DefaultEdge> directedGraph;
    
    public MyGraphApp() {
        directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    }
    
    // Feature 1 : parseGraph, toString, outputGraph
    
    public void parseGraph(String filepath) {
    
        // Read the input dot file as string 
        StringBuilder contentBuilder = new StringBuilder();
    
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) 
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            String source = directedGraph.getEdgeSource(e);
            String target = directedGraph.getEdgeTarget(e);
            currentLine = "\tEdge : " + source + " -> " + target + "\n";
            graphBuilder.append(currentLine);
        }
        return  graphBuilder.toString(); 
    }

    public void outputGraph(String filepath) {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(filepath)));
            writer.write(toString());
        } catch (IOException ex) {
            // Report
        } finally {
           try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }

    // Feature 2 : addNode, addNodes
    
    public boolean addNode(String label) {
        boolean status = directedGraph.addVertex(label);
        return status;
    }

    public void addNodes(String [] label) {
        for(String nv : label ){
            boolean status = addNode(nv);
            if (!status) {
                System.out.println("Label " + nv + " already exists in Graph");
            }
        }
    }
}
