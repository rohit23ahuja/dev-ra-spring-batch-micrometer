package dev.ra.springbatch.micrometer.launcher;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;

public class CricketJobLauncher {
    private final JobLauncher jobLauncher;

    public CricketJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public JobExecution run(ApplicationContext context, String jobName) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addString("job.name", jobName)
                .addString("file.name", "E:/temp/cricketplayer.csv")
                .addString("message", "i love cricket")
                .toJobParameters();
        Job job = (Job) context.getBean("cricketJob");
        JobExecution execution = jobLauncher.run(job, jobParameters);
        return execution;
    }
}
