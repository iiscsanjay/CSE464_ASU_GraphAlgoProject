import java.io.*;
import java.util.*;
import java.lang.ProcessBuilder;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.nio.*;
import org.jgrapht.nio.dot.*;
import org.jgrapht.util.*;

/**
 * @author : Sunanada Tummala
*/

public class MyGraphIO {
    
    /**
     * readGraph method : reads the graph from dot file and build the directed graph and returns
     */
    public void readGraph(MyGraph graph,  String filepath) {

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

        graph.importGraph(contentBuilder);
    }
   
    
    /**
     * writeGraph method : reads the graph from directedGraph and write the details into the filepath
     */
    public void writeGraph(MyGraph graph,  String filepath) {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(filepath)));
            writer.write(graph.toString());
        } catch (IOException ex) {
            // Report
        } finally {
           try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }


    /**
     * writeDotGraph method: reads the graph from directedGraph and write the dot file into given path
     */
    public void writeDotGraph(MyGraph graph,  String path) throws IOException {
        graph.exportGraph(path);
    }
    
    /**
     * writeImageFile method: reads the graph from directedGraph 
     * and generate the dot file and utilize the dot executable 
     * to write the image by given format to given path
     */
    public void writeImageFile(MyGraph graph,  String path, String format) throws IOException {
        String inputFile = "output.dot";
        writeDotGraph(graph,inputFile);
        String outputFile = path +"." +  format;
        try {
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", inputFile, "-o" , outputFile);
            Process p = pb.start();
        }catch (IOException e){
        }
    }

}
