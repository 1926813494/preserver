package com.yaoxun.preserver.util.entrypt;

public class CRC16 {
	
	/**
	 * 计算16位CRC
	 * 
	 * @param buf
	 *            缓冲区
	 * @return 16位CRC
	 */
	public static int crc16(byte[] buf) {
		int crc = 0;
		int j;
		int i = 0;

		for (i = 0; i < buf.length; i++) {
			crc ^= ((buf[i] & 0xFF) & 0xFF);
			for (j = 0; j < 8; j++) {
				if ((crc & 0X0001) == 1) {
					crc >>= 1;
					crc ^= 0XA001;
				} else {
					crc >>= 1;
				}
			}
		}
		return crc;
	}

	/**
	 * 计算16位CRC
	 * 
	 * @param buf
	 *            缓冲区
	 * @param offset
	 *            计算偏移量
	 * @return 16位CRC
	 */
	public static int crc16(byte[] buf, int offset) {
		int crc = 0;
		int j;
		int i = 0;

		for (i = offset; i < buf.length; i++) {
			crc ^= ((buf[i] & 0xFF) & 0xFF);
			for (j = 0; j < 8; j++) {
				if ((crc & 0X0001) == 1) {
					crc >>= 1;
					crc ^= 0XA001;
				} else {
					crc >>= 1;
				}
			}
		}
		return crc;
	}

	/**
	 * 计算16位CRC
	 * 
	 * @param buf
	 *            缓冲区
	 * @param offset
	 *            计算偏移量
	 * @param len
	 *            计算长度
	 * @return 16位CRC
	 */
	public static int crc16(byte[] buf, int offset, int len) {
		int crc = 0;
		int j;
		int i = 0;

		for (i = 0; (i < len) && ((i + offset) < len); i++) {
			crc ^= ((buf[i + offset] & 0xFF) & 0xFF);
			for (j = 0; j < 8; j++) {
				if ((crc & 0X0001) == 1) {
					crc >>= 1;
					crc ^= 0XA001;
				} else {
					crc >>= 1;
				}
			}
		}
		return crc;
	}

}