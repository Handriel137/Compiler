
package lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Ryan.Solnik
 */
public class Parser {

    Token currentToken;
    String nodeName;
    ArrayList<Token> tokens;
    HashMap<String, Node> createdNodes = new HashMap<>();
    ArrayList<GraphNode> nodesList = new ArrayList<>();
    
    int tokenIndex;

    public Parser() {

        Lexer lex = new Lexer();
        this.tokens = lex.execute();
        tokenIndex = 0;
        
        this.currentToken = tokens.get(0);
//    System.out.println(currentToken);

    }

    public void parseInput() {
        parseStrict();
        parseGraphType();
    }

    public void parseGraphType() {
        if (currentToken.tokenValue.equals("digraph")) {
            accept(currentToken, "DIGRAPH");
            accept(currentToken, "ID");
            accept(currentToken, "LEFT_CB");
            parseStatementList();
            accept(currentToken, "RIGHT_CB");

        } else if (currentToken.tokenValue.equals("graph")) {
            accept(currentToken, "GRAPH");
            accept(currentToken, "ID");
            accept(currentToken, "LEFT_CB");
            parseStatementList();
            accept(currentToken, "RIGHT_CB");
        } else {
            System.err.println("Something is wrong on line " + currentToken.lineNumber);
            System.exit(1);
        }

    }

    private void parseStatementList() {

        while (!(currentToken.tokenType.equals("RIGHT_CB"))) {
            parseStatement();
        }
    }

    private void parseStatement() {
        if (currentToken.tokenType.equals("NODE")) {
            parseNodeStatement();
        } 
        
        else if (currentToken.tokenType.equals("ID")) {
            nodeName = currentToken.getValue();
            accept(currentToken, "ID");
            if (currentToken.tokenType.equals("D_EDGEOP") || (currentToken.tokenType.equals("U_EDGEOP"))) {
                parseEdgeStatement(nodeName);
                parseSemicolon();
            }
            else if(currentToken.tokenType.equals("LEFT_BR")){
                parseNodeCreation(nodeName);
            }
            
            else if(currentToken.tokenType.equals("EQUALS")){
                parseSingleAssign();
            }
        } 
        else if (currentToken.tokenType.equals("SUBGRAPH")) {
            parseSubgraph();

        } else {
            System.err.println("Something is wrong on line " + currentToken.lineNumber);
            System.exit(1);
        }
    }
 
    private void parseSubgraph(){
        if(currentToken.tokenType.equals("SUBGRAPH"))
        {
            accept(currentToken, "SUBGRAPH");
            parseID();
            accept(currentToken,"LEFT_CB");
            parseStatementList();
            accept(currentToken,"RIGHT_CB");
        }
    }
    
    private void parseSingleAssign(){
        if(currentToken.tokenType.equals("EQUALS")){
            accept(currentToken,"EQUALS");
            accept(currentToken,"ID");
            parseSemicolon();
        }
    }
    
    private void parseNodeCreation(String nodeName){
        
        addNode(nodeName);
        if(currentToken.tokenType.equals("LEFT_BR")){
            accept(currentToken, "LEFT_BR");
            parseAssignmentList();
            accept(currentToken, "RIGHT_BR");
            parseSemicolon();
        }
        else
        {
            System.err.println("Something is wrong on line " + currentToken.lineNumber);
            System.exit(1);
        }
    }
    
    private void parseNodeStatement() {
        if (currentToken.tokenType.equals("NODE")) {
            accept(currentToken, "NODE");
            parseAttributeList();
            parseSemicolon();
        }

    } 

    private void parseAttributeList() {
        if (currentToken.tokenType.equals("LEFT_BR")) {
            accept(currentToken, "LEFT_BR");
            parseAssignmentList();
            accept(currentToken, "RIGHT_BR");
        }

    }

