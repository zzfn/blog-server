FROM docker.io/java
ENV TZ=Asia/Shanghai
RUN /bin/cp /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ADD scaffold-0.0.1-SNAPSHOT.jar scaffold.jar
VOLUME /tmp
VOLUME /home/wordToHtml
VOLUME /home/file
ENV JAVA_OPTS="-Dspring.profiles.active=test"
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/scaffold.jar"]
#容器启动后需要执行的命令
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar scaffold.jar"]
EXPOSE 8080
