package com.stringmatcher.pipeline;

import java.io.BufferedReader;
import java.io.FileReader;

import com.stringmatcher.data.DataChunk;
import com.stringmatcher.data.QueueBlock;
import com.stringmatcher.utils.Log;

public class Reader {
	
	public void read(String filePath, int batchSize, QueueBlock queueBlock) throws Exception
	{
		
		 try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
             String line;
             long lineOffset = 0;
             long charOffset = 0;
             StringBuilder batch = new StringBuilder();
             int lineCount = 0;
             int batchNumber = 0;
             

             while ((line = reader.readLine()) != null) {
            	 
                 batch.append(line).append("\n");
                 lineCount++;
                 charOffset += line.length() + 1; //store including \n

                 if (lineCount == batchSize) {
                	 
                	 queueBlock.getMatcherQueue().put(new DataChunk(batch.toString(), lineOffset, (long)charOffset - batch.length(), batchNumber));
                     batch.setLength(0);
                     lineOffset += (long) batchSize;
                     lineCount = 0;
                     
                     batchNumber++;
                    
                 }
                 
             }

             if (batch.length() > 0) {
            	 batchNumber++;
            	 Log.logInfo("Total Batches :" + batchNumber);
            	 queueBlock.getMatcherQueue().put(new DataChunk(batch.toString(), lineOffset, (long)charOffset - batch.length(), batchNumber));
             }

             queueBlock.getMatcherQueue().put(DataChunk.POISON_PILL);
            
		}
		
		catch(Exception e)
		{
			Log.logSevere("Exception Occured at Reader " + e.getMessage());	
			throw e;
		}
		
		
	}

}
