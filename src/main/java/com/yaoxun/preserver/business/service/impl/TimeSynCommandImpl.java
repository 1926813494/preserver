package com.yaoxun.preserver.business.service.impl;

import org.springframework.stereotype.Service;

import com.yaoxun.preserver.business.service.Command;
import com.yaoxun.preserver.entity.Req;
import com.yaoxun.preserver.entity.Resp;
import com.yaoxun.preserver.entity.time.TimeSyn;
import com.yaoxun.preserver.util.RespUtils;

@Service("TIME_SYN")
public class TimeSynCommandImpl implements Command {

	@Override
	public boolean execute(Req req) {
		TimeSyn body = (TimeSyn) req.getBody();
		if(body != null) {
			System.out.println(body);
			return true;
		}
		return false;
	}

	@Override
	public Resp sendAck(boolean execute, Req req) {
		return RespUtils.sendAck(execute, req);
	}

}
