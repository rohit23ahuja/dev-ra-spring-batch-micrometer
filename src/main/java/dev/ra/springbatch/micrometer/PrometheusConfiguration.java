package dev.ra.springbatch.micrometer;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.PushGateway;

@Configuration
@PropertySource("classpath:application.properties")
@Profile("metrics")
@EnableScheduling
public class PrometheusConfiguration {

    private CollectorRegistry collectorRegistry;

    private PushGateway pushGateway;
    private final Map<String, String> groupingKey = new HashMap<>();

    @Value("${metrics.grouping.key}")
    private String metricsGroupingKey;

    @Value("${metrics.job.name}")
    private String metricsJobName;

    @Value("${metrics.pushgateway.url}")
    private String pushgatewayUrl;

    @PostConstruct
    public void init() {
        pushGateway = new PushGateway(pushgatewayUrl);
        groupingKey.put(metricsGroupingKey, metricsJobName);
        PrometheusMeterRegistry prometheusMeterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        collectorRegistry = prometheusMeterRegistry.getPrometheusRegistry();
        Metrics.globalRegistry.add(prometheusMeterRegistry);
    }
    @Scheduled(fixedRateString = "${metrics.pushrate}")
    public void pushMetrics() {
        try {
            pushGateway.pushAdd(collectorRegistry, metricsJobName, groupingKey);
        } catch (Throwable ex) {
            System.err.println("Unable to push metrics to Prometheus Push Gateway");
            ex.printStackTrace();
        }
    }

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler taskScheduler(@Value("${thread.pool.size}") int threadPoolSize) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(threadPoolSize);
        return threadPoolTaskScheduler;
    }
}
