import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;

public class MyGraphAppTest {
    
    public String inputFile = "input.dot";
    public String outFile = "output.txt";
    @Test
    public void testNode() {
        MyGraphApp mg = new MyGraphApp();
        mg.parseGraph(inputFile);
        String result = mg.toString();
        System.out.println(result);
        mg.outputGraph(outFile);
    }
}
