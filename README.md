# Best Cloud

### 介绍

这个一个基础架构项目，以目前最新的技术栈输出最好的示例，以实现拿来即用的开发框架脚手架。项目结构主要采用微服务 Spring Cloud 系的技术栈实现，是一个非常好用的轮子，欢迎大家指错，一起进步。

* [~~后台项目地址~~](https://github.com/shanzhaozhen/best-server)
* [~~Spring Cloud实现项目地址~~](https://github.com/shanzhaozhen/best-cloud)
* [~~前端项目地址~~](https://github.com/shanzhaozhen/best-client)
* [~~Demo站点~~](http://best.loogoos.tk)
    > 仅支持ipv6用户访问，因为demo站点是使用我私有的小服务器搭建的的ipv6的方式映射的，[详见：我另外一个仓库（暂时没完善）](https://github.com/shanzhaozhen/MyNAS)
    > 默认账号：admin
    > 默认密码：123456

_没条件搞服务器都挂了_

### 功能实现
|                  实现功能                  | 是否已实现 |
|:--------------------------------------:|:-----:|
|           Spring Cloud 微服务化            |   √   |
|              整合 OAuth2.1               |   √   |
| ~~增加 Password 认证方式~~ (有需要请看password分支) | ~~√~~ |
|            OAuth2.1 加入JWT增强            |   √   |
|              RBAC 动态权限管理               |   √   |
|                 动态分配菜单                 |   √   |
|            整合服务熔断 Sentinel             |   -   |
|            整合消息队列 RocketMQ             |   -   |
|         整合查询中间件 Elasticsearch          |   -   |
|             openapi文档查看、导出             |   √   |
|               实现分布式定时任务                |   -   |
|                加入分布式事务                 |   -   |
|             引入分布式存储 MinIO              |   √   |
|           加入 OAuth2 实现第三方登陆            |   -   |
|         加入Dockerfile直接打包部署到服务器         |   -   |
|               实现低代码流程引擎                |   -   |


### 微服务组件选型

| 组件功能  |    组件    |
|:-----:|:--------:|
| 服务发现  |  Nacos   |
| 配置中心  |  Nacos   |
| 服务熔断  | Sentinel |
| 分布式事务 |  Seata   |
| 消息队列  | RocketMQ |

### 版本说明

[Spring Cloud Alibaba 版本说明](https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E)

### 相关技术
该项目使用的相关技术/工具主要有:
* Spring Cloud / Spring Cloud Alibaba
* Spring Boot
* Spring Security
* OAuth2.1
* JWT
* React
* Typescript & ES6
* Redis
* openapi
* Docker

### 版本要求

1. JDK 1.8
2. Gradle 7.0+
3. Node.JS 16.x.x

### 项目路径

```lua
best-cloud -- 父项目,各模块分离，方便集成和微服务
│  │─authorize  -- 授权服务器
│  │  ├─authorize-server -- 授权服务器-后台应用
│  │  ├─authorize-web -- 授权服务器-前端模块
│  │─client -- 前端应用
│  ├─common -- 核心通用模块，主模块
│  │  ├─common-core -- 封装通用模块
│  │  ├─common-minio -- 封装 minio 配置模块
│  │  ├─common-mybatis -- 封装 mybatis 配置模块
│  │  ├─common-redis -- 封装 redis 配置模块
│  │  ├─common-web -- 封装 web 常用基础模块
│  │─doc -- 文档
│  │─gateway -- 统一网关模块 [8088]
│  │─generator -- 代码生成器
│  │─authorize -- 统一认证中心模块 [9000]
│  │─uaa -- 系统用户管理模块，主模块
│  │  ├─uaa-api -- 系统用户管理的通用模块，供其他模块引用
│  │  ├─uaa-biz -- 系统用户管理模块核心功能 [9500]
```


### 建表语句
基础服务建表语句:
在sql文件夹下

oauth2授权信息持久化建表：
https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql

生成密钥库jwt.jks
```(shell script)
keytool -genkey -alias best-cloud -keyalg RSA -keypass 123456 -storepass 123456 -keystore jwt.jks

# genkey 生成密钥
# alias 别名
# keyalg 密钥算法
# keypass 密钥口令
# keystore 生成密钥库的存储路径和名称
# storepass 密钥库口令
```

### 环境要求



### 执行指南

#### 1. 启动 Nacos ([详细文档](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-config-example/readme-zh.md))

1. 下载/更新源码或下载发行包，[详细点击查看](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-config-example/readme-zh.md)
   1. 直接下载：[Nacos Server 下载页](https://github.com/alibaba/nacos/releases)
   2. 源码构建：进入 Nacos [Github 项目页面](https://github.com/alibaba/nacos) ，将代码 git clone 到本地自行编译打包，[参考此文档](https://nacos.io/zh-cn/docs/quick-start.html) 。

2. 启动Nacos

Linux/Unix/Mac 启动命令(standalone代表着单机模式运行，非集群模式):
`sh startup.sh -m standalone`

如果您使用的是ubuntu系统，或者运行脚本报错提示[[符号找不到，可尝试如下运行：
`bash startup.sh -m standalone`

Windows 启动命令(standalone代表着单机模式运行，非集群模式):
`startup.cmd -m standalone`

* Docker 启动

```shell
mkdir -p /home/nacos/logs/                      #新建logs目录
mkdir -p /home/nacos/init.d/
vim /home/nacos/init.d/custom.properties        #修改配置文件

git clone https://github.com/nacos-group/nacos-docker.git
cd nacos-docker

# 单机模式 Derby
docker-compose -f example/standalone-derby.yaml up

# 如果希望使用MySQL5.7
docker-compose -f example/standalone-mysql-5.7.yaml up

# 如果希望使用MySQL8
docker-compose -f example/standalone-mysql-8.yaml up

# 集群模式
docker-compose -f example/cluster-hostname.yaml up 

docker run -d \
  --restart=always \
  --name nacos-standalone \
  -e MODE=standalone \
  -p 8848:8848 \
  nacos/nacos-server:latest
```

* K8S
```shell
git clone https://github.com/nacos-group/nacos-k8s.git
```

#### 2. 启动 Sentinel ([详细文档](http://edas-public.oss-cn-hangzhou.aliyuncs.com/install_package/demo/sentinel-dashboard.jar))

1. 首先需要获取 Sentinel 控制台，支持直接下载和源码构建两种方式。

   1. 直接下载：[下载 Sentinel 控制台](http://edas-public.oss-cn-hangzhou.aliyuncs.com/install_package/demo/sentinel-dashboard.jar)
   2. 源码构建：进入 Sentinel [Github 项目页面](https://github.com/alibaba/Sentinel) ，将代码 git clone 到本地自行编译打包，[参考此文档](https://github.com/alibaba/Sentinel/tree/master/sentinel-dashboard) 。

2. 启动控制台，执行 Java 命令 `java -jar sentinel-dashboard.jar`完成 Sentinel 控制台的启动。
   控制台默认的监听端口为 8080。Sentinel 控制台使用 Spring Boot 编程模型开发，如果需要指定其他端口，请使用 Spring Boot 容器配置的标准方式，详情请参考 [Spring Boot 文档](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-customizing-embedded-containers) 。

* Docker 启动

  Sentinel 没有官方的镜像，所以使用Dockerfile构建镜像`(请看路径/middleware/sentinel)`

    ```shell
    docker run \
      --name sentinel \
      -p 18080:18080 \
      -d sentinel-dashboard:1.8.0
    ```
  

### 启动分布式存储 MinIO

- [文档](https://docs.min.io)
- Docker 启动

```shell
# 创建一个存储的文件夹进行映射，我这里的环境是使用虚拟机，直接用硬盘映射到虚拟机测试
# 默认账号密码：minioadmin:minioadmin
mkdir -p /root/docker/minio/{data,config}

docker run -d \
    --restart always \
    -p 9100:9000 \
    -p 9101:9001 \
    -v /root/docker/minio/data:/data \
    -v /root/docker/minio/config:/root/.minio \
    -e "MINIO_ROOT_USER=minioadmin" \
    -e "MINIO_ROOT_PASSWORD=minioadmin" \
    quay.io/minio/minio server /data --console-address ":9001"

```


### RSA证书

```shell
# 生成RSA证书
# -genkey 生成密钥
# -alias 别名
# -keyalg 密钥算法
# -keypass 密钥口令
# -keystore 生成密钥库的存储路径和名称
# -storepass 密钥库口令

keytool -genkey -alias jwt -keyalg RSA -keystore jwt.jks

keytool -genkey -alias <alias> -keyalg RSA -keypass <keypass> -keystore <filename>.jks -storepass <storepass>

```


