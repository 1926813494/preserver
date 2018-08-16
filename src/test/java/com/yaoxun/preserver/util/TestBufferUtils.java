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
		long start = System.currentTimeMillis();
		byte[] bytes = ByteUtils.hexToBytes("aa00831200c8C000000000000000000000000000000000EF55");
		for(int i=1; i<=10000; i++) {
			ByteBuf buf = Unpooled.copiedBuffer(bytes);
			DemoEntity demoEntity = BufferUtils.parse(buf, DemoEntity.class);
			buf.clear();
//			System.out.println(buf.readableBytes());
		}
		long end = System.currentTimeMillis();
		System.out.println("耗时：" + (end - start));
//		System.out.println(demoEntity);
	}
	
}
