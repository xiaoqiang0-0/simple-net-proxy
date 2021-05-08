package org.example.simplenetproxy.core.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.example.simplenetproxy.core.protocol.Packet;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ProxyPacketDecoder extends MessageToMessageDecoder<Packet> {
    @Override
    protected void decode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
        ByteBuf msgBody = msg.getBody();
        ProxyPacket proxyPacket = new ProxyPacket();

        proxyPacket.setRemoteChannelId(msgBody.readCharSequence(8, StandardCharsets.UTF_8).toString());
        proxyPacket.setLength(msgBody.readInt());
        proxyPacket.setBody(msgBody);
        out.add(proxyPacket);
    }
}
