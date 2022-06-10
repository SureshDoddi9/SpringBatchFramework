package com.springBatch.suresh.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class JobService {

    @Autowired
    JobLauncher jobLauncher;

    @Qualifier("firstJob")
    @Autowired
    Job firstJob;

    @Qualifier("secondJob")
    @Autowired
    Job secondJob;


    @Async
    public void startJob(String jobName){

        Map<String, JobParameter> params = new HashMap<>();
        params.put("CurrentSeconds", new JobParameter(Instant.now().getEpochSecond()));

        JobParameters jobParameters = new JobParameters(params);

        try {
            JobExecution jobExecution = null;
            if (jobName.equals("First Job")) {
               jobExecution=  jobLauncher.run(firstJob, jobParameters);
            } else if (jobName.equals("Second Job")) {
               jobExecution= jobLauncher.run(secondJob, jobParameters);
            }
            System.out.println("Job Exectution Id: "+jobExecution.getId());
        }
        catch (Exception e){
            System.out.println("Exxception while Executing job");
        }
    }
}
