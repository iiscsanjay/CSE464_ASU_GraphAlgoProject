import java.io.*;
import java.util.*;
import java.lang.ProcessBuilder;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.nio.*;
import org.jgrapht.nio.dot.*;
import org.jgrapht.util.*;

/**
 * @author: iiscsanjay 
*/

public class MyGraphIO {
    
    /**
     * readGraph method : reads the graph from dot file and build the directed graph and returns
     */
    public void readGraph(Graph<String, DefaultEdge> directedGraph,  String filepath) {

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
   
    
    /**
     * writeGraph method : reads the graph from directedGraph and write the details into the filepath
     */
    public void writeGraph(Graph<String, DefaultEdge> directedGraph,  String filepath) {
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


    /**
     * writeDotGraph method: reads the graph from directedGraph and write the dot file into given path
     */
    public void writeDotGraph(Graph<String, DefaultEdge> directedGraph,  String path) {
        // Initialize the DOTExporter object
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>(v -> v.toString());

        try {
            exporter.exportGraph(directedGraph, new FileWriter(path));
        }catch (IOException e){
        }
    }
    
    /**
     * writeImageFile method: reads the graph from directedGraph 
     * and generate the dot file and utilize the dot executable 
     * to write the image by given format to given path
     */
    public void writeImageFile(Graph<String, DefaultEdge> directedGraph,  String path, String format) {
        String inputFile = "output.dot";
        writeDotGraph(directedGraph,inputFile);
        String outputFile = path +"." +  format;
        try {
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", inputFile, "-o" , outputFile);
            Process p = pb.start();
        }catch (IOException e){
        }
    }

}
