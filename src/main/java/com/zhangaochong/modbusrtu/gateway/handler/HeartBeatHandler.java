package com.zhangaochong.modbusrtu.gateway.handler;

import com.zhangaochong.modbusrtu.gateway.constant.ChannelConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳处理
 *
 * @author Aochong Zhang
 */
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        // 定义1字节数据为心跳包
        if (byteBuf.readableBytes() == 1) {
            log.debug("[心跳] data = {}", byteBuf.readByte());
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                log.warn("[设备空闲] clientId = {}", ChannelConstant.getIdByChannel(ctx.channel()));
                ctx.close();
            }
        }
    }
}
