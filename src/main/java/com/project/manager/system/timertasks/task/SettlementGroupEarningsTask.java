package com.project.manager.system.timertasks.task;

import java.util.TimerTask;

import org.springframework.context.ApplicationContext;

import com.project.common.util.DateUtil;
import com.project.manager.competition.service.CompetitionEarningsService;

public class SettlementGroupEarningsTask extends TimerTask {
	private static ApplicationContext applicationContext;
	private static CompetitionEarningsService competitionEarningsService;

	@SuppressWarnings("static-access")
	public SettlementGroupEarningsTask(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void run() {
		System.out.println("执行UserCorpseJudgeTask定时任务内容：" + DateUtil.getCurDateTime());

		competitionEarningsService = (CompetitionEarningsService) applicationContext.getBean("competitionEarningsService");

		try {
			competitionEarningsService.settlementGroupEarnings();
		} catch (Exception e) {
			System.out.println("执行结束UserCorpseJudgeTask定时任务失败：" + DateUtil.getCurDateTime());
			e.printStackTrace();
		}

		System.out.println("执行结束UserCorpseJudgeTask定时任务内容：" + DateUtil.getCurDateTime());
	}

}
