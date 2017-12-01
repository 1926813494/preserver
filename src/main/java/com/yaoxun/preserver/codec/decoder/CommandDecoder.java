package com.yaoxun.preserver.codec.decoder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yaoxun.preserver.entity.Req;
import com.yaoxun.preserver.util.BufferUtils;
import com.yaoxun.preserver.util.ByteUtils;
import com.yaoxun.preserver.util.Constant;
import com.yaoxun.preserver.util.enume.FunCode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

/**
 * 命令解码器
 * @author Loren
 * @createTime 2017年11月30日 上午9:54:03
 */
public class CommandDecoder extends ByteToMessageDecoder {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommandDecoder.class);
	
	/*最大字节数阈值*/
	private int maxFrameSize = 1035;
	
	private int headerSize = 5; //开始码 + 功能码 + 帧长度 的字节长度
	
	private int endSize = 2; //校验码 + 结束码 的字节长度
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int readable = in.readableBytes();
		if(readable > maxFrameSize) {
			in.skipBytes(readable); //忽略所有可读字节
			throw new TooLongFrameException("Frame too big");
		}
		if(readable < headerSize) {
			return ; //不足字节不做处理
		}
		byte startCode = in.readByte(); //开始码
		if(startCode == Constant.START_CODE) {
			byte[] fcBytes = new byte[2];
			in.readBytes(fcBytes);
			FunCode funCode = FunCode.getFunCode(fcBytes);
			if(funCode != null) {
				//读取帧长度
				byte[] flBytes = new byte[2]; 
				in.readBytes(flBytes);
				short frameLen = ByteUtils.hiloToShort(flBytes);
				int endLen = frameLen + endSize;
				if(in.readableBytes() < endLen) {
					return ; //不足字节不做处理
				}
				ByteBuf body = in.readBytes(frameLen); //读取出数据体
				byte verifyCode = in.readByte();
				byte endCode = in.readByte();
				if(endCode == Constant.END_CODE) {
					Req req = new Req(funCode, verifyCode, readObject(funCode, body));
					if(body.readableBytes() > 0) {						
						body.clear(); //如果没有读取完，清空
					}
					out.add(req);
				}else {
					errorEndCode(ctx, in, endCode);					
				}
			} else {
				errorFuncode(ctx, in, fcBytes);
			}
		}else {
			errorStartCode(ctx, in, startCode);
		}
	}

	private Object readObject(FunCode funCode, ByteBuf body) {
		Class<?> clazz = funCode.getClazz();
		Object object = BufferUtils.parse(body, clazz);
		return object;
	}
	
	
	private void errorEndCode(ChannelHandlerContext ctx, ByteBuf in, byte b) {
		release(ctx, in);
		LOG.error("结束码错误：{}", ByteUtils.byteToHex(b));
	}
	
	private void errorFuncode(ChannelHandlerContext ctx, ByteBuf in, byte[] dst) {
		release(ctx, in);
		LOG.error("功能码错误：{}", ByteUtils.byteToHex(dst, true));
	}
	
	private void errorStartCode(ChannelHandlerContext ctx, ByteBuf in, byte b) {
		release(ctx, in);
		LOG.error("开始码错误：{}", ByteUtils.byteToHex(b));
	}
	
	private void release(ChannelHandlerContext ctx, ByteBuf in) {
		in.clear();
		ctx.close();
	}
	
}
