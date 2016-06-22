/*
 * A Graph object to organize Nodes in an ArrayList and create parents / chilren
 */
package lexer;

import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * @author Ryan.Solnik
 */
public class Graph {

    private String name;
    public ArrayList<GraphNode> knownNodes = new ArrayList<GraphNode>();
    
    public Graph(ArrayList<GraphNode> nodeList)
    {
     buildGraph(nodeList);
    }

    public void addGraphNode(GraphNode node)
    {
        if(!knownNodes.contains(node))
        {
            this.knownNodes.add(node);
        }
    }
    
    public void addParentChild(GraphNode parent, GraphNode child)
    {
        addGraphNode(parent);
        addGraphNode(child);
        parent.addChild(child);
    }
    
    public String toString()
    {
        
        String output ="";
        
        if(this.knownNodes.size()>0){
            output = this.knownNodes.get(0).toString();
            for(GraphNode node: knownNodes){
                output= output + ", "+ node.toString();
            }
        }
        
        return output;
    }
    
    public void buildGraph(ArrayList<GraphNode> nodeList){
        
        for(GraphNode n: nodeList){
            addGraphNode(n);
        }
    }
    
    public GraphNode getNode(String nodeName){
     
        for(GraphNode node: knownNodes){
            if(node.name.equals(nodeName))
                return node;
        }
        return null;
    }
}

