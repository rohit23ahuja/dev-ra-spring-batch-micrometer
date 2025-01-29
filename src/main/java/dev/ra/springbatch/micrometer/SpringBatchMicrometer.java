package dev.ra.springbatch.micrometer;

import dev.ra.springbatch.micrometer.launcher.CricketJobLauncher;
import dev.ra.springbatch.micrometer.launcher.FootballJobLauncher;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.atomic.AtomicInteger;

public class SpringBatchMicrometer {

    private static final Logger _log = LoggerFactory.getLogger(SpringBatchMicrometer.class);

    public static void main(String[] args) throws InterruptedException {
        final String jobName = args[0];
        System.setProperty("springbatch.job.name", jobName);
        System.setProperty("cricketEnabled", "true");
        AtomicInteger gauge = Metrics.globalRegistry.gauge("app.job.status", Tags.of("jobName", jobName), new AtomicInteger(0));
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("job/context.xml");
        _log.info("Job name: {}", jobName);
        JobExecution execution = null;
        try {
            if (jobName.equals("footballJobXml")) {
                FootballJobLauncher footballJobLauncher = (FootballJobLauncher) context.getBean("footballJobLauncher");
                execution = footballJobLauncher.run(context, jobName);
            } else if (jobName.equals("cricketJobJava")) {
                CricketJobLauncher cricketJobLauncher = (CricketJobLauncher) context.getBean("cricketJobLauncher");
                execution = cricketJobLauncher.run(context, jobName);
            }
            _log.info("Exit Status : {}", execution.getStatus());
            gauge.set(BatchStatus.COMPLETED.equals(execution.getStatus())?1:2);
        } catch (Exception e) {
            gauge.set(2);
            _log.error("Exception occurred.", e);
        }
        _log.info("Done");
        Thread.sleep(3000);
        context.close();
    }
}
