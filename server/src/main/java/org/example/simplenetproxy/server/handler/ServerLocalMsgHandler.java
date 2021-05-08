package org.example.simplenetproxy.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.simplenetproxy.core.proxy.ProxyPacket;
import org.example.simplenetproxy.server.management.TransformManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerLocalMsgHandler extends SimpleChannelInboundHandler<ProxyPacket> {

    @Autowired
    private TransformManagement transformManagement;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProxyPacket msg) throws Exception {
        Channel channel = transformManagement.getRemoteChannel(msg.getRemoteChannelId());
        //TODO 远程通道不存在异常处理

        channel.writeAndFlush(msg.getBody());
    }
}
