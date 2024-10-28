package dev.ra.springbatch.micrometer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBatchMicrometer {

	public static void main(String[] args) {
		final String jobName = args[0];
		System.setProperty("springbatch.job.name", jobName);
		
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("job/footballJob.xml");
		System.out.println("Job name: " + jobName);
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
	            System.out.println("Exit Status : " + execution.getStatus());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        System.out.println("Done");
	        context.close();
		} else if(jobName.equals("cricketJobJava")) {
	        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
	        JobParameters jobParameters = new JobParametersBuilder()
	                .addLong("time", System.currentTimeMillis())
	                .addString("job.name", jobName)
	                .addString("file.name", "E:/temp/cricketplayer.csv")
	                .toJobParameters();
	        Job job = (Job) context.getBean("cricketJob");
	        try {
	            JobExecution execution = jobLauncher.run(job, jobParameters);
	            System.out.println("Exit Status : " + execution.getStatus());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        System.out.println("Done");
	        context.close();
		}
	}
}
