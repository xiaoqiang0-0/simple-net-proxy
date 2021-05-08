package org.example.simplenetproxy.core.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

public class PacketEncoder extends MessageToByteEncoder {


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        Packet packet = (Packet) msg;

        out.writeInt(packet.getId());
        out.writeByte(packet.getType());
        out.writeByte(packet.getState());
        out.writeInt(packet.getLength());
        out.writeBytes(packet.getBody());
        out.readerIndex(0);
        byte[]data = new byte[out.readableBytes()];
        out.readBytes(data);
        System.err.println(new String(data));
        out.readerIndex(0);
        System.out.println("refcnt = " + packet.getBody().refCnt());
        ReferenceCountUtil.release(packet.getBody());
    }
}

/*public class PacketEncoder extends MessageToMessageEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
        out.clear();
        ByteBuf header = Unpooled.buffer();
        header.writeInt(msg.getId());
        header.writeByte(msg.getType());
        header.writeByte(msg.getState());
        header.writeInt(msg.getLength());
        CompositeByteBuf resp = Unpooled.compositeBuffer();
        resp.addComponent(true, header);
        resp.addComponent(true, msg.getBody().retain());
        byte[] data = new byte[resp.readableBytes()];
        resp.readBytes(data);
        System.err.println(new String(data));
        resp.readerIndex(0);
        out.add(resp);
    }
}*/
