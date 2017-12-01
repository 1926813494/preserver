package com.yaoxun.preserver.util;

import org.junit.Test;

import com.yaoxun.preserver.util.enume.FunCode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class TestBufferUtils {

	@Test
	public void testToByte() {
		byte[] body = new byte[10];
		short frameLen = 12;
		DemoEntity demoEntity = 
				new DemoEntity(Constant.START_CODE, 
						FunCode.TIME_SYN.value(), frameLen, "BFC8", body,
						Constant.DEFAULT_VERIFY_CODE, Constant.END_CODE);
		byte[] bytes = BufferUtils.toBytes(demoEntity);
		System.out.println(bytes.length);
		System.out.println(ByteUtils.byteToHex(bytes));
	}
	
	@Test
	public void testParse() {
		byte[] bytes = ByteUtils.hexToBytes("aa00831200c8C000000000000000000000000000000000EF55");
		ByteBuf buf = Unpooled.copiedBuffer(bytes);
		long start = System.currentTimeMillis();
		DemoEntity demoEntity = BufferUtils.parse(buf, DemoEntity.class);
		long end = System.currentTimeMillis();
		System.out.println("耗时：" + (end - start));
		System.out.println(demoEntity);
		buf.clear();
		System.out.println(buf.readableBytes());
	}
	
}
