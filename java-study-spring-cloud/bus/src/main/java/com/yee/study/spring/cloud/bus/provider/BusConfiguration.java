package com.yee.study.spring.cloud.bus.provider;

import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;

/**
 * 消息总线配置类
 *
 * @author Roger.Yi
 */
@Configuration
@RemoteApplicationEventScan(basePackages = "com.yee.study.spring.cloud.bus.provider.event")
public class BusConfiguration {
}
