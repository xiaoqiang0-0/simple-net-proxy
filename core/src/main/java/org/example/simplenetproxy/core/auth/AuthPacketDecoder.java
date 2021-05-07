package org.example.simplenetproxy.core.auth;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.example.simplenetproxy.core.protocol.Packet;

import java.util.List;

public class AuthPacketDecoder extends MessageToMessageDecoder<Packet> {
    @Override
    protected void decode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {

    }
}
