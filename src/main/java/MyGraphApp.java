import java.io.*;
import java.util.*;
import java.lang.ProcessBuilder;
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
            String srcLabel = directedGraph.getEdgeSource(e);
            String dstLabel = directedGraph.getEdgeTarget(e);
            currentLine = "\tEdge : " + srcLabel + " -> " + dstLabel + "\n";
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

    // Feature 3: Adding edges from the imported graph (10 points)
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

    // Feature 4 :  outputDOTGraph and : outputGraphics(String path, String format)
    public void outputDOTGraph(String path) throws IOException{

        // Initialize the DOTExporter object
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>(v -> v.toString());
        
        try {
            exporter.exportGraph(directedGraph, new FileWriter(path));
        }catch (IOException e){
        
        }
    }

    public void outputGraphics(String path, String format ) throws IOException {
        String inputFile = "output.dot";
        outputDOTGraph(inputFile);
        String outputFile = path +"." +  format;
        try {
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", inputFile, "-o" , outputFile);
            Process p = pb.start();
        }catch (IOException e){
        }
    }


    public boolean removeNode(String label) {
        boolean status = directedGraph.removeVertex(label);
        return status;
    }

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

    public Path GraphSearch(String srcLabel, String dstLabel) {
        
        Path p = null;

        boolean status1 = directedGraph.containsVertex(srcLabel);
        boolean status2 = directedGraph.containsVertex(dstLabel);
        
        // If both source and destination node is found in graph, then go for BFS search
        if (status1 == true & status2 == true) {

            // declaring the visited nodes
            Set<String> visited = new HashSet<>();

            // declaring the queue to hold unexplored nodes
            Queue<String> queue = new LinkedList<>();

            // declaring the map/dict to hold the parent -> connecting path
            HashMap<String, String> parent = new HashMap<>();

            // Initializing the queue, visited nodes and parents dictionary for path
            queue.add(srcLabel);
            visited.add(srcLabel);
            parent.put(srcLabel,null);

            //Running the loop until queue becomes empty
            while (!queue.isEmpty()) {

                // removing the front element
                String currentNode = queue.remove();
                
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
                        //System.out.println(source + "->" + neighbor + "=>" +  currentNode);
                        if (! visited.contains(neighbor)) {
                            visited.add(neighbor);
                            queue.add(neighbor);
                            parent.put(neighbor, currentNode);
                        }
                    }
                }
            }
        }
        else {
            System.out.println("Not Found");
        }
        return p;
    }

}
