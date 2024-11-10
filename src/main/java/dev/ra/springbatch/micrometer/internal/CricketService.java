package dev.ra.springbatch.micrometer.internal;

import dev.ra.springbatch.micrometer.PrometheusConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CricketService {
    private static final Logger _log = LoggerFactory.getLogger(CricketService.class);

    public void serviceMethod(String message){
        _log.info(String.format("Parameter received %s", message));
    }
}
