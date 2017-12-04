package com.yaoxun.preserver.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {

	@Value("${netty.readIdle}")
	public int nettyReadIdle;
	
	@Value("${netty.writeIdle}")
	public int nettyWriteIdle;
	
	@Value("${netty.allIdle}")
	public int nettyAllIdle;
	
}
