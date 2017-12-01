package com.yaoxun.preserver.util.enume;

import com.yaoxun.preserver.entity.server.ServerOrder;
import com.yaoxun.preserver.entity.time.TimeSyn;
import com.yaoxun.preserver.util.ByteUtils;

/**
 * 功能码
 * @author Loren
 * @createTime 2017年11月29日 下午2:39:43
 */
public enum FunCode {
	
	/**
	 * 车辆轨迹信息
	 */
	CAR_TRACK(0x8100, ServerOrder.class),
	
	/**
	 * 主机时间同步
	 */
	TIME_SYN(0x8300, TimeSyn.class);
	
	private Integer value;
	
	private Class<?> clazz;
	
	private FunCode(int value, Class<?> clazz) {
		this.value = value;
		this.clazz = clazz;
	}
	
	/**
	 * 获取对应的值
	 * @author Loren
	 * @createTime 2017年12月1日 下午3:24:02
	 * @return
	 */
	public short value() {
		return value.shortValue();
	}
	
	/**
	 * 获取对应的转换类型
	 * @author Loren
	 * @createTime 2017年12月1日 下午3:24:12
	 * @return
	 */
	public Class<?> getClazz() {
		return this.clazz;
	}
	
	/**
	 * 给定两个字节数获取功能码
	 * @author Loren
	 * @createTime 2017年11月30日 上午9:03:39
	 * @param dst
	 * @return 存在返回功能码，不存在返回null
	 */
	public static FunCode getFunCode(byte[] dst) {
		short funCode = ByteUtils.hiloToShort(dst);
		for(FunCode code : FunCode.values()) {
			if(funCode == code.value()) {
				return code;
			}
		}
		return null;
	}
	
}
