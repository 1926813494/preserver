package com.yaoxun.preserver.util.test;

import org.junit.Test;

import com.yaoxun.preserver.util.ByteUtils;
import com.yaoxun.preserver.util.Constant;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class TestByteUtils {

	@Test
	public void testShortToHilo() {
		short value = 85;
		byte[] hilo = ByteUtils.shortToHilo(value);
		System.out.println(ByteUtils.byteToHex(hilo));
	}
	
	@Test
	public void testPrettyDump() {
		String value = "hello";
		ByteBuf buf = Unpooled.copiedBuffer(value.getBytes());
		System.out.println(ByteUtils.prettyDump(buf));
	}
	
	@Test
	public void testByteToHex() {
		System.out.println(Constant.START_CODE);
	}
	
	@Test
	public void testReverse() {
		String value = "1145";
		long start = System.currentTimeMillis();
//		ArrayUtils.reverse(value.getBytes());
		ByteUtils.reverse(value.getBytes());
		long end = System.currentTimeMillis();
		System.out.println("耗时：" + (end - start));
	}
	
}
