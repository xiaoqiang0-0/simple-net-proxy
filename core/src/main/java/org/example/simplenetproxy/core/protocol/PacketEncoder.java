package org.example.simplenetproxy.core.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(packet.getId());
        byteBuf.writeByte(packet.getType());
        byteBuf.writeByte(packet.getState());
        byteBuf.writeInt(packet.getLength());
        byteBuf.writeBytes(packet.getBody());
    }
}
