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
    public void parseGraph(String filepath) {
       
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

        DOTImporter<String, DefaultEdge> dotImporter = new DOTImporter<>();
        dotImporter.setVertexFactory(label -> label);

        Map<String, Map<String, Attribute>> attrs = new HashMap<>();
        dotImporter.addVertexAttributeConsumer((p, a) -> {
            Map<String, Attribute> map = attrs.computeIfAbsent(p.getFirst(), k -> new HashMap<>());
            map.put(p.getSecond(), a);
        });
        dotImporter.importGraph(directedGraph, new StringReader(contentBuilder.toString()));

        // Printing the Number of Nodes
        System.out.println("Number of Nodes: " + directedGraph.vertexSet().size());
        for(String v : directedGraph.vertexSet() ) {
            System.out.println("\tLabel of Node : " + v);
        }
        // Printing the Number of Edges 
        System.out.println("Number of Edges: " + directedGraph.edgeSet().size());
        //for(String v : directedGraph.vertexSet() ) {
        //    System.out.println("\tNodes: Label of Node : " + v);
        //}
        for(DefaultEdge e : directedGraph.edgeSet() ) {
            String source = directedGraph.getEdgeSource(e);
            String target = directedGraph.getEdgeTarget(e);
            System.out.println("\tEdge : " + source + " -> " + target);
        }
        
    }

    public String toString() {
        return directedGraph.toString();
    }

    public void outputGraph(String filepath) {
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>();
        exporter.setVertexAttributeProvider((v) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.toString()));
            return map;
        });
        try {
            exporter.exportGraph(directedGraph, new FileWriter(filepath));
        }catch (IOException e){
        }
    }
}
