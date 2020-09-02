# 该镜像需要依赖的基础镜像
FROM java:8-alpine
# 将当前目录下的jar包复制到docker容器的/目录下
COPY ./target/jello-0.0.1-SNAPSHOT.jar /jello-0.0.1-SNAPSHOT.jar

EXPOSE 8060
ENTRYPOINT ["java", "-jar","/jello-0.0.1-SNAPSHOT.jar"]