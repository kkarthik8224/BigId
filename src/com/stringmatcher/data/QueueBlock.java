package com.stringmatcher.data;

import java.util.concurrent.LinkedBlockingQueue;

public class QueueBlock {
	
	private LinkedBlockingQueue<DataChunk> MatcherQueue;
	  
	public QueueBlock(int capacity) {
	    this.MatcherQueue = new LinkedBlockingQueue<>(capacity);
	  }

	public LinkedBlockingQueue<DataChunk> getMatcherQueue() {
		return MatcherQueue;
	}
	
	

}
