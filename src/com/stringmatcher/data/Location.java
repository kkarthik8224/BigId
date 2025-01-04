package com.stringmatcher.data;

public class Location {
	
	private final long lineOffset;
    private final long charOffset;

    public Location(long lineOffset, long charOffset) {
        this.lineOffset = lineOffset;
        this.charOffset = charOffset;
    }

	public long getCharOffset() {
		return charOffset;
	}

	public long getLineOffset() {
		return lineOffset;
	}
	
    @Override
    public String toString() {
        return "[lineOffset=" + lineOffset + ", charOffset=" + charOffset + "]";
    }

}
