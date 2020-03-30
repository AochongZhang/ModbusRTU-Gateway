package com.zhangaochong.modbusrtu.gateway.method;

import io.netty.channel.ChannelHandlerContext;

@FunctionalInterface
public interface AuthMethod {
    String checkAuth(ChannelHandlerContext ctx, Object msg);
}
