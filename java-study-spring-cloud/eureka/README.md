Eureka注册示例：
包com.yee.study.spring.cloud.eureka

Server: com.yee.study.spring.cloud.eureka.server.EurekaServerApplication
Client: com.yee.study.spring.cloud.eureka.client.EurekaClientApplication

1. 单节点Eureka服务
    server：单节点的Eureka服务，使用spring.profiles.active=eureka-server
    client：使用spring.profiles.active=eureka-client

2. 多节点Eureka服务
    本例一共配置了4个Eureka服务（同一个Region，2个zone中）
    使用spring.profiles.active=eureka-server-zone1a
    使用spring.profiles.active=eureka-server-zone1b
    使用spring.profiles.active=eureka-server-zone2a
    使用spring.profiles.active=eureka-server-zone2b

    client：启动2个Client服务，分别在zone1和zone2中分别向服务端注册
     
