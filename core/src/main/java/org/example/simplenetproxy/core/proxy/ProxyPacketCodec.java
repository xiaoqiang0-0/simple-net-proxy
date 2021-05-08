package org.example.simplenetproxy.core.proxy;

import io.netty.channel.CombinedChannelDuplexHandler;

public class ProxyPacketCodec extends CombinedChannelDuplexHandler<ProxyPacketDecoder, ProxyPacketEncoder> {
    public ProxyPacketCodec(ProxyPacketDecoder inboundHandler, ProxyPacketEncoder outboundHandler) {
        super(inboundHandler, outboundHandler);
    }
}
