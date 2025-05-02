import java.util.LinkedList;
import java.util.Iterator;

// Declaring Path Class
public class Path {

    // linkedlist to store the nodes in the path
    private LinkedList<String> nodes;

    // Default Constructor
    public Path() {
        nodes = new LinkedList<String> ();
    }
    
    // addNode method: adds the input node into the path
    public void addNode(String label) {
        nodes.addFirst(label);
    }
    
  // removeNode method: removes the input node from the path
    public boolean removeNode(String label) {
        return nodes.remove(label);
    }
    
    // removeNodes method: removes the all the nodes from path
    public void removeNodes() {
        Iterator<String> it = nodes.iterator();
        System.out.println("Removing Nodes: ");
        while(it.hasNext()) {
            boolean status = removeNode(it.next());
            if (status) {
                System.out.println("\tNode:" + it.next() + "is removed.");
            }
            else {
                System.out.println("\tNode:" + it.next() + "is failed to remove.");
            }
        }
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
