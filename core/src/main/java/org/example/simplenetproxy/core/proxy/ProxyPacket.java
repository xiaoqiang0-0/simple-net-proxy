package org.example.simplenetproxy.core.proxy;

import io.netty.buffer.ByteBuf;

public class ProxyPacket {

    private String remoteChannelId;

    private ByteBuf body;

    public String getRemoteChannelId() {
        return remoteChannelId;
    }

    public void setRemoteChannelId(String remoteChannelId) {
        this.remoteChannelId = remoteChannelId;
    }

    public ByteBuf getBody() {
        return body;
    }

    public void setBody(ByteBuf body) {
        this.body = body;
    }
}
