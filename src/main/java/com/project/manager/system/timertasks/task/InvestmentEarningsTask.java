package com.project.manager.system.timertasks.task;

import java.util.TimerTask;

import org.springframework.context.ApplicationContext;

import com.project.common.util.DateUtil;
import com.project.manager.investment.service.InvestmentService;

public class InvestmentEarningsTask extends TimerTask {
	private static ApplicationContext applicationContext;
	private static InvestmentService investmentService;

	@SuppressWarnings("static-access")
	public InvestmentEarningsTask(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void run() {
		System.out.println("执行InvestmentEarningsTask定时任务内容：" + DateUtil.getCurDateTime());

		investmentService = (InvestmentService) applicationContext.getBean("investmentService");
		
		investmentService.calculationEarnings();
		
		
		System.out.println("执行结束InvestmentEarningsTask定时任务内容：" + DateUtil.getCurDateTime());
	}

}
