# dev-ra-spring-batch-micrometer
Spring framework + Spring batch using xml based configuration and integration with micrometer.

Steps will follow to build this project :-
1. create an executable maven fat jar project using assembly plugin -- done 16 august
2. create a spring xml config project -- done 17 august
3. set up spring spring batch xml config project
4. integate micrometer in above
5. integrate and learn how prometheus will come into picture 
6. set up grafana on local and dashboard config in the project

# spring xml based config
we can configure spring application using xml configs as well. earlier beans in spring config xml were configured using `<bean>` tag.
but later in spring 2, xml schema based config was introduced to make the config concise, easy to read. This means use to various tags e.g. `<util>`

https://stackoverflow.com/questions/68731932/are-we-not-encouraged-to-write-xml-configuration-using-maven-spring
https://www.studytonight.com/spring-framework/spring-xml-based-configuration

one negative aspect of doing xml based config - it is does not provide compile time safety. lets say for a property value you have the wrong data type - in java based config the ide will complaint during compile time itself.