
package lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Token {
    
    String tokenType;
    String tokenValue;
    int lineNumber;
    
    public Token(String value, String type, int lineNumber) {
        this.tokenType = type; 
        this.tokenValue = value;  
        this.lineNumber = lineNumber;
    }
    
    @Override
  public String toString()
  {
      return "<"+tokenValue +": " + tokenType+ " @Line " + lineNumber + ">"; 
  }
   
  public String getValue()
  {
      
      return tokenValue;
  }
  
  public String getType()
  {
      
      return tokenType;
  }
}