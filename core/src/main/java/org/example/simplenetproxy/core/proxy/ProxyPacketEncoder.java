package org.example.simplenetproxy.core.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProxyPacketEncoder extends MessageToByteEncoder<ProxyPacket> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ProxyPacket msg, ByteBuf out) throws Exception {

    }
}
