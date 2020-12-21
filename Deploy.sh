#!/bin/sh

# 项目名称
PROJECT_NAME="talent-six-service"

# 使用mvn help 获取项目版本号
TAG=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

# 创建docker镜像
sudo docker build --build-arg TAG=$TAG -t $PROJECT_NAME:$TAG .

# 停止docker容器服务 && 删除docker容器服务
sudo docker stop $PROJECT_NAME && sudo docker rm $PROJECT_NAME

# 启动docker容器
# -d 后台启动
# -p 设置端口（宿主机对外端口:docker内部端口）
# --name 设置别名
# --restart=always docker启动，容器自启动
# --net=host  使用宿主机网络  否则注册中心获取ip是错误的
# -v /talentSix/logs/$PROJECT_NAME:/logs  挂载日志文件到宿主机
sudo docker run -d --net=host --name $PROJECT_NAME --restart=always -v /talentSix/logs/$PROJECT_NAME:/logs $PROJECT_NAME:$TAG

# 删除无用的docker镜像
sudo docker images | grep none | awk '{print $3}' |xargs -i sudo docker rmi {