package com.project.manager.system.timertasks.servlet;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.project.common.util.DateUtil;
import com.project.manager.system.timertasks.task.InvestmentEarningsTask;
import com.project.manager.system.timertasks.task.SettlementGroupEarningsTask;
import com.project.manager.system.timertasks.task.UserCorpseJudgeTask;

public class InvestmentEarningsServlet extends HttpServlet {

	private static final long serialVersionUID = -6861231281702514362L;

	// private long begintime;// 开始时间
	private long frequency;// 执行频率
	private String loadonstartup;

	// 获得web配置的参数
	public void init(ServletConfig config) throws ServletException {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 1); // 凌晨1点
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime(); // 第一次执行定时任务的时间
		// 如果第一次执行定时任务的时间 小于当前的时间
		// 此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
		if (date.before(new Date())) {
			date = DateUtil.addDay(date, 1);
		}

		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		super.init(config);
		// begintime = Long.parseLong(config.getInitParameter("begintime"));
		// frequency = Long.parseLong(config.getInitParameter("frequency"));
		frequency = 24 * 60 * 60 * 1000;// 设置执行频率为24小时
		loadonstartup = config.getInitParameter("loadonstartup");
		System.out.println("启动InvestmentEarningsTask（计算理财产品收益）定时任务成功，开始时间：每日凌晨1点，间隔时间：" + frequency + "," + loadonstartup + "；下次执行时间" + date);
		// 初始化定时任务
		// 理财产品的定时任务
		new Timer().schedule(new InvestmentEarningsTask(applicationContext), date, frequency);// 这个方法是Timer类的方法：time是具体执行的内容
		// 僵尸用户定时任务
		new Timer().schedule(new UserCorpseJudgeTask(applicationContext), date, frequency);// 这个方法是Timer类的方法：time是具体执行的内容
		// 团队解冻收益定时任务
		new Timer().schedule(new SettlementGroupEarningsTask(applicationContext), date, frequency);// 这个方法是Timer类的方法：time是具体执行的内容
	}
}
