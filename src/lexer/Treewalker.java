/*
 *This Class implements treewalking methods for analysis
 */
package lexer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Ryan.Solnik
 */
public class Treewalker {

    ArrayList<String> visited = new ArrayList<>();

    public Treewalker() {
    }

    //determines what nodes in the graph / tree are leaf nodes.
    public void findLeaves(ArrayList<GraphNode> NodeList) {
        
        for (GraphNode node : NodeList) 
        {
            if (!isVisited(node)) 
            {
                if (node.children.isEmpty()) 
                {
                    System.out.println(node.name + " is a leaf node");
                }
                visited.add(node.name);
                findLeaves(node.children);

            }
        }
        if (visited.equals(NodeList)) {
            visited.clear();
        }
    }

    //does a breadth first search at the root of a tree
    public void bfs(GraphNode root)
	{
		Queue queue = new LinkedList();
                GraphNode Start = root;
                

                root.visit();
                queue.add(root);
//		System.out.println("added "+ root.name);
                
		while(!queue.isEmpty()) {
			GraphNode node = (GraphNode)queue.remove();
                        System.out.println("Parent:\t"+node.name);
			                        
                        for(GraphNode kid: node.children){
                            if(!kid.visited){
                                kid.visit();
                                System.out.println("\tChild:\t "+kid.name);
                                
                                node = kid;
                                queue.add(kid);
                            }
                        }
		}
            
                // Clear visited property of nodes
//		resetVisits(Start);
	}    
    
    //prints out the fragmented parts of the tree / graph.
    public void findFragements(ArrayList<GraphNode> nodes)
    {
        for(GraphNode node: nodes){
            if(node.visited==false){System.out.println(node.name + " is a fragment");}
        }
    }
    
    //resets the visit attribute of Nodes in the explored graph.
    private void resetVisits(GraphNode root) {
        Queue unVisitQueue = new LinkedList();

                root.unvisit();
                unVisitQueue.add(root);
//		System.out.println("added "+ root.name);
                
		while(!unVisitQueue.isEmpty()) {
			GraphNode node = (GraphNode)unVisitQueue.remove();
//                        System.out.println(node.name);
			                        
                        for(GraphNode kid: node.children){
                            if(kid.visited){
                                kid.unvisit();
//                                System.out.println(kid.name);
                                node = kid;
                                unVisitQueue.add(kid);
                            }
                        }
                        System.out.println(" ");
		}
    }
    
    //prints which node has the most children
    public void mostChildrenOutput(ArrayList<GraphNode> NodeList) {
        String output = mostChildren(NodeList);
        System.out.println(output);
    }
    
    //helper method to determine node with most children
    private String mostChildren(ArrayList<GraphNode> NodeList) {

        int maxConnections = 0;
        String nodeName = "";
        for (GraphNode node : NodeList) {
            if (!isVisited(node)) {

                if (node.children.size() > maxConnections) {
                    maxConnections = node.children.size();
                    nodeName = node.name;
                }
                visited.add(node.name);
                findLeaves(node.children);
            }
        }
//        visited.clear();
        return nodeName + " has the most children @" + maxConnections;
    }

    //unused
    //for use with visitor array
    public boolean isVisited(GraphNode node) {

        if (visited.contains(node.name)) {
            return true;
        }
        return false;
    }

    //unused
    //returns an unvisited child node of the parent
    public GraphNode getUnvisitedChildNode(ArrayList<GraphNode> parent){
        
        for (GraphNode child: parent)
        {
            if(child.visited == false)
            {
            return child;    
            }
                }
        return null;
    }
    //unused
    public GraphNode getVisitedChildNode(ArrayList<GraphNode> parent){
        
        for (GraphNode child: parent)
        {
            if(child.visited == true)
            {
            return child;    
            }
                }
        return null;
    }
    
}
