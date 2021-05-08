package org.example.simplenetproxy.core.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static org.example.simplenetproxy.core.protocol.Packet.BODY_START_INDEX;

public class PacketDecoder extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] data = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(data);
        System.err.println(new String(data));
        byteBuf.readerIndex(0);


        Packet packet = new Packet();
        packet.setId(byteBuf.readInt());
        packet.setType(byteBuf.readByte());
        packet.setState(byteBuf.readByte());
        packet.setLength(byteBuf.readInt());
        packet.setBody(byteBuf.retainedSlice(BODY_START_INDEX, packet.getLength()));
        list.add(packet);
    }
}
