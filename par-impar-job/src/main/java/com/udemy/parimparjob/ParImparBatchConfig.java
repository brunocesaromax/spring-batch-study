package com.udemy.parimparjob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class ParImparBatchConfig {

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("imprimeParImparJob", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step imprimeParImparStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("imprimeParImparStep", jobRepository)
                .<Integer, String>chunk(1, transactionManager) // Um registro por pedaço
                .reader(contaAteDezReader())
                .processor(parOuImparProcessor())
                .writer(imprimeWriter())
                .build();
    }

    private IteratorItemReader<Integer> contaAteDezReader() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return new IteratorItemReader<>(numbers.iterator());
    }

    private FunctionItemProcessor<Integer, String> parOuImparProcessor() {
        return new FunctionItemProcessor<>(item -> item % 2 == 0 ?
                String.format("Item %s é Par", item) : String.format("Item %s é Ímpar", item));
    }

    private ItemWriter<String> imprimeWriter() {
        return itens -> itens.forEach(System.out::println);
    }
}
