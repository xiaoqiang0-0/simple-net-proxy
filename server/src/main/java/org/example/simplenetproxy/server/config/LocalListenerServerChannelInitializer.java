package org.example.simplenetproxy.server.config;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.example.simplenetproxy.core.auth.AuthPacketDecoder;
import org.example.simplenetproxy.core.auth.AuthPacketEncoder;
import org.example.simplenetproxy.core.protocol.PacketDecoder;
import org.example.simplenetproxy.core.protocol.PacketEncoder;
import org.example.simplenetproxy.core.proxy.ProxyPacketDecoder;
import org.example.simplenetproxy.core.proxy.ProxyPacketEncoder;
import org.example.simplenetproxy.server.auth.LocalAuthHandler;
import org.example.simplenetproxy.server.handler.ServerLocalMsgHandler;
import org.springframework.stereotype.Component;

@Component
public class LocalListenerServerChannelInitializer extends ChannelInitializer {
    private final LocalAuthHandler authHandler;


    private final AuthPacketEncoder authPacketEncoder;
    private final AuthPacketDecoder authPacketDecoder;

    private final PacketEncoder packetEncoder;
    private final PacketDecoder packetDecoder;


    public LocalListenerServerChannelInitializer(LocalAuthHandler authHandler, AuthPacketEncoder authPacketEncoder, AuthPacketDecoder authPacketDecoder, PacketEncoder packetEncoder, PacketDecoder packetDecoder) {
        this.authHandler = authHandler;
        this.authPacketEncoder = authPacketEncoder;
        this.authPacketDecoder = authPacketDecoder;
        this.packetEncoder = packetEncoder;
        this.packetDecoder = packetDecoder;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast("packetEncoder", packetEncoder);
        ch.pipeline().addLast("authPacketEncoder", authPacketEncoder);

        ch.pipeline().addLast("packetDecoder", packetDecoder);
        ch.pipeline().addLast("authPacketDecoder", authPacketDecoder);
        ch.pipeline().addLast("authHandler", authHandler);
    }
}
