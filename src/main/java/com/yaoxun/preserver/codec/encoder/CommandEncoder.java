package com.yaoxun.preserver.codec.encoder;

import com.yaoxun.preserver.entity.Resp;
import com.yaoxun.preserver.util.BufferUtils;
import com.yaoxun.preserver.util.Constant;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 命令编码器
 * 
 * @author Loren
 * @createTime 2017年11月30日 上午11:59:27
 */
public class CommandEncoder extends MessageToByteEncoder<Resp> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Resp msg, ByteBuf out) throws Exception {
		out.writeByte(Constant.START_CODE); //开始码
		out.writeShortLE(msg.getFunCode().value()); //功能码
		writeBody(msg, out); //输出帧长度和数据体
		out.writeByte(msg.getVerifyCode()); //校验码
		out.writeByte(Constant.END_CODE); //结束码
	}
	
	/*输出帧长度和数据体*/
	private void writeBody(Resp msg, ByteBuf out) {
		Object body = msg.getBody();
		if(body.getClass().getSimpleName().equals("byte[]")) {
			byte[] dst = (byte[]) body;
			out.writeShortLE(dst.length); //先输出帧长度
			out.writeBytes(dst);
		}else {
			//转换成
			ByteBuf buf = BufferUtils.toBuf(body);
			out.writeShortLE(buf.readableBytes()); //输出帧长度
			out.writeBytes(buf);
		}
	}
	
}

