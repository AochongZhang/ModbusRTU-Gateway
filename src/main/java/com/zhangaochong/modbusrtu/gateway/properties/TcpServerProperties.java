package com.zhangaochong.modbusrtu.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * tcp服务配置
 *
 * @author Aochong Zhang
 */
@Data
@Component
@ConfigurationProperties(prefix = "tcp-server")
public class TcpServerProperties {
    /**
     * tcp服务启动端口, 默认9000
     */
    private Integer port = 9000;
}
