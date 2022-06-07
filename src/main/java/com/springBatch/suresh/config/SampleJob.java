package com.springBatch.suresh.config;

import com.springBatch.suresh.service.ThirdTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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

    @Bean
    public Job firstJob(){
        return jobBuilderFactory.get("First Job")
                .start(firstStep())
                .next(secondStep())
                .next(thirdStep())
                .build();
    }

    public Step firstStep(){
       return stepBuilderFactory.get("First step")
               .tasklet(firstTask())
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
}
