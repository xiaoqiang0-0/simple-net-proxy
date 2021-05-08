package org.example.simplenetproxy.server.config;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.example.simplenetproxy.core.auth.AuthPacketCodec;
import org.example.simplenetproxy.core.protocol.PacketCodec;
import org.example.simplenetproxy.core.proxy.ProxyPacketCodec;
import org.example.simplenetproxy.server.auth.LocalAuthHandler;
import org.example.simplenetproxy.server.handler.ServerLocalMsgHandler;
import org.springframework.stereotype.Component;

@Component
public class LocalListenerServerChannelInitializer extends ChannelInitializer {
    private final LocalAuthHandler authHandler;
    private final ServerLocalMsgHandler localMsgHandler;
    private final ProxyPacketCodec proxyPacketCodec;
    private final AuthPacketCodec authPacketCodec;
    private final PacketCodec packetCodec;

    public LocalListenerServerChannelInitializer(LocalAuthHandler authHandler, ServerLocalMsgHandler localMsgHandler, ProxyPacketCodec proxyPacketCodec, AuthPacketCodec authPacketCodec, PacketCodec packetCodec) {
        this.authHandler = authHandler;
        this.localMsgHandler = localMsgHandler;
        this.proxyPacketCodec = proxyPacketCodec;
        this.authPacketCodec = authPacketCodec;
        this.packetCodec = packetCodec;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(packetCodec);
        ch.pipeline().addLast(authPacketCodec);
        ch.pipeline().addLast(authHandler);
        ch.pipeline().addLast(proxyPacketCodec);
        ch.pipeline().addLast(localMsgHandler);
    }
}
