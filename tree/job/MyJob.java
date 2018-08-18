package tree.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String userName=(String)jobExecutionContext.getJobDetail().getJobDataMap().get("userName");
        String userPassword=(String)jobExecutionContext.getTrigger().getJobDataMap().get("userPassword");
        System.out.println("---------------------------");
        System.out.println("user-name: "+userName);
        System.out.println("user-password: "+userPassword);
        System.out.println("---------------------------");
    }
}
