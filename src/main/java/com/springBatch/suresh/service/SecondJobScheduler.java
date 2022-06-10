package com.springBatch.suresh.service;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecondJobScheduler {


    @Autowired
    JobLauncher jobLauncher;

    @Qualifier("secondJob")
    @Autowired
    Job secondJob;

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void  secondJobstarter() throws Exception {
        Map<String, JobParameter> params = new HashMap<>();
        params.put("CurrentSeconds", new JobParameter(Instant.now().getEpochSecond()));

        JobExecution jobExecution  = null;

        JobParameters jobParameters = new JobParameters(params);
        jobExecution = jobLauncher.run(secondJob,jobParameters);
        System.out.println("Job Execution Id: "+jobExecution.getId());
    }
}
