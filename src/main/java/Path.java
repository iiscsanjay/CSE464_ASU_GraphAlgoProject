import java.util.LinkedList;
import java.util.Iterator;

/**
 * @author : Sunanada Tummala
*/

public class Path {
    /**
     * Declaring Path Class for storing path from src to dst node
    */
    
    // linkedlist to store the nodes in the path
    private LinkedList<String> nodes;


    /**
     * Default Constructor: Constructor to initialize the linkedlist to store nodes 
    */
    public Path() {
        nodes = new LinkedList<String> ();
    }


    /**
     * addNode method: adds the input node into the path in the front
    */
    public void addNode(String label) {
        nodes.addFirst(label);
    }

    /**
     * addNodeInLast method: adds the input node into the path in the last 
    */
    public void addNodeInLast(String label) {
        nodes.add(label);
    }

    /** 
     * removeNode method: removes the input node from the path
    */
    public boolean removeNode(String label) {
        return nodes.remove(label);
    }
    
    /**
     * isEmpty method : returns boolean true is nodes list is empty, else return false 
     */
    public boolean isEmpty() {
        return nodes.isEmpty(); 
    }
    
    /**
     * getSize method: returns the number of nodes in the list
     */
    public int getSize(){
        return nodes.size();
    }
    
    /**
     * getLastNode method: return the last node of the nodes list
     */
    public String getLastNode() {
        String v = nodes.get(nodes.size() -1);
        return v;
    }
    
    /**
     * removeLastNode method: removes and return the last node of the nodes list
     */
    public boolean removeLastNode() {
        String v = nodes.get(nodes.size() -1);
        nodes.remove(nodes.size() -1);
        return true;
    }
    /**
     * removeNodes method: removes the all the nodes from path
    */
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


    /**
     * toString method: returns the string to the Path class object
    */
    @Override
    public String toString() {
        return getPath();
    }


    /**
     * getPath method : generates the path string and returns
    */
    public String getPath() {
        Iterator<String> it = nodes.iterator();
        String path = "";
        if (it.hasNext()){
            path += "Path: ";
            path += it.next();
            while(it.hasNext()) {
                path += "->" + it.next();
            }
        }
        return path;
    }

    
    /**
     * displayPath method: prints the path of the graph
    */
    public void displayPath() {
        String path = getPath();
        if (path.isEmpty() == true) {
            System.out.println(path);
        }
        else {
            System.out.println("No Path Found!");
        }
    }


}
