package dev.ra.springbatch.micrometer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBatchMicrometer {

	private static final Logger _log = LoggerFactory.getLogger(SpringBatchMicrometer.class);

	public static void main(String[] args) throws InterruptedException {
		final String jobName = args[0];
		System.setProperty("springbatch.job.name", jobName);
		System.setProperty("cricketEnabled","true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("job/footballJob.xml");
		_log.info("Job name: {}", jobName);
		if (jobName.equals("footballJobXml")) {
	        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
	        Job job = (Job) context.getBean("footballJob");
	        JobParameters jobParameters = new JobParametersBuilder()
	                .addLong("time", System.currentTimeMillis())
	                .addString("jobName", jobName)
	                .addString("names", "ID,lastName,firstName,position,birthYear,debutYear")
	                .toJobParameters();
	        try {
	            JobExecution execution = jobLauncher.run(job, jobParameters);
				_log.info("Exit Status : {}", execution.getStatus());
	        } catch (Exception e) {
				_log.error("Exception occurred.", e);
	        }
			_log.info("Done");
			Thread.sleep(3000);
	        context.close();
		} else if(jobName.equals("cricketJobJava")) {
	        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
	        JobParameters jobParameters = new JobParametersBuilder()
	                .addLong("time", System.currentTimeMillis())
	                .addString("job.name", jobName)
	                .addString("file.name", "E:/temp/cricketplayer.csv")
					.addString("message","i love cricket")
	                .toJobParameters();
	        Job job = (Job) context.getBean("cricketJob");
	        try {
	            JobExecution execution = jobLauncher.run(job, jobParameters);
				_log.info("Exit Status : {}", execution.getStatus());
	        } catch (Exception e) {
				_log.error("Exception occurred.", e);
	        }
			_log.info("Done");
			Thread.sleep(3000);
	        context.close();
		}
	}
}
