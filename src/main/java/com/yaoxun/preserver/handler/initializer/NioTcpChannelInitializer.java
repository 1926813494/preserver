package com.yaoxun.preserver.handler.initializer;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.yaoxun.preserver.codec.decoder.CommandDecoder;
import com.yaoxun.preserver.codec.encoder.CommandEncoder;
import com.yaoxun.preserver.entity.Config;
import com.yaoxun.preserver.handler.in.NioTcpHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 用于初始化channel pipeline
 * 
 * @author Loren
 * @createTime 2017年11月29日 上午11:49:52
 */
public class NioTcpChannelInitializer 
								extends ChannelInitializer<SocketChannel> 
									implements ApplicationContextAware {
	
	private ApplicationContext ac;
	
	@Resource
	private Config config;
	
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		//添加心跳检测handler
		pipeline.addLast("idleStateHandler", 
				new IdleStateHandler(config.nettyReadIdle, config.nettyWriteIdle, config.nettyAllIdle));
		//解码器
		pipeline.addLast("commandDecoder", new CommandDecoder());
		//编码器
		pipeline.addLast("commandEncoder", new CommandEncoder());
		//添加自定义的handler
		pipeline.addLast("nioTcpHandler", ac.getBean(NioTcpHandler.class));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ac = applicationContext;
	}

}
