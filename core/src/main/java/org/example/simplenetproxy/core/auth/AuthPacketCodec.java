package org.example.simplenetproxy.core.auth;

import io.netty.channel.CombinedChannelDuplexHandler;

public class AuthPacketCodec extends CombinedChannelDuplexHandler<AuthPacketDecoder, AuthPacketEncoder> {
    public AuthPacketCodec(AuthPacketDecoder inboundHandler, AuthPacketEncoder outboundHandler) {
        super(inboundHandler, outboundHandler);
    }
}
