/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

import java.util.HashMap;

/**
 *
 * @author Ryan.Solnik
 */
public class TokenTypes {
    
      public HashMap<String,String> type = new HashMap<String, String>();
    public TokenTypes()
    {    
        type.put("EOF", "EOF");
        type.put("{", "LEFT_CB");
        type.put("}", "RIGHT_CB");
        type.put("=", "EQUALS");
        type.put(",", "COMMA");
        type.put(";", "SEMICOLON");
        type.put("[", "LEFT_BR");
        type.put("]", "RIGHT_BR");
        type.put("->", "D_EDGEOP");
        type.put("--", "U_EDGEOP");
        type.put("ID", "ID");
        type.put("digraph", "DIGRAPH");
        type.put("graph", "GRAPH");
        type.put("node","NODE");
        type.put("edge", "EDGE");
        type.put("subgraph","SUBGRAPH");
    }
    
}
