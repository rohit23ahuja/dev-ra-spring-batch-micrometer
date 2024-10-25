package dev.ra.springbatch.micrometer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import dev.ra.springbatch.micrometer.internal.CricketPlayerFieldSetMapper;
import dev.ra.springbatch.micrometer.internal.CricketPlayerItemWriter;

@Configuration
public class JobConfiguration {
	private static final String OVERRIDDEN_BY_EXPRESSION = null;


	@Bean
	public Job configureJob(@Value("${springbatch.job.name}") String jobName, ApplicationContext context) {
		JobRepository jobRepository = (JobRepository) context.getBean(("jobRepository"));
		Job job = new JobBuilder(jobName, jobRepository).start(getTaskletStep(context, OVERRIDDEN_BY_EXPRESSION)).build();
		return job;
	}

	@Bean
	public TaskletStep getTaskletStep(ApplicationContext context, @Value("${springbatch.job.name}") String jobName) {
		JobRepository jobRepository = (JobRepository) context.getBean(("jobRepository"));
		PlatformTransactionManager transactionManager = (PlatformTransactionManager) context
				.getBean(("transactionManager"));
		TaskletStep taskletStep = new StepBuilder(jobName + "_step", jobRepository)
				.<CricketPlayer, CricketPlayer>chunk(5, transactionManager)
				.reader(getReader(OVERRIDDEN_BY_EXPRESSION))
				.writer(getWriter())
				.build();
		return taskletStep;
	}

	@Bean
	public ItemWriter<CricketPlayer> getWriter() {
		return new CricketPlayerItemWriter();
	}

	@StepScope
	@Bean
	public FlatFileItemReader<CricketPlayer> getReader(@Value("#{jobParameters['file.name']}") String fileName) {
		return new FlatFileItemReaderBuilder<CricketPlayer>()
				.name("genericreader")
				.resource(new FileSystemResource(fileName))
				.lineMapper(getLineMapper())
				.build();
	}

	@Bean
	public LineMapper<CricketPlayer> getLineMapper() {
		DefaultLineMapper<CricketPlayer> lineMapper = new DefaultLineMapper<CricketPlayer>();
		lineMapper.setLineTokenizer(getLineTokenizer());
		lineMapper.setFieldSetMapper(new CricketPlayerFieldSetMapper());
		return lineMapper;
	}

	@Bean
	public DelimitedLineTokenizer getLineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("id", "lastName", "firstName", "type");
		return lineTokenizer;
	}
}
