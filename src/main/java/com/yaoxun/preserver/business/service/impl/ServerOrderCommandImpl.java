package com.yaoxun.preserver.business.service.impl;

import org.springframework.stereotype.Service;

import com.yaoxun.preserver.business.service.Command;
import com.yaoxun.preserver.entity.Req;
import com.yaoxun.preserver.entity.Resp;
import com.yaoxun.preserver.util.RespUtils;

/**
 * 
 * 
 * @author Loren
 * @createTime 2017年11月30日 下午3:10:01
 */
@Service("SERVER_ORDER")
public class ServerOrderCommandImpl implements Command {

	@Override
	public boolean execute(Req req) {
		Object body = req.getBody();
		System.out.println(body);
		return true;
	}

	@Override
	public Resp sendAck(boolean execute, Req req) {
		return RespUtils.sendAck(execute, req);
	}
	
}
