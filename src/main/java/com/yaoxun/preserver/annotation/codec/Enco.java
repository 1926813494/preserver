package com.yaoxun.preserver.annotation.codec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 编码实体
 * <br />
 * <pre>
 * startCode = aa
 * funCode = 00 11
 * frameLen = 04 00
 * number = bb cc gg dd
 * 那么报文组成：aa 00 11 04 00 bb cc gg dd
 * 实体定义如下
 * class Entity {
 * 		@Enco
 * 		private byte startCode;
 * 
 * 		@Enco
 * 		private short funCode;
 * 		
 * 		@Enco
 * 		private short frameLen;
 * 
 * 		@Enco
 * 		private String number;
 * }
 * </pre>
 * @author Loren
 * @createTime 2017年12月1日 下午5:22:51
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Enco {

	/**
	 * 用于字符串，标志当前的字符串是类型
	 * @author Loren
	 * @createTime 2017年12月1日 下午5:25:45
	 * @return
	 */
	int curRadix() default DecoRadix.HEX;
	
	/**
	 * 是否需要进行高低位转换
	 * @author Loren
	 * @createTime 2017年12月1日 下午2:12:29
	 * @return
	 */
	boolean hilo() default false;
	
}
