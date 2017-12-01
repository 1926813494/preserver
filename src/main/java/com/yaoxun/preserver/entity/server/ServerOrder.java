package com.yaoxun.preserver.entity.server;

/**
 * 允许服务器命令
 * 
 * @author Loren
 * @createTime 2017年11月30日 下午4:46:25
 */
public class ServerOrder {

	private String staionNum;
	
	private String version;

	public String getStaionNum() {
		return staionNum;
	}

	public void setStaionNum(String staionNum) {
		this.staionNum = staionNum;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "ServerOrder [staionNum=" + staionNum + ", version=" + version + "]";
	}
	
}
