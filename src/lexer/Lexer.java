/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

/**
 *
 * @author Ryan.Solnik
 */
public class Lexer {

    List<LinedCharacter> result = new ArrayList<LinedCharacter>();
    ArrayList<Token> tokens = new ArrayList<>();
    TokenTypes types = new TokenTypes();
    Tokenizer tokenizer = new Tokenizer();
    int index;
    String index_character;
    int index_lineNumber;
    String pattern;
    String space;
    String quotes;
    String direction;
    String nonDirection;
    String comments;
    String c = "";

    public Lexer() {

        result = tokenizer.tokenize("HW2Input.txt");
        //position counter of string
        index = 0;
        //actual character at location in result
        index_character = result.get(index).getCharater();
        index_lineNumber = result.get(index).getLine();
        pattern = "[A-Za-z0-9\\.\\_\\\\]+";
        quotes =  "^[A-Za-z0-9\\.\\_\\\"\\\\ ]$";
        space = "[ \\t]";
        direction = "[->]";
        nonDirection = "[--]";
        

    }

    public ArrayList<Token> execute() {
        
        while (index < result.size()) {
            
            if ("}".equals(index_character)) {
                Token Current_token = new Token(index_character, types.type.get(index_character), 
                        result.get(index).getLine());
                tokens.add(Current_token);
                consume();
            } else if ("{".equals(index_character)) {
                Token Current_token = new Token(index_character, types.type.get(index_character), result.get(index).getLine());
                tokens.add(Current_token);
                consume();
            } else if ("=".equals(index_character)) {
                Token Current_token = new Token(index_character, types.type.get(index_character), result.get(index).getLine());
                tokens.add(Current_token);
                consume();
            } else if (Pattern.matches(space, index_character)) {
                consume();
            } else if (",".equals(index_character)) {
                Token Current_token = new Token(index_character, types.type.get(index_character), result.get(index).getLine());
                tokens.add(Current_token);
                consume();
            } 
            else if (";".equals(index_character)) {
                Token Current_token = new Token(index_character, types.type.get(index_character), result.get(index).getLine());
                tokens.add(Current_token);
                consume();
            } else if ("[".equals(index_character)) {
                Token Current_token = new Token(index_character, types.type.get(index_character), result.get(index).getLine());
                tokens.add(Current_token);
                consume();
            } else if ("]".equals(index_character)) {
                Token Current_token = new Token(index_character, types.type.get(index_character), result.get(index).getLine());
                tokens.add(Current_token);
                consume();
            } else if ("\"".equals(index_character)) {
                String lexeme = "";
                
                while (Pattern.matches(quotes, index_character)&& !(index_character.equals("EOF"))) {
                  
                    lexeme += result.get(index).getCharater();
                    consume();  
                    
                }
                
                Token Current_token = new Token(lexeme, types.type.get("ID"), result.get(index).getLine());
                tokens.add(Current_token);
            } 
            
            
            else if("EOF".equals(index_character)){
                Token Current_token = new Token(index_character, types.type.get(index_character), result.get(index).getLine());
                tokens.add(Current_token);               
                 return tokens;
            }
            
            else if ("-".equals(index_character)) {
                String lexeme = "";
                lexeme += result.get(index).getCharater();
                consume();

                if (Pattern.matches(direction, result.get(index).getCharater())) {
                    lexeme += result.get(index).getCharater();
                    consume();
                } else if (Pattern.matches(nonDirection, result.get(index).getCharater())) {
                    lexeme += result.get(index).getCharater();
                    consume();
                }
                Token Current_token = new Token(lexeme, types.type.get(lexeme), result.get(index).getLine());
                tokens.add(Current_token);

            } 
            
            else if (Pattern.matches(pattern, index_character)) {
                String lexeme = "";

                    
                    while (Pattern.matches(pattern, index_character)&& !(index_character.equals("EOF"))) 
                        {
                        lexeme += result.get(index).getCharater();
                        
                        consume();
                        }
                    
                    if(lexeme.equals("digraph"))
                    {
                        Token Current_token = new Token(lexeme, types.type.get("digraph"), result.get(index).getLine());
                        tokens.add(Current_token);    
                    }
                    else if(lexeme.equals("graph"))
                    {
                        Token Current_token = new Token(lexeme, types.type.get("graph"), result.get(index).getLine());
                        tokens.add(Current_token);
                    }
                    else if(lexeme.equals("node"))
                    {
                        Token Current_token = new Token(lexeme, types.type.get("node"), result.get(index).getLine());
                        tokens.add(Current_token);
                    }
                    else
                    {
                    Token Current_token = new Token(lexeme, types.type.get("ID"), result.get(index).getLine());
                    tokens.add(Current_token);
                    }

            } else {
                consume();
            }

        }


        return tokens;
    }

    public void consume() {
        
        if (index >= result.size()-1) {
            index_character = "EOF";
            
        } else {
            index += 1;
            index_character = result.get(index).getCharater();
        }

    }
}
