package com.xunpay.money.core.plugin;

import java.util.List;

import com.xunpay.money.core.job.BaseJob;
import com.xunpay.money.model.SysTask;
import com.xunpay.money.utils.Constant;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.jfinal.plugin.IPlugin;

/**
 * 自定义调度任务插件
 * <pre>
 * 在线生成器:  http://cron.qqe2.com
 * 常用示例:
	0 0 12 * * ? 每天12点触发
	0 15 10 ? * * 每天10点15分触发
	0 15 10 * * ? 每天10点15分触发
	0 15 10 * * ? * 每天10点15分触发
	0 15 10 * * ? 2005 2005年每天10点15分触发
	0 * 14 * * ? 每天下午的 2点到2点59分每分触发
	0 0/5 14 * * ? 每天下午的 2点到2点59分(整点开始，每隔5分触发)
	0 0/5 14,18 * * ? 每天下午的 2点到2点59分(整点开始，每隔5分触发) 每天下午的 18点到18点59分(整点开始，每隔5分触发)
	0 0-5 14 * * ? 每天下午的 2点到2点05分每分触发
	0 10,44 14 ? 3 WED 3月分每周三下午的 2点10分和2点44分触发
	0 15 10 ? * MON-FRI 从周一到周五每天上午的10点15分触发
	0 15 10 15 * ? 每月15号上午10点15分触发
	0 15 10 L * ? 每月最后一天的10点15分触发
	0 15 10 ? * 6L 每月最后一周的星期五的10点15分触发
	0 15 10 ? * 6L 2002-2005 从2002年到2005年每月最后一周的星期五的10点15分触发
	0 15 10 ? * 6#3 每月的第三周的星期五开始触发
	0 0 12 1/5 * ? 每月的第一个中午开始每隔5天触发一次
	0 11 11 11 11 ? 每年的11月11号 11点11分触发
 * </pre>
 * 2016年9月16日
 */
@SuppressWarnings("unchecked")
public class QuartzPlugin implements IPlugin {

	private static final Logger logger = Logger.getLogger(QuartzPlugin.class);
	private static Scheduler sched;
	private static final String packageName = "com.xunpay.money.core.job";

	public boolean start() {
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			sched = sf.getScheduler();
			reload();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean stop() {
		try {
			sched.clear();
			sched.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean startJob(Integer taskId) {
		SysTask task = SysTask.dao.findById(taskId);
		try {
			if (sched.checkExists(new JobKey(task.getTaskCode(), task.getGroupCode()))) {
				sched.deleteJob(new JobKey(task.getTaskCode(), task.getGroupCode()));
			}
			startTask(task);
			task.setStatus(Constant.NORMAL);
			task.update();
			return true;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean stopJob(Integer taskId) {
		SysTask task = SysTask.dao.findById(taskId);
		try {
			if (sched.checkExists(new JobKey(task.getTaskCode(), task
					.getGroupCode()))) {
				sched.deleteJob(new JobKey(task.getTaskCode(), task
						.getGroupCode()));
			}
			task.setStatus(Constant.DISABLE);
			task.update();
			return true;
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isStart() {
		try {
			return sched.isStarted();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean reload() {
		try {
			sched.clear();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
		List<SysTask> taskList = SysTask.dao.find("select * from sys_task where status = ?", Constant.NORMAL);
		for (SysTask task : taskList) {
			startTask(task);
		}
		try {
			sched.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private static void startTask(SysTask task){
		try {
			Class<Job> clazz = (Class<Job>) Class.forName(packageName + "." + task.getTaskClass());
			JobDetail job = JobBuilder.newJob(clazz)
					.withIdentity(task.getTaskCode(), task.getGroupCode())
					.build();
			job.getJobDataMap().put("taskId", task.getId());
			
			if (Constant.YES.equals(task.getAutoExec())) {
				BaseJob bj = (BaseJob) clazz.newInstance();
				bj.setTaskId(task.getId());
				bj.execute(null);
			}
			
			CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger()
					.withIdentity(task.getTaskCode(), task.getGroupCode())
					.withSchedule(CronScheduleBuilder.cronSchedule(task.getTaskCron()))
					.build();

			sched.scheduleJob(job, trigger);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("初始化任务失败" + task.getTaskClass() + " -> " + e.getMessage());
		}
	}
}
