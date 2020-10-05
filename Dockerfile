#
# Build stage
#
FROM maven:3.6.3-openjdk-8
COPY src/ src/
COPY pom.xml .
RUN mvn clean install site

#
# Package stage
#
FROM tomcat
COPY --from=0 target/kvachwebapp.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]
