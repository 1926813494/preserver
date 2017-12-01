package com.yaoxun.preserver.entity.time;

import com.yaoxun.preserver.annotation.codec.Deco;

public class TimeSyn {

	@Deco(hilo=true, length=2)
	private String stationNum;

	public String getStationNum() {
		return stationNum;
	}

	public void setStationNum(String stationNum) {
		this.stationNum = stationNum;
	}

	@Override
	public String toString() {
		return "TimeSyn [stationNum=" + stationNum + "]";
	}
		
}
