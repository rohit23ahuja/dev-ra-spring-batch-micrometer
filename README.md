# dev-ra-spring-batch-micrometer
Spring framework + Spring batch using xml based configuration and integration with micrometer.

Steps will follow to build this project :-
1. create an executable maven fat jar project using assembly plugin -- done 16 august
2. create a spring xml config project -- done 17 august
3. integrate postgres and spring using xml config - done 17 august
4. set up spring spring batch xml config project 
5. integate micrometer in above 
6. integrate and learn how prometheus will come into picture 
7. set up grafana on local and dashboard config in the project

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
