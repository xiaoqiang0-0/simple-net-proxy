package org.example.simplenetproxy.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.simplenetproxy.server.management.TransformManagement;
import org.example.simplenetproxy.server.utils.HostParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        for (ByteBuf byteBuf : getMsg(buf)) {
            channel.write(byteBuf);
        }
        channel.flush();
    }

    public CompositeByteBuf getMsg(ByteBuf body){
        return null;
    }
}
