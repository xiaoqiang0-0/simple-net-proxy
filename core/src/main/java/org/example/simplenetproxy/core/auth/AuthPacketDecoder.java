package org.example.simplenetproxy.core.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.example.simplenetproxy.core.protocol.Packet;

import java.util.List;

public class AuthPacketDecoder extends MessageToMessageDecoder<Packet> {
    @Override
    protected void decode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
        byte[]data = new byte[msg.getLength()];
        msg.getBody().readBytes(data);
        AuthPacket authPacket = new ObjectMapper().readValue(data, AuthPacket.class);
        out.add(authPacket);
    }
}
