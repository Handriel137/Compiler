/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Ryan.Solnik
 */
public class Tokenizer {

    
    public Tokenizer() {
        
    }

    public List<LinedCharacter> tokenize(String input) {

        String filename = input;
        String line;
        List<LinedCharacter> result = new ArrayList<LinedCharacter>();

        try {
            String outputFile = "myTempFile.txt";
            FileReader filereader = new FileReader(filename);
            
            
            BufferedReader bufferedReader = new BufferedReader(filereader);
            BufferedWriter writer = new BufferedWriter(new FileWriter("myTempFile.txt"));
            
            String lineToRemove = "[a]+";
            String currentLine;
            String newLine;
            int lineNumber =0;
            
            while((currentLine = bufferedReader.readLine()) != null)
            {
                //trim newline when comparing with line ToRemove
                String trimmedLine = currentLine.trim();
                newLine = trimmedLine.replaceAll( "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/", "$1 " );
                writer.write(newLine+"\n");
            }
            writer.close();
            
            //opens and tokenizes the modified file without comments
            BufferedReader modifiedreader = new BufferedReader(new FileReader(outputFile));
            
            while ((line = modifiedreader.readLine()) != null) {
                               
                int counter =0;
                
                for (Character c : line.toCharArray()) {
                    counter++;
                    
                    
                    result.add(new LinedCharacter(c.toString(), lineNumber));
                
                    if(counter == line.length()){
                        lineNumber++;
                        counter =0;
                    }
                
                }

            }
                
        }
        
        catch (Exception e)
        {
            System.out.println("no workie");
        }
        return result;
    }

}
