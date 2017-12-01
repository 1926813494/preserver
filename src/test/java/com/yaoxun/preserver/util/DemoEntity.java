package com.yaoxun.preserver.util;

import com.yaoxun.preserver.annotation.codec.Deco;
import com.yaoxun.preserver.annotation.codec.Enco;

public class DemoEntity {

	@Deco
	@Enco
	private byte start;
	
	@Deco(hilo=true)
	@Enco(hilo=true)
	private short funCode; 
	
	@Deco(hilo=true)
	@Enco(hilo=true)
	private short frameLen;
	
	@Deco(hilo=true, length=2)
	@Enco(hilo=true)
	private String stationNum;
	
	@Deco(length=16)
	@Enco
	private byte[] body;
	
	@Deco
	@Enco
	private byte verifyCode;
	
	@Deco
	@Enco
	private byte endCode;
	
	public DemoEntity() {
		super();
	}

	public DemoEntity(byte start, short funCode, short frameLen, String stationNum, byte[] body, byte verifyCode,
			byte endCode) {
		super();
		this.start = start;
		this.funCode = funCode;
		this.frameLen = frameLen;
		this.stationNum = stationNum;
		this.body = body;
		this.verifyCode = verifyCode;
		this.endCode = endCode;
	}

	public byte getStart() {
		return start;
	}

	public void setStart(byte start) {
		this.start = start;
	}

	public short getFunCode() {
		return funCode;
	}

	public void setFunCode(short funCode) {
		this.funCode = funCode;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public byte getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(byte verifyCode) {
		this.verifyCode = verifyCode;
	}

	public byte getEndCode() {
		return endCode;
	}

	public void setEndCode(byte endCode) {
		this.endCode = endCode;
	}

	public short getFrameLen() {
		return frameLen;
	}

	public void setFrameLen(short frameLen) {
		this.frameLen = frameLen;
	}

	public String getStationNum() {
		return stationNum;
	}

	public void setStationNum(String stationNum) {
		this.stationNum = stationNum;
	}

	@Override
	public String toString() {
		return "DemoEntity [start=" + start + ", funCode=" + funCode + ", frameLen=" + frameLen + ", stationNum="
				+ stationNum + ", body=" + ByteUtils.byteToHex(body) + ", verifyCode=" + verifyCode + ", endCode=" + endCode
				+ "]";
	}

	
	
}
