package dev.ra.springbatch.micrometer;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.PushGateway;

import java.util.HashMap;
import java.util.Map;

public class PrometheusConfiguration {


    private CollectorRegistry collectorRegistry;

    private PushGateway pushGateway;
    private String prometheusJobName;
    private final Map<String, String> groupingKey = new HashMap<>();



    public void init() {
        pushGateway = new PushGateway("localhost:9091");
        groupingKey.put("appname", "springbatch");
        PrometheusMeterRegistry prometheusMeterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        collectorRegistry = prometheusMeterRegistry.getPrometheusRegistry();
        Metrics.globalRegistry.add(prometheusMeterRegistry);
    }
    public void pushMetrics() {
        try {
            pushGateway.pushAdd(collectorRegistry, "springbatch", groupingKey);
        }
        catch (Throwable ex) {
            System.err.println("Unable to push metrics to Prometheus Push Gateway");
            ex.printStackTrace();
        }
    }
}
