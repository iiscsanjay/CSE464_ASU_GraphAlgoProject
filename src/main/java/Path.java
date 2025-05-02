import java.util.LinkedList;
import java.util.Iterator;

public class Path {
    private LinkedList<String> nodes;

    public Path() {
        nodes = new LinkedList<String> ();
    }

    public void addNode(String label) {
        nodes.addFirst(label);
    }

    public void removeNodes() {
        nodes.clear();
    }

    public String getPath() {
        Iterator<String> it = nodes.iterator();
        String path = "Path: ";
        if (it.hasNext()){
            path += it.next();
            while(it.hasNext()) {
                path += "->" + it.next();
            }
        }
        return path;
    }

    public void displayPath() {
        Iterator<String> it = nodes.iterator();
        if (it.hasNext()){
            System.out.print("Path: ");
            System.out.print(it.next());
            while(it.hasNext()) {
                System.out.print("->" + it.next());
            }
            System.out.println("");
        }
        else {
            System.out.println("No Path Found!");
        }
    }
}
