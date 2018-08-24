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

    服务提供者：ServiceProviderApp（package: com.yee.study.spring.cloud.feign.provider）
    
    服务消费者：ServiceConsumerApp（package: com.yee.study.spring.cloud.feign.consumer）
    

5.  Hystrix示例：
    包com.yee.study.spring.cloud.hystrix
    
    Eureka服务（注册中心）: 启动 com.yee.study.spring.cloud.eureka.server.EurekaServerApp 作为单节点服务。
    
    服务提供者：ServiceProviderApp（package: com.yee.study.spring.cloud.ribbon.provider），启动1-2个提供者（启动的Program Agrs中用1，2）
    
    服务消费者：ServiceConsumerApp（package: com.yee.study.spring.cloud.hystrix.consumer）

6.  Zuul示例：
    包com.yee.study.spring.cloud.zuul
    
    Eureka服务（注册中心）: 启动 com.yee.study.spring.cloud.eureka.server.EurekaServerApp 作为单节点服务。
    
    服务提供者：ServiceProviderApp（package: com.yee.study.spring.cloud.ribbon.provider），启动1-2个提供者（启动的Program Agrs中用1，2）
    
    服务消费者：ServiceConsumerApp（package: com.yee.study.spring.cloud.ribbon.consumer）

    服务网关：GatewayApp（package: com.yee.study.spring.cloud.zuul.gateway）
    
    过滤器：PreRequestFilter（package: com.yee.study.spring.cloud.zuul.gateway.filter）
           是一个前置的请求过滤器（本例中用于在接受到请求后打印出相应的request内容）

    测试：
        1. 加载 gateway.properties 文件，可以通过以下路径访问到相应的服务提供者、消费者。
            http://localhost:8071/service-consumer/user/1，
            http://localhost:8071/service-provider/user/1
        2. 加载 gateway-routes.propeties 文件，增加了路由的配置(对service-consumer增加了映射，忽略了service-provider) 
        	http://localhost:8071/consumer/user/1（配置了路由映射，可以访问）
        	http://localhost:8071/service-provider/user/1（可以访问）
        	http://localhost:8071/service-provider/user/1（无法访问，因为在路由中被忽略）

