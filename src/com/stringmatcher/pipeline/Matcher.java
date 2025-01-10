package com.stringmatcher.pipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stringmatcher.data.Location;
import java.util.regex.Pattern;

public class Matcher {

	private final List<String> names;

    public Matcher(List<String> names) {
        this.names = names;
    }

    public Map<String, List<Location>> findMatches(String text, long lineOffset, long charOffset, long batchNumber, long batchSize) {
        Map<String, List<Location>> results = new HashMap<>();
        for (String name : names) {
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(name) + "\\b");
            java.util.regex.Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                long CharOffset =  matcher.start() - text.substring(0, matcher.start()).split("\n").length; 

               long lineNumber = lineOffset + text.substring(0, matcher.start()).split("\n").length;
               String [] paragraph = text.split("\n");
               String [] line = paragraph[text.substring(0, matcher.start()).split("\n").length-1].split(" ");
               int charCount = 0 ;
               boolean notFound = true;
               for (String word : line)
               {
            	   if (word.equals(name))
            	   {
            		   notFound =false;
            		  break;
            	   }
            	   charCount +=word.length();;
               }
               if (notFound) { lineNumber ++; charCount=0;}
            	// THe same logic can be done in Reader File /producer also
               	// If the changes has to be done there, store the text as array of strings directly there and read it here
               // Updated output can be found in output2.txt file
           
                results.computeIfAbsent(name, _ -> new ArrayList<>())
                       .add(new Location(lineNumber, (long)charCount ));
            }
        }

        return results;
    }
    

}
