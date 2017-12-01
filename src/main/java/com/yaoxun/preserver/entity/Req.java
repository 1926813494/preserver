package com.yaoxun.preserver.entity;

import com.yaoxun.preserver.util.enume.FunCode;

/**
 * 解码后返回数据实体
 * @author Loren
 * @createTime 2017年11月30日 上午9:51:14
 */
public class Req {

	private FunCode funCode; //功能码
	
	private Object body; //转换成对象 , 帧长度字段与校验字段（不包括帧长度和校验）之间的数据，注意C语言中的高低位转换
	
	private byte verfiyCode; //校验码
	
	public Req() {
		super();
	}

	public Req(FunCode funCode, byte verfiyCode, Object body) {
		super();
		this.funCode = funCode;
		this.verfiyCode = verfiyCode;
		this.body = body;
	}

	public FunCode getFunCode() {
		return funCode;
	}

	public void setFunCode(FunCode funCode) {
		this.funCode = funCode;
	}

	public byte getVerfiyCode() {
		return verfiyCode;
	}

	public void setVerfiyCode(byte verfiyCode) {
		this.verfiyCode = verfiyCode;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Req [funCode=" + funCode + ", verfiyCode=" + verfiyCode + ", body=" + body + "]";
	}
	
}
