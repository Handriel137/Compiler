
package lexer;

import java.util.ArrayList;

/**
 *
 * @author Ryan.Solnik
 */
public class Node {

    ArrayList<Node> children;

    public Node(String name) {
        String label = name;
        children = new ArrayList<Node>(); // All of the attached nodes

    }
    
    public void addChild(Node n){
        
        children.add(n);
    }
    
    public ArrayList<Node> getChildren(Node node) {
        return node.children;
    }

}
