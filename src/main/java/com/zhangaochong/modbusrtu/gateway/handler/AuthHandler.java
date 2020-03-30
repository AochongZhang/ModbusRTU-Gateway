package com.zhangaochong.modbusrtu.gateway.handler;

import com.zhangaochong.modbusrtu.gateway.constant.ChannelConstant;
import com.zhangaochong.modbusrtu.gateway.method.AuthMethod;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端连接验证
 *
 * @author Aochong Zhang
 */
@Slf4j
public class AuthHandler extends ChannelInboundHandlerAdapter {
    private AuthMethod authMethod;

    public AuthHandler(AuthMethod authMethod) {
        this.authMethod = authMethod;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String clientId = authMethod.checkAuth(ctx, msg);
        if (clientId != null) {
            log.info("[连接验证] 成功 addr = {}, clientId = {}", ctx.channel().remoteAddress(), clientId);
            // 添加客户端ID与Channel绑定
            ChannelConstant.set(clientId, ctx.channel());
            // 移除验证拦截
            ctx.pipeline().remove(this);
        } else {
            log.warn("[连接验证] 失败 addr = {}", ctx.channel().remoteAddress());
            ctx.close();
        }
    }
}
