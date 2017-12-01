package com.yaoxun.preserver.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.yaoxun.preserver.annotation.codec.Deco;
import com.yaoxun.preserver.annotation.codec.DecoRadix;
import com.yaoxun.preserver.annotation.codec.Enco;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.CharsetUtil;
/**
 * netty bytebuf 转换工具类
 * @author Loren
 * @createTime 2017年12月1日 下午3:12:39
 */
public class BufferUtils {
	
	/**
	 * 将bytebuf 转换成对应的实体
	 * <br />
	 * 注意该转换不处理char类型，因为Java的char和c的字节长度不一样
	 * @author Loren
	 * @createTime 2017年12月1日 下午2:05:17
	 * @param buf
	 * @param clazz
	 * @return
	 */
	public static <T> T parse(ByteBuf buf, Class<T> clazz) {
		try {			
			T instance = clazz.newInstance();
			Class<? extends Object> c = instance.getClass();
			Field[] fields = c.getDeclaredFields();
			for(Field field : fields) {
				handleDeco(c, instance, buf, field);
			}
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将对象转换成字节数组
	 * @author Loren
	 * @createTime 2017年12月1日 下午4:02:37
	 * @param object
	 * @return
	 */
	public static byte[] toBytes(Object object) {
		ByteBuf buf = toBuf(object);
		byte[] dst = new byte[buf.readableBytes()];
		buf.readBytes(dst);
		return dst;
	}
	
	/**
	 * 将对象转换成bytebuf
	 * @author Loren
	 * @createTime 2017年12月1日 下午5:47:11
	 * @param object
	 * @return
	 */
	public static ByteBuf toBuf(Object object) {
		try {			
			ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
			Class<? extends Object> c = object.getClass();
			Field[] fields = c.getDeclaredFields();
			for(Field field : fields) {
				handleEnco(c, object, buffer, field);
			}
			return buffer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 处理编码实体
	 * @author Loren
	 * @createTime 2017年12月1日 下午5:27:19
	 * @param c
	 * @param object
	 * @param field
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void handleEnco(Class<? extends Object> c, Object object,ByteBuf buffer, Field field) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Enco enco = field.getAnnotation(Enco.class);
		if(enco != null) {
			String methodName = assembleGet(field);
			Method method = c.getMethod(methodName);
			Object result = method.invoke(object);
			Class<?> type = field.getType();
			switch (type.getSimpleName()) {
			case "byte":
				buffer.writeByte((byte) result);
				break;
			case "short":
				writeShort(buffer, enco, result);
				break;
			case "int":
				writeInt(buffer, enco, result);
				break;
			case "long":
				break;
			case "float":
				buffer.writeFloat((float)result);
				break;
			case "double":
				buffer.writeDouble((double)result);
				break;
			case "String":
				writeString(buffer, enco, result);
				break;
			case "byte[]":				
				writeBytes(buffer, enco, result);
				break;
			case "Byte[]":
				writeBytes(buffer, enco, result);
				break;
			case "Byte":
				buffer.writeByte((byte) result);
				break;
			case "Short":
				writeShort(buffer, enco, result);
				break;
			case "Integer":
				writeInt(buffer, enco, result);
				break;
			case "Long":
				writeLong(buffer, enco, result);
				break;
			case "Float":
				buffer.writeFloat((float)result);
				break;
			case "Double":
				buffer.writeDouble((double)result);
				break;
			}
		}
	}

	private static void writeString(ByteBuf buf, Enco enco, Object result) {
			byte[] bytes = ByteUtils.hexToBytes((String) result, enco.hilo());
			buf.writeBytes(bytes);		
	}
	
	private static void writeBytes(ByteBuf buf, Enco enco, Object result) {
		if(enco.hilo()) {
			byte[] dst = ByteUtils.reverse((byte[])result);
			buf.writeBytes(dst);
		}else {
			buf.writeBytes((byte[])result);
		}
	}
	
	private static void writeLong(ByteBuf buf, Enco enco, Object result) {
		if(enco.hilo()) {
			buf.writeLongLE((long) result);
		}else {
			buf.writeLong((long) result);
		}
	}
	
	private static void writeInt(ByteBuf buf, Enco enco, Object result) {
		if(enco.hilo()) {
			buf.writeIntLE((int) result);
		}else {
			buf.writeInt((int) result);
		}
	}
	
	private static void writeShort(ByteBuf buf, Enco enco, Object result) {
		if(enco.hilo()) {
			buf.writeShortLE((short) result);
		} else {
			buf.writeShort((short) result);			
		}
	}
	
	/**
	 * 处理解码实体
	 * @author Loren
	 * @createTime 2017年12月1日 下午5:26:39
	 * @param c
	 * @param instance
	 * @param buf
	 * @param field
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private static <T> void handleDeco(Class<? extends Object> c, T instance, ByteBuf buf, Field field) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException {
		Deco deco = field.getAnnotation(Deco.class);
		if(deco != null) {
			Method method = null;
			Class<?> type = field.getType();
			String methodName = assembleSet(field); //获取setXX方法
			switch (type.getSimpleName()) {
			case "byte":
				method = c.getMethod(methodName, byte.class);
				method.invoke(instance, buf.readByte());
				break;
			case "short":
				method = c.getMethod(methodName, short.class);
				readShort(instance, buf, method, deco);
				break;
			case "int":
				method = c.getMethod(methodName, int.class);
				readInt(instance, buf, method, deco);
				break;
			case "float":
				method = c.getMethod(methodName, float.class);
				method.invoke(instance, buf.readFloat());
				break;
			case "long":
				method = c.getMethod(methodName, long.class);
				readLong(instance, buf, method, deco);
				break;
			case "double":
				method = c.getMethod(methodName, double.class);
				method.invoke(instance, buf.readDouble());
				break;
			case "String":
				method = c.getMethod(methodName, String.class);
				readString(instance, buf, method, deco);
				break;
			case "byte[]":
				method = c.getMethod(methodName, byte[].class);
				readBytes(instance, buf, method, deco);
				break;
			case "Byte[]":
				method = c.getMethod(methodName, Byte[].class);
				readBytes(instance, buf, method, deco);
				break;
			case "Byte":
				method = c.getMethod(methodName, Byte.class);
				method.invoke(instance, buf.readByte());
				break;
			case "Short":
				method = c.getMethod(methodName, Short.class);
				readShort(instance, buf, method, deco);
				break;
			case "Integer":
				method = c.getMethod(methodName, Integer.class);
				readInt(instance, buf, method, deco);
				break;
			case "Long":
				method = c.getMethod(methodName, Long.class);
				readLong(instance, buf, method, deco);
				break;
			case "Float":
				method = c.getMethod(methodName, Float.class);
				method.invoke(instance, buf.readFloat());
				break;
			case "Double":
				method = c.getMethod(methodName, Double.class);
				method.invoke(instance, buf.readDouble());
				break;
			}
		}
	}
	
	private static <T> void readBytes(T instance, ByteBuf buf,Method method, Deco deco) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		byte[] dst = new byte[deco.length()];
		buf.readBytes(dst);
		if(deco.hilo()) {
			method.invoke(instance, ByteUtils.reverse(dst));
		} else {
			method.invoke(instance, dst);
		}
	}
	
	private static <T> void readString(T instance, ByteBuf buf,Method method, Deco deco) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		byte[] dst = new byte[deco.length()];
		buf.readBytes(dst);
		String result = null;
		if(deco.radix() == DecoRadix.HEX) {
			result = ByteUtils.byteToHex(dst, deco.hilo());
		}else if(deco.radix() == DecoRadix.STR) {
			result = new String(dst, CharsetUtil.UTF_8);
		}
		method.invoke(instance, result);
	}
	
	private static <T> void readLong(T instance, ByteBuf buf,Method method, Deco deco) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(deco.hilo()) {
			method.invoke(instance, buf.readLongLE());
		}else {
			method.invoke(instance, buf.readLong());
		}
	}
	
	private static <T> void readInt(T instance, ByteBuf buf,Method method, Deco deco) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(deco.hilo()) {
			method.invoke(instance, buf.readIntLE());
		}else {
			method.invoke(instance, buf.readInt());
		}
	}
	
	private static <T> void readShort(T instance, ByteBuf buf,Method method, Deco deco) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(deco.hilo()) {
			method.invoke(instance, buf.readShortLE());
		}else {					
			method.invoke(instance, buf.readShort());
		}
	}
		
	public static String assembleSet(Field field) {
		String suffix = suffixMethod(field); 
		String methodName = "set" + suffix;
		return methodName;
	}
	
	public static String assembleGet(Field field) {
		String suffix = suffixMethod(field);
		String methodName = "get" + suffix;
		return methodName;
	}
	
	private static String suffixMethod(Field field) {
		String name = field.getName();
		char start = Character.toUpperCase(name.charAt(0));
		String end = name.substring(1);
		return start + end;
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getTypeClass(String simpleTypeName) {
		switch (simpleTypeName) {
		case "byte":
			return byte.class;
		case "char":
			return char.class;
		case "short":
			return short.class;
		case "int":
			return int.class;
		case "float":
			return float.class;
		case "long":
			return long.class;
		case "double":
			return double.class;
		case "String":
			return String.class;
		case "byte[]":
			return byte[].class;
		case "Byte[]":
			return Byte[].class;
		case "Byte":
			return Byte.class;
		case "Character":
			return Character.class;
		case "Short":
			return Short.class;
		case "Integer":
			return Integer.class;
		case "Long":
			return Long.class;
		case "Float":
			return Float.class;
		case "Double":
			return Double.class;
		default:
			return null;
		}
	}
	
}
