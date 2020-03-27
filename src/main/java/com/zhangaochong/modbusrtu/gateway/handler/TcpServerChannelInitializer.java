package com.zhangaochong.modbusrtu.gateway.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LoggingHandler;

/**
 * 处理器配置
 *
 * @author Aochong Zhang
 */
public class TcpServerChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                // 打印日志 DEBUG
                .addLast(new LoggingHandler());
    }
}
