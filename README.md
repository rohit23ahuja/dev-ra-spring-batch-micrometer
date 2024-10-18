# dev-ra-spring-batch-micrometer
Spring framework + Spring batch using xml based configuration and integration with micrometer.

Steps will follow to build this project :-
1. create an executable maven fat jar project using assembly plugin -- done 16 august
2. create a spring xml config project -- done 17 august
3. integrate postgres and spring using xml config - done 17 august
4. set up spring spring batch xml config project - done 17 august
5. out of the box micrometer metrics 
6. install and configure prometheus 
7. set up grafana on local and dashboard config in the project
8. define a custon and interesting spring batch job

# spring xml based config
spring uses java reflection feature to create beans/objects etc. xml based config or java based are just two ways to tell Spring what type of beans we want.  
we can configure spring application using xml configs as well. earlier beans in spring config xml were configured using `<bean>` tag.
but later in spring 2, xml schema based config was introduced to make the config concise, easy to read. This means use to various tags e.g. `<util>`.
`ClassPathXmlApplicationContext` accepts the path of a XML file for beans to create. can use xml based with spring boot as well. 
https://stackoverflow.com/questions/68731932/are-we-not-encouraged-to-write-xml-configuration-using-maven-spring
https://www.studytonight.com/spring-framework/spring-xml-based-configuration

one negative aspect of doing xml based config - it is does not provide compile time safety. lets say for a property value you have the wrong data type - in java based config the ide will complaint during compile time itself. java or annotation based config is better than managing large xml based files. annotation based config uses a class marked `@Configuration` for beans to create.

# spring postgres integration
installed and configured postgres on machine
set up `footballdb` on my machine
defined `dataSource` bean in `context.xml`
added maven dependencies of postgres driver and spring jdbc

# spring batch xml
set up spring batch using xml config. since the objective of this project is to focus spring batch micrometer integratio. For now have set up football job defined in spring batch samples project.
https://github.com/spring-projects/spring-batch/tree/main/spring-batch-samples/src/main/java/org/springframework/batch/samples/football
https://www.digitalocean.com/community/tutorials/spring-batch-example

# out of the box micrometer metrics
java instrumentation is a feature that allows extra code to be executed while our application is getting executed. How 
it works is - you create a java agent separate from your application and you pass this java agent when executing your 
application jar. This technique is used by various profiling and monitoring applications and libraries such as jacoco
etc.
https://medium.com/javarevisited/what-is-java-instrumentation-why-is-it-needed-1f9aa467433

Micrometer is a metrics instrumentation library for JVM based applications. Its a facade over various instrumentation 
clients related to various monitoring systems such as prometheus
https://docs.micrometer.io/micrometer/reference/concepts.html

# prometheus configuration and integration
Prometheus is a monitoring platform that collects metrics from monitored targets by scraping metrics HTTP endpoints on
these targets. 
https://prometheus.io/docs/introduction/first_steps/
https://stackoverflow.com/questions/66659203/publishing-spring-batch-metrics-using-micrometer
installed prometheus push gateway
installed and configured prometheus
installed grafana

#prometheus without micrometer and push gateway
links to read to setup correctly:-
https://prometheus.github.io/client_java/instrumentation/jvm/
https://github.com/prometheus/pushgateway
https://stackoverflow.com/questions/56594029/how-to-instrument-java-application-code-metrics-to-prometheus?rq=3
https://stackoverflow.com/questions/68753082/pushing-metrics-to-pushgateway-replaces-the-old-one
https://stackoverflow.com/questions/53728336/instrumenting-a-java-application-with-prometheus?noredirect=1&lq=1
https://stackoverflow.com/questions/62832012/how-to-collect-the-prometheus-metrics-in-a-java-program?rq=3
https://stackoverflow.com/questions/46277332/how-to-correctly-use-prometheus-histogram-from-java-client-to-track-size-rather?rq=3
https://stackoverflow.com/questions/53971589/instrumenting-prometheus-metrics-with-java-client?rq=3
https://stackoverflow.com/questions/67385735/metric-to-use-for-job-duration-in-prometheus-gauge-or-summary/67395590#67395590
https://stackoverflow.com/questions/55395769/micrometer-metrics-with-spring-java-no-spring-boot