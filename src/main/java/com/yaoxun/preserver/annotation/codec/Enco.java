package com.yaoxun.preserver.annotation.codec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 编码实体
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
