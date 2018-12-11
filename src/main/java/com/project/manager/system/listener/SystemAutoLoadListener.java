package com.project.manager.system.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * 监听
 */
@Service
public class SystemAutoLoadListener implements ApplicationListener<ContextRefreshedEvent> {


	private int times = 0; // 防止两次扫描，加载两次

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		try {
			if (times < 1) {
			}

			times++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
