package dev.ra.springbatch.micrometer;


import io.prometheus.metrics.exporter.pushgateway.PushGateway;

public class PrometheusPushGatewayConfiguration {

    private PushGateway pushGateway;

    public void init() {
        pushGateway = PushGateway.builder().job("playersummary").build();
    }

    public void pushMetrics() {
        try {
            pushGateway.push();
        } catch (Throwable ex) {
            System.err.println("Unable to push metrics to Prometheus Push Gateway");
            ex.printStackTrace();
        }
    }
}
