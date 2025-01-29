package dev.ra.springbatch.micrometer.launcher;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;

public class FootballJobLauncher {
    private JobLauncher jobLauncher;

    public FootballJobLauncher(JobLauncher jobLauncher){
        this.jobLauncher = jobLauncher;
    }
    public JobExecution run(ApplicationContext context, String jobName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Job job = (Job) context.getBean("footballJob");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addString("jobName", jobName)
                .addString("names", "ID,lastName,firstName,position,birthYear,debutYear")
                .toJobParameters();
        return jobLauncher.run(job, jobParameters);
    }
}
