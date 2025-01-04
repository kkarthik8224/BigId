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
//                		int adjustedOffset = (int) CharOffset;  
//                // Remove New Line characters
////                for (int i = 0; i <CharOffset ; i++) {
////                	if (text.charAt(i) != '\n' && text.charAt(i) != '\r') {
////                		adjustedOffset++;
////                    }
////                }
                long lineNumber = batchSize * batchNumber;
                results.computeIfAbsent(name, _ -> new ArrayList<>())
                       .add(new Location(lineNumber, CharOffset ));
            }
        }

        return results;
    }
    

}