    private void parseAssignmentList() {
        parseAssignment();

        if (currentToken.tokenType.equals("COMMA")) {
            accept(currentToken, "COMMA");
            parseAssignmentList();
        }
        if (currentToken.tokenType.equals("SEMICOLON")) {
            accept(currentToken, "SEMICOLON");
            parseAssignmentList();
        }
    }

    private void parseAssignment() {
        accept(currentToken, "ID");
        accept(currentToken, "EQUALS");
        accept(currentToken, "ID");

    }

    private void parseStrict() {
        if (currentToken.getValue().equals("strict")) {
            accept(currentToken, "ID");
        }
    }

    private void parseEdgeStatement(String nodeName) {
        parseEdgeType();

        if (currentToken.tokenType.equals("ID")) {
            addParentChild(nodeName,currentToken.getValue());
            accept(currentToken, "ID");
            parseAttributeList();
            

        } else {
            System.err.println("Something is wrong on line " + currentToken.lineNumber);
            System.exit(1);
        }

    }
    
    private void parseEdgeType() {
        if (currentToken.tokenType.equals("D_EDGEOP")) {
            accept(currentToken, "D_EDGEOP");
        } else if (currentToken.tokenType.equals("E_EDGEOP")) {
            accept(currentToken, "E_EDGEOP");
        } else {
            System.err.println("Something is amiss @" + currentToken.lineNumber);
            System.exit(1);
        }
    }

    private void parseSemicolon() {
        if (currentToken.tokenType.equals("SEMICOLON")) {
            accept(currentToken, "SEMICOLON");
        }
    }

    private void parseID() {
        if (currentToken.tokenType.equals("ID")) {
            accept(currentToken, "ID");
        }
    }

    private void nextToken() {

        if (tokens.get(tokenIndex).tokenType.equals("EOF")) {
            System.exit(1);
        } else {
            tokenIndex += 1;
            currentToken = tokens.get(tokenIndex);

        }
    }

    private void accept(Token currentToken, String tokenType) {
        if (currentToken.getType().equals(tokenType)) {
//            System.out.println("Accepted " + currentToken.tokenValue);
            nextToken();

        } else {
            System.err.println("Something is amiss @Line " + currentToken.lineNumber);
            System.exit(1);
        }

    }

    //Checks if a "ID" is already created as a node if it is not
    //then it creates a new node and adds to the hashmap
    private void addNode(String nodeName){
        
        if(!(createdNodes.containsKey(nodeName)))
        {
          createdNodes.put(nodeName, new Node(nodeName));
          
        }
        
        if(!(nodeCheck(nodeName))){
            nodesList.add(new GraphNode(nodeName));
            
        }
        
    }
    
    //Calls addNode for the ID's in the argument for node creation
    //Adds the child node to the parent nodes Childe HashMap.
    private void addParentChild(String parent, String child){
        addNode(parent);
        addNode(child);
        
        //
        if(!(createdNodes.get(parent).children.containsKey(child))){
            createdNodes.get(parent).children.put(child, createdNodes.get(child));
//            createdNodes.get(parent).addChild(child, createdNodes.get(child));
        }
        
        getNode(parent).addChild(getNode(child));
    }
    
    public boolean nodeCheck(String nodeName){
     
        for(GraphNode node: nodesList){
            if(node.name.equals(nodeName))
                return true;
        }
        return false;
    }
    
    public GraphNode getNode(String nodeName){
     
        for(GraphNode node: nodesList){
            if(node.name.equals(nodeName))
                return node;
        }
        return null;
    }
    
    private GraphNode getGraphNode(String s){
        
        return nodesList.get(nodesList.indexOf(s));
    }
    
    public void printNodes(){
        Set<String> nodes = createdNodes.keySet();
        
        //iterates through each created node
        for(String node : nodes){
            
            //prints node name
            System.out.println("Node Name : "+ node);
            
           
            //gets the list of children from the node
            Set<String> kids = createdNodes.get(node).children.keySet();
            
            //iterates through each created child
            for(String kid : kids){
                System.out.println("\tChildren : "+ kid);
            }
        }
    }
}
