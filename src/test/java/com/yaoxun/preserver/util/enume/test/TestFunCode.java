package com.yaoxun.preserver.util.enume.test;

import org.junit.Test;

import com.yaoxun.preserver.util.enume.FunCode;

public class TestFunCode {

	@Test
	public void testValueOf() {
		FunCode[] values = FunCode.values();
		for(FunCode code : values) {
			System.out.println(code.value());
		}
	}
	
	@Test
	public void testGetFunCode() {
		byte[] dst = new byte[2];
		FunCode funCode = FunCode.getFunCode(dst);
		System.out.println(funCode);
	}
	
	@Test
	public void testFunCodeName() {
		System.out.println(FunCode.CAR_TRACK.name());
	}
	
}
