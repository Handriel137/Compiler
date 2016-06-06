
package lexer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ryan.Solnik
 */
public class Node {

    HashMap<String, Node> children;
    String label;
    
    public Node(String name) {
        label = name;
        children = new HashMap<>(); // All of the attached nodes

    }
    
    public String getName(){
        return label;
    }
    
    public void addChild(String name, Node n){
        
        children.put(name, n);
    }
    

}
