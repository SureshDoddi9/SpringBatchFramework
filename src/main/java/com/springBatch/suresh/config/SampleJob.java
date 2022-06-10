package com.springBatch.suresh.config;

import com.springBatch.suresh.listener.FirstJobListener;
import com.springBatch.suresh.listener.FirstStepListener;
import com.springBatch.suresh.processor.FirstItemProcessor;
import com.springBatch.suresh.reader.FirstItemReader;
import com.springBatch.suresh.service.ThirdTasklet;
import com.springBatch.suresh.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleJob {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public ThirdTasklet thirdTasklet;

    @Autowired
    public FirstJobListener firstJobListener;

    @Autowired
    public FirstStepListener firstStepListener;

    @Autowired
    public FirstItemReader firstItemReader;

    @Autowired
    public FirstItemProcessor firstItemProcessor;

    @Autowired
    public FirstItemWriter firstItemWriter;

    @Bean
    public Job firstJob(){
        return jobBuilderFactory.get("First Job")
                .incrementer(new RunIdIncrementer())
                .start(firstStep())
                .next(secondStep())
                .next(thirdStep())
                .listener(firstJobListener)
                .build();
    }



    public Step firstStep(){
       return stepBuilderFactory.get("First step")
               .tasklet(firstTask())
               .listener(firstStepListener)
               .build();
    }

    public Step secondStep(){
        return stepBuilderFactory.get("Second Step")
                .tasklet(secondTask())
                .build();
    }

    public Step thirdStep(){
        return stepBuilderFactory.get("Third Step")
                .tasklet(thirdTasklet)
                .build();
    }

    //First Approach
    public Tasklet firstTask(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is my first step in spring batch App...");
                System.out.println(chunkContext.getStepContext().getJobExecutionContext());
                System.out.println(chunkContext.getStepContext().getStepExecutionContext());
                return RepeatStatus.FINISHED;
            }
        };
    }

    public Tasklet secondTask(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is my second step in spring batch App...");
                return RepeatStatus.FINISHED;
            }
        };
    }

    //=========================================================
    //Chunk Oriented step
    @Bean
    public Job secondJob(){
       return jobBuilderFactory.get("Second Job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .build();
    }

    public Step firstChunkStep(){
        return stepBuilderFactory.get("First Chunk step")
                .<Integer,Long>chunk(3)
                .reader(firstItemReader)
                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }
}
