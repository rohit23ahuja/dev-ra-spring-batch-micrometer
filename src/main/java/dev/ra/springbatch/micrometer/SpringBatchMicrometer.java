package dev.ra.springbatch.micrometer;

import io.prometheus.metrics.core.metrics.Histogram;
import io.prometheus.metrics.exporter.pushgateway.PushGateway;
import io.prometheus.metrics.model.snapshots.Unit;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SpringBatchMicrometer {

    private static PushGateway pushGateway = PushGateway.builder()
            .job("playersummary")
            .build();

    public static void main(String[] args) {
        Histogram duration =
                Histogram.builder()
                        .name("job_duration_seconds")
                        .help("job duration in seconds")
                        .unit(Unit.SECONDS)
                        .labelNames("method", "status")
                        .register();
        long start = System.nanoTime();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("job/footballJob.xml");

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("footballJob");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            JobExecution execution = jobLauncher.run(job, jobParameters);
            System.out.println("Exit Status : " + execution.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done");
        context.close();

        duration.labelValues("main","success").observe(Unit.nanosToSeconds(System.nanoTime()-start));
        try {
            pushGateway.push();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
