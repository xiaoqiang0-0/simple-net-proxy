package org.example.simplenetproxy.client.config;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.example.simplenetproxy.client.auth.AuthHandler;
import org.example.simplenetproxy.client.proxy.ProxyHandler;
import org.example.simplenetproxy.core.auth.AuthPacketCodec;
import org.example.simplenetproxy.core.auth.AuthPacketDecoder;
import org.example.simplenetproxy.core.auth.AuthPacketEncoder;
import org.example.simplenetproxy.core.protocol.PacketCodec;
import org.example.simplenetproxy.core.protocol.PacketDecoder;
import org.example.simplenetproxy.core.protocol.PacketEncoder;
import org.example.simplenetproxy.core.proxy.ProxyPacketCodec;
import org.springframework.stereotype.Component;

@Component
public class LocalProxyServerChannelInitializer extends ChannelInitializer {
    private final AuthPacketEncoder authPacketEncoder;
    private final AuthPacketDecoder authPacketDecoder;
    private final PacketDecoder packetDecoder;
    private final PacketEncoder packetEncoder;
    private final AuthHandler authHandler;

    public LocalProxyServerChannelInitializer(AuthPacketEncoder authPacketEncoder, AuthPacketDecoder authPacketDecoder, PacketDecoder packetDecoder, PacketEncoder packetEncoder, AuthHandler authHandler) {
        this.authPacketEncoder = authPacketEncoder;
        this.authPacketDecoder = authPacketDecoder;
        this.packetDecoder = packetDecoder;
        this.packetEncoder = packetEncoder;
        this.authHandler = authHandler;
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
