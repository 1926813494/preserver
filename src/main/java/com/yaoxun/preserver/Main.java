package com.yaoxun.preserver;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动程序
 * @author Loren
 * @createTime 2017年11月28日 下午5:52:34
 */
public class Main {

	private static final String SPRING_CONFIG = "classpath:spring*.xml";
	
	public static void main(String[] args) {
		ConfigurableApplicationContext ac = new ClassPathXmlApplicationContext(SPRING_CONFIG);
		Launcher launcher = ac.getBean(Launcher.class);
		launcher.launch();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				launcher.close();
				ac.close();
			}
		}));
	}
	
}
