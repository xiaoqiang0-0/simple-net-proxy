package org.example.simplenetproxy.core.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ProxyPacket {

    public static final int SHORT_CHANNEL_ID_LEN = 8;

    public static final byte PROXY_MSG_STATE_SUCCESS = 1;

    public static final byte PROXY_MSG_STATE_ERROR = -1;

    private String remoteChannelId;

    private byte state;

    private int length;

    private ByteBuf body;

    public String getRemoteChannelId() {
        return remoteChannelId;
    }

    public void setRemoteChannelId(String remoteChannelId) {
        this.remoteChannelId = remoteChannelId;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ByteBuf getBody() {
        return body;
    }

    public void setBody(ByteBuf body) {
        this.body = body;
    }

    public CompositeByteBuf toByteBuf() {
        CompositeByteBuf buf = Unpooled.compositeBuffer(2);
        ByteBuf header = Unpooled.buffer();
        header.writeCharSequence(remoteChannelId, StandardCharsets.UTF_8);
        header.writeByte(state);
        header.writeInt(length);
//        buf.writeBytes(header);
//        buf.writeBytes(body);
        buf.addComponent(true, header);
        buf.addComponent(true, body);
        return buf;
    }
}
