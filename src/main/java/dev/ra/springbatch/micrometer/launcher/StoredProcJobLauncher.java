package dev.ra.springbatch.micrometer.launcher;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;

public class StoredProcJobLauncher {

    private final JobLauncher jobLauncher;

    public StoredProcJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public JobExecution run(ApplicationContext context, String playerId, String firstName, String lastName, long yearOfBirth, long yearDrafted)
            throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("playerId", playerId)
                .addString("firstName", firstName)
                .addString("lastName", lastName)
                .addLong("yearOfBirth", yearOfBirth)
                .addLong("yearOfDraft", yearDrafted)
                .toJobParameters();
        Job job = (Job) context.getBean("storedProcJob");
        return jobLauncher.run(job, jobParameters);
    }
}
