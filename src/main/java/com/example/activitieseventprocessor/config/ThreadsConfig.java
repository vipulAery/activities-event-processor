package com.example.activitieseventprocessor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadsConfig {
    @Value("${thread.count.message-process}")
    private int countMessageProcess;
    @Bean
    public ExecutorService messageProcessorExecutor() {
        return Executors.newFixedThreadPool(countMessageProcess);
    }

}
