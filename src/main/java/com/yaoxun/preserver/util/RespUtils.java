package com.yaoxun.preserver.util;

import com.yaoxun.preserver.entity.Req;
import com.yaoxun.preserver.entity.Resp;
import com.yaoxun.preserver.util.enume.FunCode;

/**
 * 响应工具类
 * 
 * @author Loren
 * @createTime 2017年11月30日 下午4:15:38
 */
public class RespUtils {

	/**
	 * 返回正确的响应
	 * @author Loren
	 * @createTime 2017年11月30日 下午4:05:57
	 * @return
	 */
	public static Resp success(FunCode funCode) {
		byte[] body = ByteUtils.shortToHilo(Constant.SUCCESS);
		Resp resp = new Resp(funCode, body);
		return resp;
	}
	
	/**
	 * 返回失败的响应
	 * @author Loren
	 * @createTime 2017年11月30日 下午4:08:42
	 * @param funCode
	 * @return
	 */
	public static Resp fail(FunCode funCode) {
		byte[] body = ByteUtils.shortToHilo(Constant.FAIL);
		Resp resp = new Resp(funCode, body);
		return resp;		
	}
	
	/**
	 * 返回成功响应或失败响应
	 * @author Loren
	 * @createTime 2017年11月30日 下午4:10:20
	 * @param execute
	 * @param req
	 * @return
	 */
	public static Resp sendAck(boolean execute, Req req) {
		if(execute) {
			return success(req.getFunCode());
		}else {
			return fail(req.getFunCode());
		}
	}
	
	
}
