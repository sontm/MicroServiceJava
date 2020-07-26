FROM openjdk:8-jre
MAINTAINER sansanvn

# add user
#RUN groupadd pipe
#RUN useradd pipe -g pipe -m -s /bin/bash
#RUN echo "pipe:dream" | chpasswd

# see https://spring.io/guides/gs/spring-boot-docker/
VOLUME /tmp
ADD ./target/hibertest-0.0.1-SNAPSHOT.war app.war
RUN sh -c 'touch /app.war'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.war" ]

