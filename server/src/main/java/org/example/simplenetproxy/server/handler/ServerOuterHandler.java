package org.example.simplenetproxy.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.simplenetproxy.core.proxy.ProxyPacket;
import org.example.simplenetproxy.server.management.TransformManagement;
import org.example.simplenetproxy.server.utils.HostParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.example.simplenetproxy.core.proxy.ProxyPacket.PROXY_MSG_STATE_SUCCESS;

@ChannelHandler.Sharable
@Component
public class ServerOuterHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Autowired
    private TransformManagement transformManagement;
    @Autowired
    private HostParser hostParser;


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf buf) throws Exception {
        String host = hostParser.parse(buf);
        transformManagement.registerRemoteChannel(channelHandlerContext.channel());
        Channel channel = transformManagement.getLocalChannel(host);
        //TODO 域名对应本地通道不存在异常处理

        ProxyPacket proxyPacket = new ProxyPacket();
        proxyPacket.setRemoteChannelId(channelHandlerContext.channel().id().asShortText());
        proxyPacket.setState(PROXY_MSG_STATE_SUCCESS);
        proxyPacket.setLength(buf.readableBytes());
        buf.retain();
        proxyPacket.setBody(buf);
        channel.writeAndFlush(proxyPacket);
    }
}
