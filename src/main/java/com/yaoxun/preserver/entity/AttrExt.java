package com.yaoxun.preserver.entity;

public class AttrExt {

	private String uniqueKey;
	
	private long timestamp;
	
	public AttrExt() {
		super();
	}

	public AttrExt(String uniqueKey, long timestamp) {
		super();
		this.uniqueKey = uniqueKey;
		this.timestamp = timestamp;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "AttrExt [uniqueKey=" + uniqueKey + ", timestamp=" + timestamp + "]";
	}
}
