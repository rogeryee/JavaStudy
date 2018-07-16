1.  简单的Demo：展示了一个简单的服务提供和消费的示例
    包com.yee.study.spring.cloud.demo

    provider：服务提供方

    consumer：服务消费方
    
    注意：
    1）demo中的ProviderApp和ConsumerApp都没用到Eureka，但是由于pom中引用了Eureka相关的依赖，所以需要启动是增加对EurekaConfiguration的排除；
    2）由于每个项目有自己的yml文件，而不能配置全局的bootstrap.yml文件，所以可以在启动的JVM Args中添加 "-Dspring.cloud.bootstrap.enabled=false"， 这样可以避免程序在启动阶段会去localhost
    :8888 获取默认配置的问题。
    

2.  Eureka注册示例：
    包com.yee.study.spring.cloud.eureka

    server：单节点的Eureka服务

    haserver：多节点Eureka服务，需要启动FirstEurekaHAServerApp、SecondEurekaHAServerApp，可以看到两个EurekaServer互为备份

    client：Eureka Client程序，实现了向单节点或者多节点EurekaServer注册（区别在于eureka-client.properties文件中的注册URL）。
    
    注意：自出没有使用yml的加载方式，因为发现自定义yml加载是在 EurekaInstanceConfigBean初始化之后，所以会导致Eureka初始化时会优先使用eureka.instance.appname (即使配置了spring.application.name)，
    否者会出现服务名为 UNKNOWN的问题。   


3.  Ribbon负载示例：
    包com.yee.study.spring.cloud.ribbon
    
    Eureka服务（注册中心）: 启动 com.yee.study.spring.cloud.eureka.server.EurekaServerApp 作为单节点服务。
    
    provider/ServiceProviderApp：应用服务提供者（启动2个，在启动的Program Agrs中用1，2来分别表示加载service-provider-{args[0]}.properties）
    
    consumer/ServiceConsumerApp：应用服务消费者（访问消费者下的UserController，可以看到通过Ribbon负载来访问不同的ServiceProviderApp）；
    我们可以有两种方式来配置Ribbon，1）通过RibbonConfig类；2）通过配置文件中对Ribbon的配置


4.  Feign示例：
    包com.yee.study.spring.cloud.feign
    
    Eureka服务（注册中心）: 启动 com.yee.study.spring.cloud.eureka.server.EurekaServerApp 作为单节点服务。
    
    provider/ServiceProviderApp：应用服务提供者
    
    consumer/ServiceConsumerApp：应用服务消费者（本例中实现了Get、Post方式的交互）
    
