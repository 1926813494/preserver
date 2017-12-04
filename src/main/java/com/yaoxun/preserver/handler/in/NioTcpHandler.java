package com.yaoxun.preserver.handler.in;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yaoxun.preserver.business.service.Command;
import com.yaoxun.preserver.entity.Req;
import com.yaoxun.preserver.entity.Resp;
import com.yaoxun.preserver.handler.manager.HandlerContextManger;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 *  channel 事件方法类
 * @author Loren
 * @createTime 2017年11月29日 下午2:11:46
 */
@Component("nioTcpHandler")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Sharable
public class NioTcpHandler extends ChannelInboundHandlerAdapter implements ApplicationContextAware {

	private static final Logger LOG = LoggerFactory.getLogger(NioTcpHandler.class);
		
	private ApplicationContext ac;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LOG.debug("channel id : {} , hashCode: {}", ctx.channel().id(), ctx.hashCode());
		//可以使用HandlerContextManger维护ChannelHandlerContext
		HandlerContextManger.put(ctx.channel().id().asLongText(), ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		LOG.debug("输出内容为:{}", msg);
		Req req = (Req) msg;
		Command command = (Command) ac.getBean(req.getFunCode().name());
		boolean execute = command.execute(req);
		Resp resp = command.sendAck(execute, req);
		if(resp != null) {
			ctx.writeAndFlush(resp);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		LOG.error(cause.getMessage(), cause);
		HandlerContextManger.delete(ctx);
		ctx.close();
	}

	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ac = applicationContext;
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(evt instanceof IdleStateEvent) {
			IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
			if(idleStateEvent.state() == IdleState.READER_IDLE) {
				//TODO 处理闲置读事件
			} else if(idleStateEvent.state() == IdleState.WRITER_IDLE) {
				//TODO 处理闲置写事件
			} else {
				//TODO 处理同时没有读写事件
//				LOG.debug("空闲读写事件");
				LOG.debug("在线连接数:{}", HandlerContextManger.size());
			}
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		LOG.debug("客户端断开连接");
		HandlerContextManger.delete(ctx);
	}

}
