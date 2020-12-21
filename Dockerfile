
#使用镜像
FROM ccr.ccs.tencentyun.com/expo/openjdk:8-jdk-alpine

#作者
MAINTAINER mnufs

#版本号
ARG TAG

#复制项目文件到容器内
COPY ./target/talent-six-service-exec-$TAG.jar app.jar

#设置时区和时间
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

#端口号
EXPOSE 19999

#启动项目
ENTRYPOINT java -Xms64m -Xmx64m -jar /app.jar