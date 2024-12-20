package com.study.bc.springbatchstudy.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldJob {

    @Bean
    public Job job(JobRepository jobRepository, Step printHelloWorldStep) {
        return new JobBuilder("job", jobRepository)
                .start(printHelloWorldStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
