package tree;

import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String args[]) throws Exception{
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        Scheduler scheduler=(Scheduler)context.getBean("scheduler");
        /*
         *  由 spring 封装的 scheduler 不需要 sleep，当任务中断后继续任务也不需要以下代码
         *      TriggerKey key=new TriggerKey("myTrigger","group1");
         *      scheduler.rescheduleJob(key,scheduler.getTrigger(key));
         */
        scheduler.start();
    }
}
