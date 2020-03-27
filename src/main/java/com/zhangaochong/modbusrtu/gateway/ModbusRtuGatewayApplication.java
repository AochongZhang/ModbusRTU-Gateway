package com.zhangaochong.modbusrtu.gateway;

import com.zhangaochong.modbusrtu.gateway.server.TcpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ModbusRtuGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModbusRtuGatewayApplication.class, args);
        log.info("[ModbusRtuGateway] web服务启动成功");
        new TcpServer().start();
    }

}
