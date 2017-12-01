package com.yaoxun.preserver.entity;

import com.yaoxun.preserver.util.Constant;
import com.yaoxun.preserver.util.enume.FunCode;

/**
 * 响应实体
 * 
 * @author Loren
 * @createTime 2017年11月30日 下午12:03:48
 */
public class Resp {
	
	/*功能码*/
	private FunCode funCode;
		
	/*数据体，帧长度字段与校验字段（不包括帧长度和校验）之间的数据，注意C语言中的高低位转换*/
	private Object body;
	
	/*校验码*/
	private byte verifyCode = Constant.DEFAULT_VERIFY_CODE;

	
	public Resp(FunCode funCode, Object body) {
		super();
		this.funCode = funCode;
		this.body = body;
	}

	public Resp() {
		super();
	}
	
	public Resp(FunCode funCode, Object body, byte verifyCode) {
		super();
		this.funCode = funCode;
		this.body = body;
		this.verifyCode = verifyCode;
	}

	public FunCode getFunCode() {
		return funCode;
	}

	public void setFunCode(FunCode funCode) {
		this.funCode = funCode;
	}


	/**
	 * 不填写校验码，默认值{@link Constant#DEFAULT_VERIFY_CODE}
	 * @author Loren
	 * @createTime 2017年11月30日 下午2:14:51
	 * @return
	 */
	public byte getVerifyCode() {
		return verifyCode;
	}
	
	/**
	 * 默认值{@link Constant#DEFAULT_VERIFY_CODE}
	 * @author Loren
	 * @createTime 2017年11月30日 下午2:13:56
	 * @param verifyCode
	 */
	public void setVerifyCode(byte verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Resp [funCode=" + funCode + ", body=" + body + ", verifyCode=" + verifyCode + "]";
	}
	
}
