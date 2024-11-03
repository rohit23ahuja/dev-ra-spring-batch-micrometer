package dev.ra.springbatch.micrometer;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CricketCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String cricketJobEnabled = System.getProperty("cricketEnabled");
        return "true".equalsIgnoreCase(cricketJobEnabled);
    }
}
