package com.project.manager.system.timertasks.task;

import java.util.TimerTask;

import org.springframework.context.ApplicationContext;

import com.project.common.util.DateUtil;
import com.project.manager.memberuser.service.MemberUserService;

public class UserCorpseJudgeTask extends TimerTask {
	private static ApplicationContext applicationContext;
	private static MemberUserService memberUserService;

	@SuppressWarnings("static-access")
	public UserCorpseJudgeTask(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void run() {
		System.out.println("执行UserCorpseJudgeTask定时任务内容：" + DateUtil.getCurDateTime());

		memberUserService = (MemberUserService) applicationContext.getBean("memberUserService");

		try {
			memberUserService.userCorpseJudgeTask();

			memberUserService.calculationUserLevelTask();
			
			memberUserService.backOpenDeduction();
		} catch (Exception e) {
			System.out.println("执行UserCorpseJudgeTask定时任务内容【异常】：" + DateUtil.getCurDateTime());
			e.printStackTrace();
		}
		System.out.println("执行结束UserCorpseJudgeTask定时任务内容：" + DateUtil.getCurDateTime());
	}

}
