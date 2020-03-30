package com.zhangaochong.modbusrtu.gateway.handler;

import com.zhangaochong.modbusrtu.gateway.constant.ChannelConstant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端连接状态
 *
 * @author Aochong Zhang
 */
@Slf4j
public class StatusHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("[设备连接] addr = {}", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String clientId = ChannelConstant.getIdByChannel(ctx.channel());
        log.info("[设备断开] addr = {}, clientId = {}", ctx.channel().remoteAddress(), clientId);
        if (clientId != null) {
            ChannelConstant.remove(clientId);
        }
    }
}
