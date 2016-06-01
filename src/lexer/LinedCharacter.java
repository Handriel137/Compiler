/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

/**
 *
 * @author Ryan.Solnik
 */
public class LinedCharacter {
    
    String character;
    int lineNumber;
    
    LinedCharacter(String character, int lineNumber){
       this.character=character;
       this.lineNumber=lineNumber;
    }
    
    public String getCharater(){
            
        try{
        return character;
        }
        catch(Exception e){
            return "EOF";
        }
    }
    public int getLine(){
        return lineNumber;
    }
}
