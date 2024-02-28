package com.suchtool.nicelog.property;

import lombok.Data;

@Data
public class NiceLogProperty {
    /**
     * 启用日志功能
     */
    private Boolean enabled = true;

    /**
     * 收集所有日志
     * <p>true：自动收集组件支持的所有日志</p>
     * <p>false：不自动收集日志，需要用{@link com.suchtool.nicelog.annotation.NiceLog}注解来启用类或者方法的日志</p>
     */
    private Boolean collectAll = true;

    /**
     * 启用Controller日志
     */
    private Boolean enableControllerLog = true;

    /**
     * 启用XxlJob日志
     */
    private Boolean enableXxlJobLog = true;

    /**
     * 启用RabbitMQ日志
     */
    private Boolean enableRabbitMQLog = true;

    /**
     * 启用NiceLog注解日志
     */
    private Boolean enableNiceLogAnnotationLog = true;

    /**
     * 启用Feign日志
     */
    private Boolean enableFeignLog = true;

    /**
     * 不收集Feign日志的包名，多个用逗号隔开
     */
    private String ignoreFeignLogPackageName;

    /**
     * feign的traceId的header名字
     */
    private String feignTraceIdHeader = "nice-log-trace-id";

    /**
     * 启用RocketMQ日志
     */
    private Boolean enableRocketMQLog = true;
}
