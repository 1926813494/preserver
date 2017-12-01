package com.yaoxun.preserver.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

/**
 * 字节工具类
 * @author Loren
 * @createTime 2017年11月29日 下午4:13:37
 */
public class ByteUtils {
	
	public static final String HEX_STR = "0123456789ABCDEF";
	
	/**
	 * 将16进制转换成字节数组
	 * @author Loren
	 * @createTime 2017年11月29日 下午6:27:00
	 * @param hex
	 * @return
	 */
	public static byte[] hexToBytes(String hex, boolean...hilo) {
		try {
			byte[] bytes = Hex.decodeHex(hex.toCharArray());
			if(hilo != null && hilo.length > 0 && hilo[0] == true) {
				return reverse(bytes);
			}else {
				return bytes;
			}
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将short转换成高低位字节数组
	 * @author Loren
	 * @createTime 2017年11月30日 下午3:49:40
	 * @param value
	 * @return
	 */
	public static byte[] shortToHilo(short value) {
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.putShort(value);
		byte[] array = buffer.array();
		return reverse(array);
	}
	
	/**
	 * 将int 转换成高低位字节数组
	 * @author Loren
	 * @createTime 2017年11月30日 下午4:01:34
	 * @param value
	 * @return
	 */
	public static byte[] intToHilo(int value) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(value);
		byte[] array = buffer.array();
		return reverse(array);
	}
	
	/**
	 * 将高低位形式字节数组（length=2），转换成short
	 * @author Loren
	 * @createTime 2017年11月30日 上午9:07:40
	 * @param dst
	 * @return
	 */
	public static short hiloToShort(byte[] dst) {
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.put(dst);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.flip();
		return buffer.getShort();
	}
	
	/**
	 * 将高低位形式字节数组（length=4），转换成int
	 * @author Loren
	 * @createTime 2017年11月30日 上午9:11:50
	 * @param dst
	 * @return
	 */
	public static int hiloToInt(byte[] dst) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.put(dst);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.flip();
		return buffer.getInt();
	}
	
	/**
	 * 将一个字节输出成16进制
	 * @author Loren
	 * @createTime 2017年11月29日 下午6:24:40
	 * @param dst
	 * @return
	 */
	public static String byteToHex(byte dst) {
		String hex = String.valueOf(HEX_STR.charAt((dst & 0xF0) >> 4));
		hex += String.valueOf(HEX_STR.charAt(dst & 0x0F));
		return hex;
	}
	
	/**
	 * 将一个字节的16进制转换成byte
	 * @author Loren
	 * @createTime 2017年11月29日 下午6:22:55
	 * @param hex
	 * @return
	 */
	public static byte hexToByte(String hex) {
		byte b = Integer.valueOf(hex, 16).byteValue();
		return b;
	}
	
	/**
	 * 将字节输出成大写的16进制字符串
	 * @author Loren
	 * @createTime 2017年11月29日 下午4:33:53
	 * @param dst
	 * @param hilo 是否需要转换成高低位
	 * @return
	 */
	public static String byteToHex(byte[] dst, boolean... hilo) {
		if(hilo != null && hilo.length > 0 && hilo[0] == true) {
			dst = reverse(dst);
		}
		char[] encodeHex = Hex.encodeHex(dst, false);
		return new String(encodeHex);
	}
	
	/**
	 * 反转数组
	 * @author Loren
	 * @createTime 2017年11月29日 下午5:33:49
	 * @param dst
	 * @return
	 */
	public static byte[] reverse(byte[] dst) {
		byte[] src = new byte[dst.length];
		for(int i=0; i<dst.length; i++) {
			src[i] = dst[dst.length - i - 1];
		}
		return src;
	}
	
	
	/**
	 * 打印出格式化大写16进制
	 * @author Loren
	 * @createTime 2017年11月29日 下午5:08:18
	 * @param dst
	 * @return
	 */
	public static String prettyDump(byte[] dst) {
		StringBuilder sb = new StringBuilder();
		for (byte b : dst) {
			sb.append(String.valueOf(HEX_STR.charAt((b & 0xF0) >> 4)));
			sb.append(String.valueOf(HEX_STR.charAt(b & 0x0F)));
			sb.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 将netty bytebuf 以格式化的形式打印
	 * @author Loren
	 * @createTime 2017年11月29日 下午4:59:26
	 * @param buf
	 * @return
	 */
	public static String prettyDump(ByteBuf buf) {
		return ByteBufUtil.prettyHexDump(buf);
	}
	
}
