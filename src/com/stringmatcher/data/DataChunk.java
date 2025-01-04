package com.stringmatcher.data;

public class DataChunk {
	
	private final String text;       // The text content of the data chunk
    private final long lineOffset;    // The starting line number of this chunk
    private final long charOffset;    // The starting character offset of this chunk
    private final long batchNumber;

    public DataChunk(String text, long lineOffset , long charOffset, long batchNumber ) {
        this.text = text;
        this.lineOffset = lineOffset ;
        this.charOffset = charOffset;
        this.batchNumber = batchNumber;
    }

	public long getCharOffset() {
		return charOffset;  //Can be used to store character offset of a chunk
	}

	public long getLineOffset() {
		return lineOffset;
	}

	public String getText() {
		return text;
	}
	
	public long getBatchNumber() {
		return batchNumber;
	}

	public static final DataChunk POISON_PILL = new DataChunk("POISON_PILL", -1, -1,-1);

}
