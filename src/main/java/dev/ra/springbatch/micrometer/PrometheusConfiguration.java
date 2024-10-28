package dev.ra.springbatch.micrometer;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.PushGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableScheduling
public class PrometheusConfiguration {


    private CollectorRegistry collectorRegistry;

    private PushGateway pushGateway;
    private String prometheusJobName;
    private final Map<String, String> groupingKey = new HashMap<>();

    @PostConstruct
    public void init() {
        pushGateway = new PushGateway("localhost:9091");
        groupingKey.put("appname", "springbatch");
        PrometheusMeterRegistry prometheusMeterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        collectorRegistry = prometheusMeterRegistry.getPrometheusRegistry();
        Metrics.globalRegistry.add(prometheusMeterRegistry);
    }
    @Scheduled(fixedRateString = "1000")
    public void pushMetrics() {
        try {
            pushGateway.pushAdd(collectorRegistry, "springbatch", groupingKey);
        } catch (Throwable ex) {
            System.err.println("Unable to push metrics to Prometheus Push Gateway");
            ex.printStackTrace();
        }
    }

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
        return threadPoolTaskScheduler;
    }
}
