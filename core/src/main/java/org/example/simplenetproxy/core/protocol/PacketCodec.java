package org.example.simplenetproxy.core.protocol;

import io.netty.channel.CombinedChannelDuplexHandler;

public class PacketCodec extends CombinedChannelDuplexHandler<PacketDecoder, PacketEncoder> {
    public PacketCodec(PacketDecoder inboundHandler, PacketEncoder outboundHandler) {
        super(inboundHandler, outboundHandler);
    }
}
