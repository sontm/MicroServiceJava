FROM openjdk:8-jre
MAINTAINER sansanvn

# see https://spring.io/guides/gs/spring-boot-docker/
VOLUME /tmp
ADD ./target/accountservice-0.0.1.war app.war
RUN sh -c 'touch /app.war'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.war" ]

