package org.example.simplenetproxy.core.auth;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class AuthPacketEncoder extends MessageToByteEncoder<AuthPacket> {
    @Override
    protected void encode(ChannelHandlerContext ctx, AuthPacket msg, ByteBuf out) throws Exception {

    }
}
