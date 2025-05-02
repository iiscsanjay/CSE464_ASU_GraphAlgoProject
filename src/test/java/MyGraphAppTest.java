import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;

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
        String outputFile = "output1.txt";
        String expectedFile = "expected1.txt";
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
        boolean expected;
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        expected = false;
        boolean result = mg.addNode("a");
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddNodePositiveCase() throws IOException{
        String inputFile = "input.dot";
        boolean expected;
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        expected = true;
        boolean result = mg.addNode("i");
        assertEquals(expected, result);
    }



    @Test
    public void testAddNodes() throws IOException{
        String inputFile = "input.dot";
        String outputFile = "output2.txt";
        String expectedFile = "expected2.txt";
        String [] nodes = { "i","j","k"};
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        mg.addNodes(nodes);
        mg.outputGraph(outputFile);
        String output = mg.toString();
        String expected = readFileAsString(expectedFile);
        assertEquals(expected, output);
    }
}
