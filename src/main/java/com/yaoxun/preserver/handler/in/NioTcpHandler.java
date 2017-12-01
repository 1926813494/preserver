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

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
		LOG.debug("channelActive");
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
		ctx.close();
	}

	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ac = applicationContext;
	}
	
}
