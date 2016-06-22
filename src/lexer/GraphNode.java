/*
 *A Class for building Node Objects to fit into a Graph Object
 */
package lexer;

import java.util.ArrayList;

/**
 *
 * @author Ryan.Solnik
 */
public class GraphNode {

   String name;
   ArrayList<GraphNode> children= new ArrayList<>();
   boolean visited = false;
   
   public GraphNode(String name){
       this.name = name;
   }

   public void addChild(GraphNode node)
   {
       
       if(!children.contains(node) && node!=null)
       {
       children.add(node);    
       } 
   }
   
   public void visit(){visited = true;}
   
   public void unvisit(){ visited = false;}
   
   public String toString(){
       return name;
   }
}
