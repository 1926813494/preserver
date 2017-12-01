package com.yaoxun.preserver.entity.time;

import java.util.Arrays;

public class TimeSynAck {

	private String stationNum;
	
	private byte[] data;

	public String getStationNum() {
		return stationNum;
	}

	public void setStationNum(String stationNum) {
		this.stationNum = stationNum;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "TimeSynAck [stationNum=" + stationNum + ", data=" + Arrays.toString(data) + "]";
	} 
}
