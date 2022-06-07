package com.springBatch.suresh.service;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Service
public class ThirdTasklet implements Tasklet {

    //Second Approach
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
       System.out.println("This is my Third step in spring batch App...");
       return RepeatStatus.FINISHED;
    }
}
