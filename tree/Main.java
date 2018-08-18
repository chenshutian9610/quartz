package tree;

import org.junit.Before;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;
import org.quartz.impl.triggers.CronTriggerImpl;
import tree.job.MyJob;

import java.util.*;
import java.util.Calendar;

public class Main {
    Scheduler scheduler;
    @Before
    public void init() throws Exception{
        /* 初始化容器 */
        SchedulerFactory factory=new StdSchedulerFactory();
        scheduler=factory.getScheduler();
    }
    @Test
    public void doInTest() throws Exception{
        /* 设置五一假期和国庆 */
        AnnualCalendar holidays=new AnnualCalendar();
        Calendar laborDay=new GregorianCalendar();
        laborDay.add(Calendar.MONTH,5);
        laborDay.add(Calendar.DATE,1);
        Calendar nationDay=new GregorianCalendar();
        nationDay.add(Calendar.MONTH,10);
        nationDay.add(Calendar.DATE,1);
        ArrayList<Calendar>calendars=new ArrayList<>();
        calendars.add(laborDay);
        calendars.add(nationDay);
        holidays.setDaysExcluded(calendars);
        /* 注册时间 */
        scheduler.addCalendar("holidays",holidays,false,false);

        /*
         *  SimpleTrigger trigger=new SimpleTrigger("myTrigger","group1");
         *  trigger.setStartTime(new Date(System.currentTimeMillis()+3000));
         *  trigger.setRepeatCount(5);
         *  trigger.setRepeatInterval(2000);
         */

        /* cron 触发器，实现复杂调度 */
        CronTriggerImpl trigger=new CronTriggerImpl("myTrigger","group1","0/5 * * ? * *");
        trigger.getJobDataMap().put("userPassword","1014");
        trigger.setCalendarName("holidays");

        /* 任务描述 */
        JobDetailImpl detail=new JobDetailImpl("myJob","group1",MyJob.class);
        detail.getJobDataMap().put("userName","triski");

        /* 注册任务并开始 */
        scheduler.scheduleJob(detail,trigger);
        scheduler.start();
        /* 这一步很重要，否则退出主线程之后，任务也会被退出 */
        Thread.currentThread().sleep(100000);
    }
    @Test
    public void redo() throws Exception{
        /* 从中断处重新开始任务 */
        TriggerKey key=new TriggerKey("myTrigger","group1");
        scheduler.rescheduleJob(key,scheduler.getTrigger(key));
        scheduler.start();
        Thread.currentThread().sleep(100000);
    }
}
