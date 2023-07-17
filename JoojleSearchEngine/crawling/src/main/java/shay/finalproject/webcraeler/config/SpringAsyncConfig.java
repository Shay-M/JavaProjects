package shay.finalproject.webcraeler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
/**
 * The AppConfig class is a configuration class that enables asynchronous processing and provides a custom executor for handling asynchronous tasks.
 * It implements the AsyncConfigurer interface to customize the executor.
 */
// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/AsyncConfigurer.html
@Configuration
@EnableAsync
public class SpringAsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // set the number of threads to use
        executor.setMaxPoolSize(20); // set the maximum number of threads
        executor.setQueueCapacity(500); // set the queue capacity
        executor.initialize();
        return executor;
    }
}
