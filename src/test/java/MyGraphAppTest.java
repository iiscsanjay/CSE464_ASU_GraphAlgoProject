import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MyGraphAppTest {

    public static String readFileAsString(String filePath) throws IOException {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Test
    public void testFeature1Parse() throws IOException{
        String inputFile = "input.dot";
        String outputFile = "output.txt";
        String expectedFile = "expected.txt";
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        String output = mg.toString();
        mg.outputGraph(outputFile);
        String expected = readFileAsString(expectedFile);
        assertEquals(expected, output);
    }

    @Test
    public void testAddNodeNegativeCase() throws IOException{
        String inputFile = "input.dot";
        boolean expected = false;
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        boolean result = mg.addNode("a");
        assertEquals(expected, result);
    }

    @Test
    public void testAddNodePositiveCase() throws IOException{
        String inputFile = "input.dot";
        boolean expected = true;
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        boolean result = mg.addNode("i");
        assertEquals(expected, result);
    }

    @Test
    public void testAddNodes() throws IOException{
        String inputFile = "input.dot";
        String [] nodes = { "i","j","k"};
        String outputFile = "output2.txt";
        String expectedFile = "expected2.txt";
        MyGraphApp mg = new MyGraphApp();
        mg.addNodes(nodes);
        mg.outputGraph(outputFile);
        String output = mg.toString();
        String expected = readFileAsString(expectedFile);
        assertEquals(expected, output);
    }

    @Test
    public void testAddEdgeNegativeCase() throws IOException{
        String inputFile = "input.dot";
        boolean expected = false;
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        boolean result = mg.addEdge("c", "d");
        assertEquals(expected, result);
    }

    @Test
    public void testAddEdgePositiveCase() throws IOException{
        String inputFile = "input.dot";
        boolean expected = true;
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        boolean result = mg.addEdge("c", "e");
        assertEquals(expected, result);
    }

    @Test
    public void testOutputDotGraph() throws IOException{
        String inputFile = "input.dot";
        String outputFile = "output.dot";
        MyGraphApp oldG = new MyGraphApp();
        MyGraphApp newG = new MyGraphApp();
        oldG.parseGraph(inputFile);
        oldG.outputDOTGraph(outputFile);
        newG.parseGraph(outputFile);
        assertEquals(oldG.toString(), newG.toString());
    }

    @Test
    public void testOutputGraphics() throws IOException{
        String inputFile = "input.dot";
        String path = "output";
        String format = "png";
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        mg.outputGraphics(path, format);
        assertNotNull(path +"." + format);
    }

    @Test
    public void testRemoveNodePositiveCase() throws IOException {
        String inputFile = "input.dot";
        String label = "a";
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        boolean expected = true;
        boolean result = mg.removeNode(label);
        assertEquals(expected, result);

    }

    @Test
    public void testRemoveNodeNegativeCase() throws IOException {
        //String [] nodes = { "i","j","k"};
        String inputFile = "input.dot";
        String label = "i";
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        boolean expected = false;
        boolean result = mg.removeNode(label);
        assertEquals(expected, result);

    }

    @Test
    public void testRemoveNodesPositiveCase() throws IOException {
        String inputFile = "input.dot";
        String [] label = {"a", "b", "c" };
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        boolean expected = true;
        boolean result = mg.removeNodes(label);
        assertEquals(expected, result);

    }

    @Test
    public void testRemoveNodesNegativeCase() throws IOException {
        String inputFile = "input.dot";
        String [] label = {"a", "b", "i" };
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        boolean expected = false;
        boolean result = mg.removeNodes(label);
        assertEquals(expected, result);
    }

    @Test
    public void testRemoveEdgePositiveCase() throws IOException {
        String inputFile = "input.dot";
        String srcLabel = "b";
        String dstLabel = "c";
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        boolean expected = true;
        boolean result = mg.removeEdge(srcLabel,dstLabel);
        assertEquals(expected, result);
    }

    @Test
    public void testRemoveEdgeNegativeCase() throws IOException {
        String inputFile = "input.dot";
        String srcLabel = "c";
        String dstLabel = "b";
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        boolean expected = false;
        boolean result = mg.removeEdge(srcLabel,dstLabel);
        assertEquals(expected, result);
    }

}
