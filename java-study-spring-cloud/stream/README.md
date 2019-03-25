Stream 示例：
包com.yee.study.spring.cloud.stream

Server: com.yee.study.spring.cloud.stream.eureka.EurekaServerApplication
Consumer: com.yee.study.spring.cloud.stream.consumer.MessageConsumerApplication

1. StreamClient 定义了消息的Input和Output

2. StreamReceiver 消息的具体操作类，EnableBinding用于绑定所对应的Input和Output，StreamListener用于监听消息的接受
     
