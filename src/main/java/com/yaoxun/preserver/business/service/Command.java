package com.yaoxun.preserver.business.service;

import com.yaoxun.preserver.entity.Req;
import com.yaoxun.preserver.entity.Resp;

public interface Command {

	boolean execute(Req req);
	
	Resp sendAck(boolean execute, Req req);
}
