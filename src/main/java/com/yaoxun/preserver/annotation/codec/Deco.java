package com.yaoxun.preserver.annotation.codec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 解码注解，使用该注解的类定义的字段属性需要按和报文的顺序一致
 * <br />
 * <pre>
 * startCode = aa
 * funCode = 00 11
 * frameLen = 04 00
 * number = bb cc gg dd
 * 那么报文组成：aa 00 11 04 00 bb cc gg dd
 * 实体定义如下
 * class Entity {
 * 		@Deco
 * 		private byte startCode;
 * 
 * 		@Deco
 * 		private short funCode;
 * 		
 * 		@Deco
 * 		private short frameLen;
 * 
 * 		@Deco(length=4,hilo=true)
 * 		private String number;
 * }
 * </pre>
 * @author Loren
 * @createTime 2017年12月1日 下午5:22:33
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Deco {

	/**
	 * 解码的长度
	 * @author Loren
	 * @createTime 2017年12月1日 上午9:33:08
	 * @return
	 */
	int length() default 1;
	
	/**
	 * 用于解码成字符串时使用，指定解码的类型{@link DecoRadix}
	 * @author Loren
	 * @createTime 2017年12月1日 下午12:00:05
	 * @return
	 */
	int radix() default DecoRadix.HEX;
	
	/**
	 * 是否需要进行高低位转换
	 * @author Loren
	 * @createTime 2017年12月1日 下午2:12:29
	 * @return
	 */
	boolean hilo() default false;
	
}
