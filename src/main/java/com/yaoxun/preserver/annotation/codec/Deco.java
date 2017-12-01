package com.yaoxun.preserver.annotation.codec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 解码注解
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
