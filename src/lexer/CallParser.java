/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ryan.Solnik
 */
public class CallParser {

    //CMD Line Argument = file name
    public static void main(String[] args) {
        
        Lexer lex = new Lexer();
        ArrayList<Token> tokens = new ArrayList<Token>();
        Parser parser = new Parser();
        
       parser.parseInput();
        
    }
}
