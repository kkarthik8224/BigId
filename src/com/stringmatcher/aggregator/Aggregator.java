package com.stringmatcher.aggregator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import com.stringmatcher.data.Location;


public class Aggregator {
    private final Map<String, List<Location>> aggregatedResults = new HashMap<>();
     
    public Aggregator(List < Future < Map < String, List < Location >>> > futures)
    {
    	for (Future < Map < String, List < Location >>> future:futures ) {
    		      try {
    		        aggregate(future.get());
    		      } catch (Exception e) {
    		        e.printStackTrace();
    		      }
    		    }
    }
    
    public void aggregate(Map<String, List<Location>> results) {
        for (Map.Entry<String, List<Location>> entry : results.entrySet()) {
            this.aggregatedResults.computeIfAbsent(entry.getKey(), _ -> new ArrayList<>())
                             .addAll(entry.getValue());
        }
    }

    public void printResults(List<String> names) {
    	Set <String> nameset = new HashSet<String>(names);
    	System.out.println("Total Mathes Number: "+ aggregatedResults.keySet().size());
    	System.out.println("MATCHES ________________________________");
        for (Map.Entry<String, List<Location>> entry : aggregatedResults.entrySet()) {
            System.out.println(entry.getKey() + " --> " + entry.getValue());
        }
    	System.out.println("Non Matches ______________________________");
    	System.out.println("Total NonMathes Number: "+ (nameset.size()- aggregatedResults.keySet().size()));
    	nameset.removeAll(aggregatedResults.keySet());
    	System.out.println("Non Matching Names : "+nameset);
     
    }
}