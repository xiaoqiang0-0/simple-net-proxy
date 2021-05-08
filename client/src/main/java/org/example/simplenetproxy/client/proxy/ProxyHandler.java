package org.example.simplenetproxy.client.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.example.simplenetproxy.client.config.AppConfigProperties;
import org.example.simplenetproxy.core.proxy.ProxyPacket;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class ProxyHandler extends SimpleChannelInboundHandler<ProxyPacket> {

    private final AppConfigProperties properties;

    public ProxyHandler(AppConfigProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProxyPacket msg) throws Exception {
        Channel remoteChannel = ctx.channel();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(ctx.channel().eventLoop());
        bootstrap.handler(new SimpleChannelInboundHandler<ByteBuf>() {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf result) throws Exception {
                msg.setBody(result);
                remoteChannel.writeAndFlush(msg);
            }
        });
        bootstrap.connect(new InetSocketAddress(properties.getLocal().getHost(), properties.getLocal().getPort()));
    }
}
