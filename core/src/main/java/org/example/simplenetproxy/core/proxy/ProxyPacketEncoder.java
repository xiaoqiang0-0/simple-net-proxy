package org.example.simplenetproxy.core.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.example.simplenetproxy.core.protocol.Packet;

import java.util.List;

import static org.example.simplenetproxy.core.protocol.Packet.*;
import static org.example.simplenetproxy.core.proxy.ProxyPacket.PROXY_MSG_STATE_SUCCESS;
import static org.example.simplenetproxy.core.proxy.ProxyPacket.SHORT_CHANNEL_ID_LEN;

public class ProxyPacketEncoder extends MessageToMessageEncoder<ProxyPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ProxyPacket msg, List<Object> out) throws Exception {
        Packet packet = new Packet();
        packet.setId(0);
        packet.setType(TYPE_DATA);
        packet.setState(msg.getState() == PROXY_MSG_STATE_SUCCESS ? STATE_OK : STATE_ERROR);
        packet.setLength(SHORT_CHANNEL_ID_LEN + 1 + 4 + msg.getLength());
        packet.setBody(msg.toByteBuf());
    }
}
