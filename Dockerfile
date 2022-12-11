FROM tomcat:9.0.56-jdk11-corretto

COPY ./target/ROOT.war /usr/local/tomcat/webapps/
