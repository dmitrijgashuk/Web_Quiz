FROM maven:3.8-amazoncorretto:11 AS MAVEN_BUILD
COPY ./ ./
RUN mvn clean package

FROM tomcat:9-jdk11-corretto
COPY --from=MAVEN_BUILD /target/ROOT.war /usr/local/tomcat/webapps/ROOT.war
