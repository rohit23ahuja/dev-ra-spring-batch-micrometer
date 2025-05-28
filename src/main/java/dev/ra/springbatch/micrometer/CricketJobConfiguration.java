package dev.ra.springbatch.micrometer;

import javax.sql.DataSource;

import dev.ra.springbatch.micrometer.internal.CricketService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import dev.ra.springbatch.micrometer.internal.CricketPlayerFieldSetMapper;
import dev.ra.springbatch.micrometer.internal.CricketPlayerItemWriter;
import dev.ra.springbatch.micrometer.internal.JdbcCricketPlayerDao;

@Configuration
@Conditional(CricketCondition.class)
public class CricketJobConfiguration {

	@Bean
	public Job cricketJob(@Value("${springbatch.job.name}") String jobName,
						  JobRepository jobRepository,
						  TaskletStep taskletStep,
						  Step methodInvokingStep) {
		Job job = new JobBuilder(jobName, jobRepository)
				.start(taskletStep)
				.next(methodInvokingStep)
				.build();
		return job;
	}

	@Bean
	public Step methodInvokingStep(@Value("${springbatch.job.name}") String jobName,
								   JobRepository jobRepository,
								   PlatformTransactionManager transactionManager,
								   MethodInvokingTaskletAdapter methodInvokingTasklet){
		return new StepBuilder(jobName+"_methodInvokingStep", jobRepository)
				.tasklet(methodInvokingTasklet, transactionManager)
				.build();
	}

	@Bean
	public TaskletStep taskletStep(@Value("${springbatch.job.name}") String jobName,
								   ItemWriter<CricketPlayer> cricketPlayerItemWriter,
								   FlatFileItemReader<CricketPlayer> cricketPlayerFlatFileItemReader,
								   JobRepository jobRepository,
								   PlatformTransactionManager transactionManager) {
		TaskletStep taskletStep = new StepBuilder(jobName + "_step", jobRepository)
				.<CricketPlayer, CricketPlayer>chunk(5, transactionManager)
				.reader(cricketPlayerFlatFileItemReader)
				.writer(cricketPlayerItemWriter)
				.build();
		return taskletStep;
	}

	@Bean
	public ItemWriter<CricketPlayer> cricketPlayerItemWriter(CricketPlayerDao cricketPlayerDao) {
		CricketPlayerItemWriter cricketPlayerItemWriter = new CricketPlayerItemWriter();
		cricketPlayerItemWriter.setPlayerDao(cricketPlayerDao);
		return cricketPlayerItemWriter;
	}

	@Bean
	public CricketPlayerDao cricketPlayerDao(DataSource dataSource){
		JdbcCricketPlayerDao jdbcCricketPlayerDao = new JdbcCricketPlayerDao();
		jdbcCricketPlayerDao.setDataSource(dataSource);
		return jdbcCricketPlayerDao;
	}

	@StepScope
	@Bean
	public FlatFileItemReader<CricketPlayer> cricketPlayerFlatFileItemReader(@Value("#{jobParameters['file.name']}") String fileName,
																			 LineMapper<CricketPlayer> lineMapper) {
		return new FlatFileItemReaderBuilder<CricketPlayer>()
				.name("genericreader")
				.resource(new FileSystemResource(fileName))
				.lineMapper(lineMapper)
				.build();
	}

	@Bean
	public LineMapper<CricketPlayer> lineMapper(DelimitedLineTokenizer lineTokenizer,
												FieldSetMapper<CricketPlayer> cricketPlayerFieldSetMapper) {
		DefaultLineMapper<CricketPlayer> lineMapper = new DefaultLineMapper<CricketPlayer>();
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(cricketPlayerFieldSetMapper);
		return lineMapper;
	}

	@Bean
	public DelimitedLineTokenizer lineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("id", "lastName", "firstName", "type");
		return lineTokenizer;
	}

	@Bean
	public FieldSetMapper<CricketPlayer> cricketPlayerFieldSetMapper(){
		return new CricketPlayerFieldSetMapper();
	}

	@Bean
	public CricketService cricketService() { return new CricketService();}

	@Bean
	@StepScope
	public MethodInvokingTaskletAdapter methodInvokingTasklet(@Value("#{jobParameters['message']}") String message,
																	 CricketService cricketService){
		MethodInvokingTaskletAdapter methodInvokingTaskletAdapter = new MethodInvokingTaskletAdapter();
        methodInvokingTaskletAdapter.setTargetObject(cricketService);
		methodInvokingTaskletAdapter.setTargetMethod("serviceMethod");
        methodInvokingTaskletAdapter.setArguments(new String[]{message});
		return methodInvokingTaskletAdapter;
	}

}
