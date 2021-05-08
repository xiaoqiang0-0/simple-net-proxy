package org.example.simplenetproxy.core.auth;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.example.simplenetproxy.core.protocol.Packet;

import java.util.List;

import static org.example.simplenetproxy.core.protocol.Packet.STATE_OK;
import static org.example.simplenetproxy.core.protocol.Packet.TYPE_AUTH;

public class AuthPacketEncoder extends MessageToMessageEncoder<AuthPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AuthPacket msg, List<Object> out) throws Exception {
        Packet packet = new Packet();
        packet.setId(0);
        packet.setType(TYPE_AUTH);
        packet.setState(STATE_OK);
        ByteBuf data = msg.toByteBuf();
        packet.setLength(data.readableBytes());
        packet.setBody(data);
        out.add(packet);
    }
}
