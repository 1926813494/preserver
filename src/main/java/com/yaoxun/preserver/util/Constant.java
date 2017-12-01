package com.yaoxun.preserver.util;

public class Constant {
	
	/**
	 * 命令中开始码
	 */
	public static final byte START_CODE = (byte) 0xAA;
	
	/**
	 * 默认的校验码
	 */
	public static final byte DEFAULT_VERIFY_CODE = (byte) 0xEF;
	
	/**
	 * 命令中结束码
	 */
	public static final byte END_CODE = 0x55;
	
	/**
	 * 返回成功
	 */
	public static final short SUCCESS = 0x0000;
	
	/**
	 * 返回错误
	 */
	public static final short FAIL = 0x0001;
	
	/**
	 * 编程地址错误
	 */
	public static final short ADDRESS_ERROR = (short) 0x8001;
	
}
