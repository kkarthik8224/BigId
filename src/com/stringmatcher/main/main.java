package com.stringmatcher.main;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.stringmatcher.aggregator.Aggregator;
import com.stringmatcher.data.DataChunk;
import com.stringmatcher.data.Location;
import com.stringmatcher.data.QueueBlock;
import com.stringmatcher.pipeline.Matcher;
import com.stringmatcher.pipeline.Reader;
import com.stringmatcher.utils.CleanUp;
import com.stringmatcher.utils.Log;

public class main {

  public static void main(String[] args) throws InterruptedException, IOException {

    // TODO Auto-generated method stub
    Log.logInfo("Execution Starts");
    FileReader reader = new FileReader("config/config.properties"); 
    
    Properties properties = new Properties(); 
    properties.load(reader); 
       
    String filePath = properties.getProperty("FilePath");//"resources/big.txt";
    int batchSize =  Integer.parseInt( properties.getProperty("batchSize"));   //1000;
    List < String > NAMES = Arrays.asList(
    		properties.getProperty("Names").split(",")
    );

    ExecutorService readExecutor = Executors.newSingleThreadExecutor();
    ExecutorService matchExecutor = Executors.newFixedThreadPool(8);
    QueueBlock queueBlock = new QueueBlock(10);

    Log.logInfo("Intialisation Done");
    //Initiate Read thread
    Log.logInfo("Initiating Reader Thread");
    readExecutor.submit(() -> {
      try {
        new Reader().read(filePath, batchSize, queueBlock);
      } catch (Exception e) {
        e.printStackTrace();
        System.exit(1);
      }
    });

    //Initiate Match Threads
    Log.logInfo("Initiating Matcher Threads");
    List < Future < Map < String, List < Location >>> > matcherFutures = new ArrayList < > ();
    while (true) {
      DataChunk chunk;
      try {
        chunk = queueBlock.getMatcherQueue().take();
        if (chunk == DataChunk.POISON_PILL) {
          break;

        }
        matcherFutures.add(matchExecutor.submit(() -> new Matcher(NAMES).findMatches(
          chunk.getText(),
          chunk.getLineOffset(),
          chunk.getCharOffset(),
          chunk.getBatchNumber(),
          batchSize
        )));

      } catch (Exception e) {
        // TODO Auto-generated catch block
        Log.logSevere("Error While Executing Matchers " + e.getMessage());
        CleanUp.start(List.of(readExecutor, matchExecutor));
        System.exit(1);
      }

    }

    CleanUp.start(List.of(readExecutor, matchExecutor));

    Log.logInfo("All Threads Terminated");
    Log.logInfo("Printing Results ...");

    new Aggregator(matcherFutures).printResults(NAMES);


  }

}