package dev.ra.springbatch.micrometer.configuration;


import dev.ra.springbatch.micrometer.internal.PlayerInsertionService2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.apache.commons.dbcp.BasicDataSource;

@Configuration
public class StoredProcJobConfiguration {

    @Bean
    public Job storedProcJob(@Value("${springbatch.job.name}") String jobName,
                          JobRepository jobRepository, Step playerInsertionStep) {
        Job job = new JobBuilder(jobName, jobRepository)
                .start(playerInsertionStep)
                .build();
        return job;
    }

    @Bean
    public Step playerInsertionStep(@Value("${springbatch.job.name}") String jobName,
                                   JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager,
                                    MethodInvokingTaskletAdapter playerInsertionTasklet){
        return new StepBuilder(jobName+"_methodInvokingStep", jobRepository)
                .tasklet(playerInsertionTasklet, transactionManager)
                .build();
    }

    @Bean
    @StepScope
    public MethodInvokingTaskletAdapter playerInsertionTasklet(@Value("#{jobParameters['playerId']}") String playerId,
                                                              @Value("#{jobParameters['firstName']}") String firstName,
                                                              @Value("#{jobParameters['lastName']}") String lastName,
                                                              @Value("#{jobParameters['yearOfBirth']}") String yearOfBirth,
                                                              @Value("#{jobParameters['yearOfDraft']}") String yearOfDraft,
                                                               PlayerInsertionService2 playerInsertionService2){
        MethodInvokingTaskletAdapter methodInvokingTaskletAdapter = new MethodInvokingTaskletAdapter();
        methodInvokingTaskletAdapter.setTargetObject(playerInsertionService2);
        methodInvokingTaskletAdapter.setTargetMethod("insertPlayers");
        methodInvokingTaskletAdapter.setArguments(new String[]{playerId, firstName, lastName, yearOfBirth, yearOfDraft});
        return methodInvokingTaskletAdapter;
    }

    @Bean
    public PlayerInsertionService2 playerInsertionService2(BasicDataSource dataSource) {
        PlayerInsertionService2 playerInsertionService2 = new PlayerInsertionService2();
        playerInsertionService2.setDatasource(dataSource);
        return playerInsertionService2;

    }

}