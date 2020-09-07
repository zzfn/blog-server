#!/usr/bin/env sh

cd /www/wwwroot/api.zzfzzf.com

SERVER_NAME=jello
JAR_NAME=jello-0.0.1-SNAPSHOT
echo "查询进程id-->$SERVER_NAME"
PID=`ps -ef | grep "$SERVER_NAME" | grep -v grep | awk '{print $2}'`
echo "得到进程ID：$PID"
echo "结束进程"
for id in $PID
do
	kill -9 $id
	echo "killed $id"
done
echo "结束进程完成"

cd /www/wwwroot/api.zzfzzf.com
chmod 755 $JAR_NAME.jar
nohup java -jar  $JAR_NAME.jar  &
echo '执行完了构建'

#cd /root
#
#container_name=jello
## docker部署
#docker pull registry.docker.annyyy.com/$container_name
#docker stop $container_name
#docker rm $container_name
#docker run --name=$container_name -d -p 9602:8060 registry.docker.annyyy.com/$container_name
#docker image prune -f

#利用docker-compose部署
#更新镜像
#docker-compose pull
#重新构建方式1
#docker-compose up -d --build
#重新构建方式2
#docker-compose up -d --force-recreate

#docker image prune -f
