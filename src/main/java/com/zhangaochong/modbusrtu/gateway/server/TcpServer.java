package com.zhangaochong.modbusrtu.gateway.server;

import com.zhangaochong.modbusrtu.gateway.handler.TcpServerChannelInitializer;
import com.zhangaochong.modbusrtu.gateway.properties.TcpServerProperties;
import com.zhangaochong.modbusrtu.gateway.util.SpringContextUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * TCP服务器，与设备间通信
 *
 * @author Aochong Zhang
 */
@Slf4j
public class TcpServer {
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            TcpServerProperties tcpServerProperties = SpringContextUtils.getBean(TcpServerProperties.class);
            Integer port = tcpServerProperties.getPort();

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .localAddress(new InetSocketAddress(port))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TcpServerChannelInitializer());

            ChannelFuture future = bootstrap.bind().sync();
            future.addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isSuccess()) {
                    log.info("[ModbusRtuGateway] tcp服务启动成功 port={}", port);
                } else {
                    log.error("[ModbusRtuGateway] tcp服务启动失败 port={}", port);
                    channelFuture.cause().printStackTrace();
                }
            });
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
