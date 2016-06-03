/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

import java.util.ArrayList;

/**
 *
 * @author Ryan.Solnik
 */
public class Parser {

    Token currentToken;
    ArrayList<Token> tokens;
    ArrayList<Node> SyntaxTree = new ArrayList<>();
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
        } else if (currentToken.tokenType.equals("ID")) {
            accept(currentToken, "ID");
            if (currentToken.tokenType.equals("D_EDGEOP") || (currentToken.tokenType.equals("U_EDGEOP"))) {
                parseEdgeStatement();
                parseSemicolon();
            }
            else if(currentToken.tokenType.equals("LEFT_BR")){
                parseNodeCreation();
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
    
    private void parseNodeCreation(){
        if(currentToken.tokenType.equals("LEFT_BR")){
            accept(currentToken, "LEFT_BR");
            parseAssignmentList();
            accept(currentToken, "RIGHT_BR");
//            parseSemicolon();
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

    private void parseEdgeStatement() {
        parseEdgeType();

        if (currentToken.tokenType.equals("ID")) {
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

    private void addGraphNode(Node nodeID){
        
        if(!(SyntaxTree.contains(nodeID)))
        {
            SyntaxTree.add(nodeID);    
            
        }
    }
    
    private void add_parent_child(Node parent, Node child){
        addGraphNode(parent);
        addGraphNode(child);
        if(!(parent.children.contains(child))){
            parent.addChild(child);
        }
    }
}
