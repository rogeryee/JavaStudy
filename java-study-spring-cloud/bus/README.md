Feign 示例：
包com.yee.study.spring.cloud.feign

Server: com.yee.study.spring.cloud.feign.eureka.EurekaServerApplication
Provider: com.yee.study.spring.cloud.feign.provider.ServiceProviderApplication
Consumer: com.yee.study.spring.cloud.feign.consumer.ServiceConsumerApplication

1. 数据提供者 ServiceProviderApplication
   提供后端数据服务

2. 数据消费者 ServiceConsumerApplication
   消费后端服务，其中的UserClient使用Feign封装了后端数据服务的Restful接口。
     
