package com.zhangaochong.modbusrtu.gateway.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

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
                .addLast(new LoggingHandler())
                // 状态监听
                .addLast(new StatusHandler())
                // 连接验证
                .addLast(new AuthHandler(((ctx, msg) -> ((ByteBuf) msg).toString(CharsetUtil.UTF_8))))
                // 心跳配置
                .addLast(new IdleStateHandler(10, 10, 10))
                // 心跳处理
                .addLast(new HeartBeatHandler())
                // 异常处理
                .addLast(new ExceptionCaughtHandler());
    }
}
