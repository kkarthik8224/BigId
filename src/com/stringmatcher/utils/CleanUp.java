package com.stringmatcher.utils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class CleanUp {

	
	  public static void start(List<ExecutorService> executorServices) {
		  	
			Log.logInfo("Executor Cleanup");
		    for (ExecutorService executorService : executorServices) {
		      executorService.shutdown();
		      try {
		        if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
		          executorService.shutdownNow();
		        }
		      } catch (InterruptedException e) {
		        executorService.shutdownNow();
		      }
		    }
		  }
}
