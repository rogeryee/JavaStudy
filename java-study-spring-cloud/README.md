1.  简单的Demo：展示了一个简单的服务提供和消费的示例
    包com.yee.study.spring.cloud.demo
    provider：服务提供方
    consumer：服务消费方

2.  Eureka注册示例：
    包com.yee.study.spring.cloud.eureka
    server：单节点的Eureka服务
    haserver：多节点Eureka服务，需要启动FirstEurekaHAServerApp、SecondEurekaHAServerApp，可以看到两个EurekaServer互为备份
    client：Eureka Client程序，实现了向单节点或者多节点EurekaServer注册（区别在于eureka-client.properties文件中的注册URL）。

3.  Ribbon负载示例：
    包com.yee.study.spring.cloud.ribbon




    
